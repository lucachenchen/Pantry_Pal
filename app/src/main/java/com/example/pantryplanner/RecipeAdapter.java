package com.example.pantryplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;

    private List<Recipe> recipes;

    private Activity activity;

    public RecipeAdapter(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeName.setText(recipe.getRecipeName());
        holder.preparationTime.setText(recipe.getPreparationTime());
        holder.ingredientsAvailable.setText(recipe.getIngredientsAvailable().size()+ "/" + recipe.getIngredients().size());
        // For the image, you would typically load it using a library like Glide
        // For this example, I'm assuming you have the image in your drawable folder
        int resourceId = context.getResources().getIdentifier(recipe.getIconName()+"_round", "mipmap", context.getPackageName());
        holder.recipeIcon.setImageResource(resourceId);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeActivity.class);
                // Pass the recipe ID to the RecipeActivity
                intent.putExtra("RECIPE_ID", recipe.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeIcon;
        TextView recipeName;
        TextView preparationTime;
        TextView ingredientsAvailable;

        RecipeViewHolder(View itemView) {
            super(itemView);
            recipeIcon = itemView.findViewById(R.id.recipeIcon);
            recipeName = itemView.findViewById(R.id.recipeName);
            preparationTime = itemView.findViewById(R.id.preparationTime);
            ingredientsAvailable = itemView.findViewById(R.id.ingredientsAvailable);
        }
    }
}
