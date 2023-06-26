package com.example.pantryplanner;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeHelper {
    private Context context;

    public RecipeHelper(Context context) {
        this.context = context;
    }

    public List<Grocery> getIngredientsAvailable(List<String> ingredients) {
        GroceryHelper groceryHelper = new GroceryHelper(context);
        List<Grocery> availableIngredients = new ArrayList<>();
        HashMap<String, List<Grocery>> groceries = groceryHelper.loadGroceriesFromDatabase();

        for (Map.Entry<String, List<Grocery>> entry : groceries.entrySet()) {
            for (Grocery grocery : entry.getValue()) {
                if (ingredients.contains(grocery.getGroceryName())) {
                    availableIngredients.add(grocery);
                }
            }
        }

        return availableIngredients;
    }


    public Recipe getRecipeById(int id) {
        List<Recipe> recipes = loadRecipesFromAssets();
        for (Recipe recipe : recipes) {
            if (recipe.getId() == id) {
                return recipe;
            }
        }
        return null;
    }


    public List<Recipe> loadRecipesFromAssets() {
        List<Recipe> recipes = new ArrayList<>();
        String json;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open("recipes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                int id = obj.getInt("id");
                String recipeName = obj.getString("recipeName");
                String iconName = obj.getString("iconName");
                String preparationTime = obj.getString("preparationTime");

                JSONArray ingredientsJson = obj.getJSONArray("ingredients");
                List<String> ingredients = new ArrayList<>();
                for (int j = 0; j < ingredientsJson.length(); j++) {
                    ingredients.add(ingredientsJson.getString(j));
                }

                JSONArray stepsJson = obj.getJSONArray("preparationSteps");
                List<String> preparationSteps = new ArrayList<>();
                for (int k = 0; k < stepsJson.length(); k++) {
                    preparationSteps.add(stepsJson.getString(k));
                }

                Recipe recipe = new Recipe(id, recipeName, iconName, preparationTime, ingredients, preparationSteps);

                //logic to handle available ingredients
                recipe.setIngredientsAvailable(this.getIngredientsAvailable(recipe.getIngredients()));

                recipes.add(recipe);
            }
        } catch (IOException | JSONException e) {
            Log.e("RecipeHelper", "Error loading JSON from asset", e);
        }



        return recipes;
    }
}

