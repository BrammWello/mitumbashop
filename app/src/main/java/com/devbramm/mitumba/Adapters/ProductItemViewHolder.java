package com.devbramm.mitumba.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbramm.mitumba.Interface.ProductItemClickListener;
import com.devbramm.mitumba.R;

public class ProductItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productName;
    public TextView productPrice;
    public TextView productRating;
    public ImageView productImage;

    private ProductItemClickListener productItemClickListener;

    public ProductItemViewHolder(@NonNull View itemView) {
        super(itemView);

        productName = itemView.findViewById(R.id.product_name);
        productPrice = itemView.findViewById(R.id.product_price);
        productRating = itemView.findViewById(R.id.product_rating);
        productImage = itemView.findViewById(R.id.product_image);

        itemView.setOnClickListener(this);
    }

    public void setProductItemClickListener(ProductItemClickListener productItemClickListener) {
        this.productItemClickListener = productItemClickListener;
    }

    @Override
    public void onClick(View v) {
        productItemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
