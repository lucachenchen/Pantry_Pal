package com.example.pantryplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    private Context context;
    private List<Grocery> groceries;

    public GroceryAdapter(Context context, List<Grocery> groceries) {
        this.context = context;
        this.groceries = groceries;
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grocery, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        Grocery grocery = groceries.get(position);
        holder.tvGroceryName.setText(grocery.getGroceryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something with the clicked grocery
                // For example, show the dialog:
                GroceryDialogFragment dialogFragment = GroceryDialogFragment.newInstance(grocery.getId(), grocery.getGroceryName(), grocery.getIcon(), grocery.getStore(), grocery.getBoughtDate(), grocery.getOpenedDate(), grocery.getUseUntilDate());
                dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "groceryDialog");
            }
        });

        if (grocery.isAboutToExpire()) {
            holder.rlGrocery.setBackgroundResource(R.drawable.grocery_background_spoiling);
        } else if (grocery.isOpened()) {
            holder.rlGrocery.setBackgroundResource(R.drawable.grocery_background_opened);
        } else {
            holder.rlGrocery.setBackgroundResource(R.drawable.grocery_background_closed);
        }

    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    class GroceryViewHolder extends RecyclerView.ViewHolder {

        TextView tvGroceryName;
        RelativeLayout rlGrocery;

        GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroceryName = itemView.findViewById(R.id.tvGroceryName);
            rlGrocery = itemView.findViewById(R.id.rlGrocery);
        }
    }
}
