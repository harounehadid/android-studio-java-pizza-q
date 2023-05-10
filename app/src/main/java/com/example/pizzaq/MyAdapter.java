package com.example.pizzaq;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView pizzaImg;
        public TextView pizzaName;
        public TextView caloriesCount;
        public TextView price;
        public ImageButton removeBtn;
        public ImageButton addBtn;
        public TextView amount;
        public int viewId;

        public ViewHolder(View v) {
            super(v);
            pizzaImg = v.findViewById(R.id.item_img);
            pizzaName = v.findViewById(R.id.item_name);
            caloriesCount = v.findViewById(R.id.calories_count);
            price = v.findViewById(R.id.price);
            removeBtn = v.findViewById(R.id.remove_btn);
            addBtn = v.findViewById(R.id.add_btn);
            amount = v.findViewById(R.id.amount);
        }
    }

    private ArrayList<Pizza> pizzasData;
    private Context mContext;

    public MyAdapter(ArrayList<Pizza> pizzasData, Context context) {
        this.pizzasData = pizzasData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pizza pizza = this.pizzasData.get(position);

        holder.pizzaImg.setImageResource(
                this.mContext.getResources().getIdentifier(
                        pizza.getImgId(),
                        "drawable",
                        this.mContext.getPackageName()
        ));

        holder.pizzaName.setText(pizza.getName());
        holder.caloriesCount.setText(Integer.toString(pizza.getCalories()));
        holder.price.setText(Integer.toString(pizza.getPrice()));

        holder.viewId = pizza.getId();

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.amount.setText(Integer.toString(pizza.add(holder.viewId)));
            }
        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.amount.setText(Integer.toString(pizza.remove(holder.viewId)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.pizzasData.size();
    }
}
