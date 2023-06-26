package com.example.pantryplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GroceryHistoryAdapter extends RecyclerView.Adapter<GroceryHistoryAdapter.GroceryHistoryViewHolder> {
    private List<GroceryHistory> groceryHistoryList;
    private Context context;

    public GroceryHistoryAdapter(Context context, List<GroceryHistory> groceryHistoryList) {
        this.context = context;
        this.groceryHistoryList = groceryHistoryList;
    }

    @NonNull
    @Override
    public GroceryHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grocery_history_item, parent, false);
        return new GroceryHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryHistoryViewHolder holder, int position) {
        GroceryHistory groceryHistory = groceryHistoryList.get(position);
        holder.groceryName.setText(groceryHistory.getGroceryName());
        holder.statusChange.setText(groceryHistory.getStatusChange());
        holder.updateDate.setText(groceryHistory.getUpdateDate());

        // Here, you should load your grocery icon
        // This is just a placeholder, update it to suit your needs
        int id = 0;
        id = context.getResources().getIdentifier(groceryHistory.getGroceryIcon(), "drawable", context.getPackageName());

        if (id == 0) {
            id = context.getResources().getIdentifier("fridge_empty", "drawable", context.getPackageName());
        }

        holder.groceryIcon.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return groceryHistoryList.size();
    }

    static class GroceryHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView groceryName, statusChange, updateDate;
        ImageView groceryIcon;

        public GroceryHistoryViewHolder(View itemView) {
            super(itemView);
            groceryIcon = itemView.findViewById(R.id.groceryIcon);
            groceryName = itemView.findViewById(R.id.groceryName);
            statusChange = itemView.findViewById(R.id.statusChange);
            updateDate = itemView.findViewById(R.id.updateDate);
        }
    }
}

