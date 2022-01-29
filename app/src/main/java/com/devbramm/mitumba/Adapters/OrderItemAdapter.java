package com.devbramm.mitumba.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devbramm.mitumba.Models.Cart;
import com.devbramm.mitumba.R;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {

    Context mContext;
    List<Cart> itemData;

    public OrderItemAdapter(Context mContext, List<Cart> itemData) {
        this.mContext = mContext;
        this.itemData = itemData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.order_detail_order_item,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.orderRowQuantityandSize.setText(itemData.get(position).getQuantity() + " " + itemData.get(position).getProductSize());
        holder.orderRowPrice.setText(itemData.get(position).getPrice());
        holder.orderRowName.setText(itemData.get(position).getProductName());
        holder.orderRowColor.setBackgroundColor(Color.parseColor(itemData.get(position).getProductColor()));
        Glide.with(mContext).load(itemData.get(position).getProductImage()).into(holder.orderRowImage);

    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView orderRowQuantityandSize, orderRowName, orderRowPrice;
        ImageView orderRowImage;
        CardView orderRowColor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderRowQuantityandSize = itemView.findViewById(R.id.order_row_quantitiy_and_size);
            orderRowName = itemView.findViewById(R.id.order_row_name);
            orderRowPrice = itemView.findViewById(R.id.order_row_price);
            orderRowImage = itemView.findViewById(R.id.order_row_image);
            orderRowColor = itemView.findViewById(R.id.order_row_color);
        }
    }
}
