package com.example.pantryplanner;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantryplanner.databinding.ActivityStatsBinding;

import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityStatsBinding binding;
    private RecyclerView recyclerView;
    private GroceryHistoryAdapter groceryHistoryAdapter;
    private StatsActivity statsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView title = findViewById(R.id.toolbarTitle);
        title.setText(getString(R.string.title_stats));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        statsActivity = this;

        recyclerView = findViewById(R.id.groceryHistoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GroceryHelper groceryHistoryHelper = new GroceryHelper(this);
        try {
            List<GroceryHistory> groceryHistories = groceryHistoryHelper.loadGroceryHistory();
            groceryHistoryAdapter = new GroceryHistoryAdapter(this, groceryHistories);
            recyclerView.setAdapter(groceryHistoryAdapter);
        } catch (Exception e) {
            Log.e("StatsActivity", "Error loading grocery history", e);
        }

        LinearLayout navPantry = findViewById(R.id.custom_nav_inventory);
        navPantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the inventory activity
                Intent intent = new Intent(statsActivity, InventoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navMenu = findViewById(R.id.custom_nav_menu);
        navMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the menu activity
                Intent intent = new Intent(statsActivity, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navScan = findViewById(R.id.custom_nav_scan);
        navScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the scan activity
                Intent intent = new Intent(statsActivity, ScanActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navStats = findViewById(R.id.custom_nav_stats);
        navStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the stats activity
                Intent intent = new Intent(statsActivity, StatsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navProfile = findViewById(R.id.custom_nav_profile);
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the profile activity
                Intent intent = new Intent(statsActivity, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });



    }


}