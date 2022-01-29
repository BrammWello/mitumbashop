package com.devbramm.mitumba.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devbramm.mitumba.Models.Cart;
import com.devbramm.mitumba.R;
import com.google.android.material.card.MaterialCardView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartProductsAdapter extends RecyclerView.Adapter<CartViewHolder> {

    public List<Cart> cartData = new ArrayList<>();
    public Context context;

    public CartProductsAdapter(List<Cart> cartData, Context context) {
        this.cartData = cartData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_product_item,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        //get price in local format
        Locale locale = new Locale("en", "KE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        float price = (Float.parseFloat(cartData.get(position).getPrice()))*(Float.parseFloat(cartData.get(position).getQuantity()));

        holder.cartProductName.setText(cartData.get(position).getProductName());
        holder.cartProductPrice.setText(fmt.format(price));
        holder.cartProductQuantity.setText(cartData.get(position).getQuantity());
        holder.cartProductColor.setBackgroundColor(Color.parseColor(cartData.get(position).getProductColor()));
        holder.cartProductSize.setText(cartData.get(position).getProductSize());

        Glide.with(context).load(cartData.get(position).getProductImage()).into(holder.cartProductImage);
    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public void removeItem(int position) {
        cartData.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position) {
        cartData.add(position,item);
        notifyItemInserted(position);
    }
}
