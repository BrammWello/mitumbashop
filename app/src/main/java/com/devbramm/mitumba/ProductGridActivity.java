package com.devbramm.mitumba;

import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import com.bumptech.glide.Glide;
import com.devbramm.mitumba.Adapters.ProductItemViewHolder;
import com.devbramm.mitumba.Interface.ProductItemClickListener;
import com.devbramm.mitumba.Models.ProductList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ProductGridActivity extends AppCompatActivity {

    String categoryName = "";
    TextView categoryNameTitle;
    ImageView productToCart, filterBtn;
    Dialog filterDialog;
    ProgressBar loadingCategoryProducts;
    FirebaseRecyclerAdapter<ProductList, ProductItemViewHolder> adapter;
    RecyclerView productsGridRecycler;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference productsCategoriesReference;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_grid);

        categoryNameTitle = findViewById(R.id.category_name);
        productToCart = findViewById(R.id.product_to_cart);
        filterBtn = findViewById(R.id.filter_products);
        loadingCategoryProducts = findViewById(R.id.loading_category_products);
        filterDialog = new Dialog(this);

        loadingCategoryProducts.setVisibility(View.VISIBLE);

        //getCategoryNameClicked
        if (getIntent() != null) {
            categoryName = getIntent().getStringExtra("categoryName");
            if (categoryName.equals("dresses")) {
                categoryNameTitle.setText("Dresses");
            } else if (categoryName.equals("lingerie")) {
                categoryNameTitle.setText("Lingerie");
            } else if (categoryName.equals("heels")) {
                categoryNameTitle.setText("Heels");
            } else if (categoryName.equals("socks")) {
                categoryNameTitle.setText("Socks");
            } else if (categoryName.equals("casual_shoes")) {
                categoryNameTitle.setText("Casual Shoes");
            } else if (categoryName.equals("baby_shop")) {
                categoryNameTitle.setText("Baby Shop");
            }

        } else {
            finish();
        }

        FirebaseApp.initializeApp(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        productsCategoriesReference = firebaseDatabase.getReference("categories").child(categoryName);


        productsGridRecycler = findViewById(R.id.products_recycler_view);
        productsGridRecycler.setLayoutManager(new GridLayoutManager(this,2));
        //load the items from database now
        loadProductItems();


        productToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCartFromProduct = new Intent(ProductGridActivity.this, CartActivity.class);
                startActivity(toCartFromProduct);
            }
        });

        findViewById(R.id.btn_category_grid_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v);
            }
        });

    }

    private void loadProductItems() {

        Query productsQuery = productsCategoriesReference.orderByKey();
        FirebaseRecyclerOptions productsOptions = new FirebaseRecyclerOptions.Builder<ProductList>().setQuery(productsQuery, ProductList.class).build();

        adapter = new FirebaseRecyclerAdapter<ProductList, ProductItemViewHolder>(productsOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ProductItemViewHolder productItemViewHolder, int i, @NonNull final ProductList productList) {
                productItemViewHolder.productName.setText(productList.getItemName());

                Locale locale = new Locale("en", "KE");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                productItemViewHolder.productPrice.setText(fmt.format(Float.parseFloat(productList.getItemPrice())));

                productItemViewHolder.productRating.setText(productList.getItemRating());

                Glide.with(getBaseContext()).load(productList.getItemImage())
                        .into(productItemViewHolder.productImage);

                productItemViewHolder.setProductItemClickListener(new ProductItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent toProduct = new Intent(ProductGridActivity.this, ProductViewActivity.class);
                        toProduct.putExtra("productID",adapter.getRef(position).getKey());
                        toProduct.putExtra("categoryName",categoryName);

//                        Pair<View, String> pair1 = Pair.create((View)productItemViewHolder.productImage,)

                        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(ProductGridActivity.this, productItemViewHolder.productImage,"sharedName");
                        startActivity(toProduct,activityOptions.toBundle());

                    }
                });
            }

            @NonNull
            @Override
            public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.product_item,parent,false);

                return new ProductItemViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                loadingCategoryProducts.setVisibility(View.INVISIBLE);
            }
        };
        productsGridRecycler.setAdapter(adapter);
        adapter.startListening();
    }

    public void showPopUp(View view) {
        filterDialog.setContentView(R.layout.filter_category_dialog);
        Button filterFinishBtn;
        ImageView dismissDialog;
        SeekBar priceSeekBar;
        final TextView maxSeekbarPrice;

        filterFinishBtn = filterDialog.findViewById(R.id.filter_products_dialog_button);
        dismissDialog = filterDialog.findViewById(R.id.dismiss_dialog_button);
        priceSeekBar = filterDialog.findViewById(R.id.price_seekbar);
        maxSeekbarPrice = filterDialog.findViewById(R.id.max_seekbar_value);

        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            String seekBarProgressValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgressValue = String.valueOf(progress);
                maxSeekbarPrice.setText(seekBarProgressValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                maxSeekbarPrice.setText(seekBarProgressValue);
            }
        });

        filterFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.hide();
            }
        });

        dismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
            }
        });

        filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        filterDialog.show();
    }
}