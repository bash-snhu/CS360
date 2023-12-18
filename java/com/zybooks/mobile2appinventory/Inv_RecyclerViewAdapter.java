package com.zybooks.mobile2appinventory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

class Inv_RecyclerViewAdapter extends RecyclerView.Adapter<Inv_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<InventoryModel> inventoryModels;
    Activity activity;

    public Inv_RecyclerViewAdapter(Activity activity, Context context, ArrayList<InventoryModel> inventoryModels) {
        this.context = context;
        this.inventoryModels = inventoryModels;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Inv_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Populate layout with all items
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new Inv_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Inv_RecyclerViewAdapter.MyViewHolder holder, final int position) {
        holder.itemName.setText(inventoryModels.get(position).getName());
        holder.itemDesc.setText(inventoryModels.get(position).getDescription());
        holder.itemCount.setText(inventoryModels.get(position).getCount());

        // Clicking Row Item Behavior
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send row info with intent to Detail View
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", String.valueOf(inventoryModels.get(position).getId()));
                intent.putExtra("name", String.valueOf(inventoryModels.get(position).getName()));
                intent.putExtra("desc", String.valueOf(inventoryModels.get(position).getDescription()));
                intent.putExtra("count", String.valueOf(inventoryModels.get(position).getCount()));

                // Start Detail View Activity
                activity.startActivityForResult(intent, 1);
            }
        });

        // Clicking delete button in row item behavior
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send row info with intent to Delete View
                Intent intent = new Intent(context, DeleteActivity.class);
                intent.putExtra("id", String.valueOf(inventoryModels.get(position).getId()));
                intent.putExtra("name", String.valueOf(inventoryModels.get(position).getName()));
                intent.putExtra("desc", String.valueOf(inventoryModels.get(position).getDescription()));
                intent.putExtra("count", String.valueOf(inventoryModels.get(position).getCount()));

                // Refresh main activity
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inventoryModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iconView;

        TextView itemName, itemDesc, itemCount;

        ImageButton deleteButton;

        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iconView = itemView.findViewById(R.id.imageView);
            itemName = itemView.findViewById(R.id.name);
            itemDesc = itemView.findViewById(R.id.description);
            itemCount = itemView.findViewById(R.id.count);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            rowLayout = itemView.findViewById(R.id.rowlayout);
        }
    }
}
