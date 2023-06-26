package com.example.pantryplanner;

import android.content.Intent;
import android.os.Bundle;

import com.example.pantryplanner.databinding.ActivityMenuBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.pantryplanner.databinding.ActivityRecipeBinding;

public class RecipeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRecipeBinding binding;
    private RecipeActivity recipeActivity;

    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            recipeId = getIntent().getIntExtra("RECIPE_ID", 1);
        } catch (Exception e) {
            recipeId = 1;
        }

        RecipeHelper recipeHelper = new RecipeHelper(this);
        Recipe recipe = recipeHelper.getRecipeById(recipeId);

        ImageView recipeImage = findViewById(R.id.recipeImage);
        int resourceId = this.getResources().getIdentifier(recipe.getIconName(), "mipmap", this.getPackageName());
        recipeImage.setImageResource(resourceId);

        TextView recipeName = findViewById(R.id.recipeName);
        recipeName.setText(recipe.getRecipeName());

        TextView preparationTime = findViewById(R.id.preparationTime);
        preparationTime.setText(recipe.getPreparationTime());

        TextView ingredientsAvailable = findViewById(R.id.ingredientsAvailable);
        ingredientsAvailable.setText(recipe.getIngredientsAvailable().size()+ "/" + recipe.getIngredients().size());

        TextView preparationSteps = findViewById(R.id.preparationSteps);

        String allSteps = "";
        for (String step : recipe.getPreparationSteps()) {
            allSteps += step + "\n\n";
        }

        preparationSteps.setText(allSteps);


        recipeActivity = this;

        LinearLayout navPantry = findViewById(R.id.custom_nav_inventory);
        navPantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the inventory activity
                Intent intent = new Intent(recipeActivity, InventoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navMenu = findViewById(R.id.custom_nav_menu);
        navMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the menu activity
                Intent intent = new Intent(recipeActivity, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navScan = findViewById(R.id.custom_nav_scan);
        navScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the scan activity
                Intent intent = new Intent(recipeActivity, ScanActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navStats = findViewById(R.id.custom_nav_stats);
        navStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the stats activity
                Intent intent = new Intent(recipeActivity, StatsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LinearLayout navProfile = findViewById(R.id.custom_nav_profile);
        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the profile activity
                Intent intent = new Intent(recipeActivity, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }


}