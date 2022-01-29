package com.devbramm.mitumba.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.devbramm.mitumba.R;
import com.google.android.material.card.MaterialCardView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView cartProductImage;
    public TextView cartProductName, cartProductPrice, cartProductQuantity, cartProductSize;
    public MaterialCardView cartProductColor;

    public ConstraintLayout cardForeground;
    public ConstraintLayout cardBackground;

    public void setCartProductName(TextView cartProductName) {
        this.cartProductName = cartProductName;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cartProductName = itemView.findViewById(R.id.cart_product_name);
        cartProductPrice = itemView.findViewById(R.id.cart_product_price);
        cartProductImage = itemView.findViewById(R.id.cart_product_image);
        cartProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        cartProductColor = itemView.findViewById(R.id.cart_product_color);
        cartProductSize = itemView.findViewById(R.id.cart_product_size);
        cardBackground = itemView.findViewById(R.id.cart_item_background);
        cardForeground = itemView.findViewById(R.id.cart_item_foreground);
    }

    @Override
    public void onClick(View v) {

    }
}