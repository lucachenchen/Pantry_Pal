package com.example.pantryplanner;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceryDialogFragment extends DialogFragment {
    // You can add other data or views as needed

    private int groceryId;
    private String groceryName;
    private String icon;
    private String store;
    private String boughtDate;
    private String openedDate;
    private String useUntilDate;



    // Use a factory method to create a new instance of this fragment
    public static GroceryDialogFragment newInstance(int groceryId, String groceryName, String icon, String store, Date boughtDate, Date openedDate, Date useUntilDate) {
        GroceryDialogFragment fragment = new GroceryDialogFragment();


        // Pass the groceryId to the fragment
        Bundle args = new Bundle();
        SimpleDateFormat monthFormater = new SimpleDateFormat("MMM");
        args.putInt("groceryId", groceryId);
        args.putString("groceryName", groceryName);
        args.putString("icon", icon);
        args.putString("store", store);
        DateTimeFormatter monthFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            monthFormatter = DateTimeFormatter.ofPattern("MMMM");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (boughtDate != null) {
                LocalDate localDate = LocalDate.parse(sdf.format(boughtDate), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String month = localDate.format(monthFormatter);
                args.putString("boughtDate", "" + localDate.getDayOfMonth() + " " + month);
            } else {
                args.putString("boughtDate", "-");
            }
            if (openedDate != null) {
                LocalDate localDate = LocalDate.parse(sdf.format(openedDate), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String month = localDate.format(monthFormatter);
                args.putString("openedDate", "" + localDate.getDayOfMonth() + " " + month);
            } else {
                args.putString("openedDate", "-");
            }
            if (useUntilDate != null) {
                LocalDate localDate = LocalDate.parse(sdf.format(useUntilDate), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String month = localDate.format(monthFormatter);
                args.putString("useUntilDate", "" + localDate.getDayOfMonth() + " " + month);
            } else {
                args.putString("useUntilDate", "-");
            }
        }
        fragment.setArguments(args);



        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groceryId = getArguments().getInt("groceryId");
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        Activity parentActivity = getActivity();
        if (parentActivity instanceof InventoryActivity) {
            ((InventoryActivity) parentActivity).reloadGroceriesFromDatabase();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_grocery_item, container, false);

        GroceryHelper groceryHelper = new GroceryHelper(getActivity());

        if (getArguments() != null) {
            int groceryId = getArguments().getInt("groceryId");
        }

        // Retrieve TextViews and set click listeners
        TextView openButton = view.findViewById(R.id.popUpOpenButton);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, List<Grocery>> groceries = groceryHelper.loadGroceriesFromDatabase();
                for (List<Grocery> groceryList : groceries.values()) {
                    for (Grocery grocery : groceryList) {
                        if (grocery.getId() == groceryId) {
                            Date now = new Date();
                            grocery.setOpenedDate(now);
                            groceryHelper.updateGrocery(grocery, "opened");
                            // If you need to update this in your storage, add code here
                            break;
                        }
                    }
                }
                groceryHelper.writeGroceries(groceries);

                dismiss();
            }
        });

        TextView usedButton = view.findViewById(R.id.popUpUsedButton);
        usedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grocery toBeRemovedGrocery = groceryHelper.getGroceryById(groceryId);
                groceryHelper.removeGrocery(toBeRemovedGrocery.getGroceryType(), toBeRemovedGrocery.getId(), "used");

                dismiss();
            }
        });

        TextView trashButton = view.findViewById(R.id.popUpTrashButton);
        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grocery toBeRemovedGrocery = groceryHelper.getGroceryById(groceryId);
                groceryHelper.removeGrocery(toBeRemovedGrocery.getGroceryType(), toBeRemovedGrocery.getId(), "trashed");
                dismiss();
            }
        });


        if (getArguments() != null) {
            int groceryId = getArguments().getInt("groceryId");
            String groceryName = getArguments().getString("groceryName");
            String icon = getArguments().getString("icon");
            String store = getArguments().getString("store");
            String boughtDate = getArguments().getString("boughtDate");
            String openedDate = getArguments().getString("openedDate");
            String useUntilDate = getArguments().getString("useUntilDate");

            TextView groceryNameTextView = view.findViewById(R.id.popUpGroceryName);
            groceryNameTextView.setText(groceryName);
            ImageView iconImageView = view.findViewById(R.id.popUpGroceryIcon);
            iconImageView.setImageResource(R.drawable.fridge_filled);
            TextView boughtInfo = view.findViewById(R.id.popUpBoughtInfo);
            boughtInfo.setText("on " + boughtDate + " at " + store);
            TextView openedInfo = view.findViewById(R.id.popUpOpenedDate);
            openedInfo.setText(openedDate);
            TextView useUntilInfo = view.findViewById(R.id.popUpUseUntilDate);
            useUntilInfo.setText(useUntilDate);
        }





        return view;
    }
}
