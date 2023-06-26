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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantryplanner.databinding.ActivityMenuBinding;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMenuBinding binding;
    private RecipeHelper recipeHelper;

    private MenuActivity menuActivity;
    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView title = findViewById(R.id.toolbarTitle);
        title.setText(getString(R.string.title_menu));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuActivity = this;

        RecipeHelper recipeHelper = new RecipeHelper(this);
        List<Recipe> recipes = recipeHelper.loadRecipesFromAssets();

        RecyclerView recyclerView = findViewById(R.id.recipeRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecipeAdapter recipeAdapter = new RecipeAdapter(recipes, this);
        recyclerView.setAdapter(recipeAdapter);

        LinearLayout navPantry = findViewById(R.id.custom_nav_inventory);
        navPantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the inventory activity
                Intent intent = new Intent(menuActivity, InventoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navMenu = findViewById(R.id.custom_nav_menu);
        navMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the menu activity
                Intent intent = new Intent(menuActivity, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navScan = findViewById(R.id.custom_nav_scan);
        navScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the scan activity
                Intent intent = new Intent(menuActivity, ScanActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navStats = findViewById(R.id.custom_nav_stats);
        navStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the stats activity
                Intent intent = new Intent(menuActivity, StatsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navProfile = findViewById(R.id.custom_nav_profile);
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the profile activity
                Intent intent = new Intent(menuActivity, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }

}