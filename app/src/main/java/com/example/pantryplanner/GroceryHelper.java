package com.example.pantryplanner;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.pantryplanner.Grocery;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GroceryHelper {
    private File file;
    private AssetManager assetManager;
    private Context context;

    private HashMap<String, List<Grocery>> groceriesByType = new HashMap<>();


    public GroceryHelper(Context context) {
        assetManager = context.getAssets();
        this.context = context;
        file = new File(context.getFilesDir(), "groceries.csv");
    }

    public HashMap<String, List<Grocery>> loadGroceriesFromDatabase() {
        groceriesByType.clear();
        FileInputStream fis = null;
        try {
            fis = context.openFileInput("groceries.csv");
        } catch (FileNotFoundException e) {
            // If the file does not exist, create an empty file in internal storage
            try {
                FileOutputStream fos = context.openFileOutput("groceries.csv", Context.MODE_PRIVATE);
                fos.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            return new HashMap<>();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(tokens[0]));
                grocery.setGroceryType(tokens[1]);
                grocery.setGroceryName(tokens[2]);
                grocery.setIcon(tokens[3]);
                grocery.setStore(tokens[4]);
                if (tokens.length > 5){
                    grocery.setBoughtDate(stringToDate(tokens[5]));
                }
                if (tokens.length > 6){
                    grocery.setOpenedDate(stringToDate(tokens[6]));
                }
                if (tokens.length > 7){
                    grocery.setUseUntilDate(stringToDate(tokens[7]));
                }
                List<Grocery> groceries = groceriesByType.get(grocery.getGroceryType());
                if (groceries == null) {
                    groceries = new ArrayList<>();
                    groceriesByType.put(grocery.getGroceryType(), groceries);
                }
                groceries.add(grocery);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return groceriesByType;
    }


    public Grocery getGroceryById(int id) {
        HashMap<String, List<Grocery>> groceries = loadGroceriesFromDatabase();
        for (List<Grocery> groceryList : groceries.values()) {
            for (Grocery grocery : groceryList) {
                if (grocery.getId() == id) {
                    return grocery;  // Return the found grocery
                }
            }
        }
        return null;
    }

    public void writeGroceries(HashMap<String, List<Grocery>> groceries) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput("groceries.csv", Context.MODE_PRIVATE);
            for (Map.Entry<String, List<Grocery>> entry : groceries.entrySet()) {
                for (Grocery grocery : entry.getValue()) {
                    String line = String.join(";",
                            Integer.toString(grocery.getId()),
                            entry.getKey(),
                            grocery.getGroceryName(),
                            grocery.getIcon(),
                            grocery.getStore(),
                            grocery.getBoughtDate() != null ? grocery.getBoughtDate().toString() : " ",
                            grocery.getOpenedDate() != null ? grocery.getOpenedDate().toString() : " ",
                            grocery.getUseUntilDate() != null ? grocery.getUseUntilDate().toString() : " ") + "\n";
                    fos.write(line.getBytes());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        File file = new File(context.getFilesDir(), "groceries.csv");

    }

    public Grocery createGrocery(String groceryName){
        Grocery grocery = new Grocery();

        //TODO logic for classifying grocery type
        if (groceryName.equals("Zitronen")) {
            grocery.setGroceryType("Fruit");
            grocery.setIcon("lemon");
        } else if (groceryName.equals("Hüttenkäse")) {
            grocery.setGroceryType("Dairy");
            grocery.setIcon("cottage_cheese");
        } else if (groceryName.equals("Poulet")) {
            grocery.setGroceryType("Meat");
            grocery.setIcon("chicken");
        } else {
            grocery.setGroceryType("Other");
            grocery.setIcon("other");
        }
        grocery.setGroceryName(groceryName);
        grocery.setStore("Migros");
        grocery.setBoughtDate(new Date());
        grocery.setOpenedDate(null);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, 2);
        Date updatedDate = cal.getTime();
        grocery.setUseUntilDate(updatedDate);
        grocery.setId(grocery.hashCode());
        return grocery;
    }

    public void removeGrocery(String key, int groceryId, String statusChange) {
        HashMap<String, List<Grocery>> groceries = loadGroceriesFromDatabase();
        List<Grocery> groceryList = groceries.get(key);
        Grocery removedGrocery = null;
        if (groceryList != null) {
            for (Grocery grocery : groceryList) {
                if (grocery.getId() == groceryId) {
                    removedGrocery = grocery;
                    groceryList.remove(grocery);
                    break;
                }
            }
        }
        if (removedGrocery != null) {
            updateGroceryHistory(removedGrocery, statusChange);
        }
        writeGroceries(groceries);
    }

    public List<GroceryHistory> loadGroceryHistory() {
        List<GroceryHistory> groceryHistory = new ArrayList<>();
        String filename = "groceryHistory.csv";

        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                GroceryHistory history = new GroceryHistory(data[0], data[1], data[2], data[3]);
                groceryHistory.add(history);
            }
        } catch (FileNotFoundException e) {
            // File not found. No previous history.
        } catch (IOException e) {
            // Error occurred when reading the file.
        }

        return groceryHistory;
    }



    private void updateGroceryHistory(Grocery updatedGrocery, String statusChange) {
        List<GroceryHistory> groceryHistory = loadGroceryHistory();

        // Check if list has 10 or more elements.
        if (groceryHistory.size() >= 10) {
            // Remove the last element.
            groceryHistory.remove(groceryHistory.size() - 1);
        }

        // Get the current date
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        // Add the updated grocery at the beginning of the list.
        groceryHistory.add(0, new GroceryHistory(updatedGrocery.getGroceryName(), updatedGrocery.getIcon(), statusChange, currentDate));

        // Write the history back to the file.
        String filename = "groceryHistory.csv";
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            for (GroceryHistory history : groceryHistory) {
                String line = history.getGroceryName() + ";" + history.getGroceryIcon() + ";" + history.getStatusChange() + ";" + history.getUpdateDate() + "\n";
                fos.write(line.getBytes());
            }
            fos.close();
        } catch (IOException e) {
            // Error occurred when writing to the file.
        }
    }




    // helper method to convert string to date
    private Date stringToDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date stringToDateStatic(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateGrocery(Grocery grocery, String statusChange) {
        updateGroceryHistory(grocery, statusChange);
    }
}
