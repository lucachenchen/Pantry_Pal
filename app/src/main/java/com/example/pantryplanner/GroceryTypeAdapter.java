package com.example.pantryplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroceryTypeAdapter extends RecyclerView.Adapter<GroceryTypeAdapter.GroceryTypeViewHolder> {

    private Context context;
    private HashMap<String, List<Grocery>> groceriesByType;

    public GroceryTypeAdapter(Context context, HashMap<String, List<Grocery>> groceriesByType) {
        this.context = context;
        this.groceriesByType = groceriesByType;
    }

    @NonNull
    @Override
    public GroceryTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grocery_type, parent, false);
        return new GroceryTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryTypeViewHolder holder, int position) {
        String groceryType = new ArrayList<>(groceriesByType.keySet()).get(position);
        List<Grocery> groceries = groceriesByType.get(groceryType);

        holder.tvGroceryType.setText(groceryType);

        // Create and set a new adapter for the inner RecyclerView.
        GroceryAdapter groceryAdapter = new GroceryAdapter(context, groceries);
        holder.rvGroceries.setLayoutManager(new GridLayoutManager(context, 3));
        holder.rvGroceries.setAdapter(groceryAdapter);
    }

    @Override
    public int getItemCount() {
        return groceriesByType.size();
    }

    class GroceryTypeViewHolder extends RecyclerView.ViewHolder {

        TextView tvGroceryType;
        RecyclerView rvGroceries;

        GroceryTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroceryType = itemView.findViewById(R.id.tvGroceryType);
            rvGroceries = itemView.findViewById(R.id.rvGroceries);
        }
    }
}
