package com.example.pantryplanner;

import android.content.Context;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantryplanner.databinding.ActivityInventoryBinding;
import com.google.mlkit.vision.demo.textdetector.StillImageActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityInventoryBinding binding;

    private static final int MULTIPLE_PERMISSIONS_REQUEST_CODE = 123;

    private String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private InventoryActivity inventoryActivity;
    private HashMap<String, List<Grocery>> groceriesByType = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Check for permissions
        if (!hasPermissions(permissions)) {
            // Request permissions
            ActivityCompat.requestPermissions(this, permissions, MULTIPLE_PERMISSIONS_REQUEST_CODE);
        }

        TextView title = findViewById(R.id.toolbarTitle);
        title.setText(getString(R.string.title_inventory));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inventoryActivity = this;

        this.groceriesByType = this.loadGroceriesFromDatabase();

        RecyclerView rvGroceryTypes = findViewById(R.id.rvGroceryTypes);
        rvGroceryTypes.setLayoutManager(new LinearLayoutManager(this));
        GroceryTypeAdapter groceryTypeAdapter = new GroceryTypeAdapter(this, groceriesByType);
        rvGroceryTypes.setAdapter(groceryTypeAdapter);



        LinearLayout navPantry = findViewById(R.id.custom_nav_inventory);
        navPantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the inventory activity
                Intent intent = new Intent(inventoryActivity, InventoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navMenu = findViewById(R.id.custom_nav_menu);
        navMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the menu activity
                Intent intent = new Intent(inventoryActivity, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navScan = findViewById(R.id.custom_nav_scan);
        navScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the scan activity
                Intent intent = new Intent(inventoryActivity, ScanActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navStats = findViewById(R.id.custom_nav_stats);
        navStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the stats activity
                Intent intent = new Intent(inventoryActivity, StatsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navProfile = findViewById(R.id.custom_nav_profile);
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the profile activity
                Intent intent = new Intent(inventoryActivity, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


    }

    private HashMap<String, List<Grocery>> loadGroceriesFromDatabase() {
        AssetManager assetManager = getAssets();
        return new GroceryHelper(this).loadGroceriesFromDatabase();
    }

    public void reloadGroceriesFromDatabase() {
        this.groceriesByType = this.loadGroceriesFromDatabase();
        recreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.groceriesByType = this.loadGroceriesFromDatabase();
    }

    private boolean hasPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == MULTIPLE_PERMISSIONS_REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    // Permission was denied. You may want to disable the functionality that depends on this permission.
                    return;
                }
            }
            // All permissions have been granted
        }
    }
}