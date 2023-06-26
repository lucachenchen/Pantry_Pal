package com.example.pantryplanner;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.pantryplanner.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityProfileBinding binding;

    private ProfileActivity profileActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView title = findViewById(R.id.toolbarTitle);
        title.setText(getString(R.string.title_profile));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        profileActivity = this;

        LinearLayout navPantry = findViewById(R.id.custom_nav_inventory);
        navPantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the inventory activity
                Intent intent = new Intent(profileActivity, InventoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navMenu = findViewById(R.id.custom_nav_menu);
        navMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the menu activity
                Intent intent = new Intent(profileActivity, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navScan = findViewById(R.id.custom_nav_scan);
        navScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the scan activity
                Intent intent = new Intent(profileActivity, ScanActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navStats = findViewById(R.id.custom_nav_stats);
        navStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the stats activity
                Intent intent = new Intent(profileActivity, StatsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navProfile = findViewById(R.id.custom_nav_profile);
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the profile activity
                Intent intent = new Intent(profileActivity, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


    }


}