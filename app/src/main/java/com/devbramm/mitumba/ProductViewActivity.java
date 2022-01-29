package com.devbramm.mitumba;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.palette.graphics.Palette;
import androidx.cardview.widget.CardView;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devbramm.mitumba.Database.CartDatabase;
import com.devbramm.mitumba.Models.Cart;
import com.devbramm.mitumba.Models.ProductList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductViewActivity extends AppCompatActivity {

    ImageView productViewImage, firstImageSlide, btnIncreaseQuantity, btnDecreaseQuantity;
    TextView productViewName, productViewPrice, productViewRating,productQuantity;
    CardView productCardOne, cartButton;
    Dialog itemAddedDialog;

    DatabaseReference productReference;
    String categoryName = "";
    String productID = "";

    ProductList currentProduct;

    //vars to collect product details
    int quantityCounter;
    String finalStringCounter;
    String finalProductSize = "";
    String finalProductColor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        productViewImage = findViewById(R.id.product_view_image);
        firstImageSlide = findViewById(R.id.first_image_slide);
        productViewName = findViewById(R.id.product_view_name);
        productViewPrice = findViewById(R.id.product_view_price);
        productViewRating = findViewById(R.id.product_view_rating);
        productCardOne = findViewById(R.id.product_card_one);
        cartButton = findViewById(R.id.cart_button_add);
        productQuantity = findViewById(R.id.product_quantity_count);
        btnDecreaseQuantity = findViewById(R.id.btn_reduce_quantity);
        btnIncreaseQuantity = findViewById(R.id.btn_increase_quantity);

        itemAddedDialog = new Dialog(this);

        //getProductDetailsClicked
        if (getIntent() != null) {
            productID = getIntent().getStringExtra("productID");
            categoryName = getIntent().getStringExtra("categoryName");
        } else {
            finish();
        }

        productReference = FirebaseDatabase.getInstance().getReference("categories").child(categoryName).child(productID);

        //set The final layout touches
        quantityCounter = 1; //sets default quantity
        finalStringCounter = String.valueOf(quantityCounter);
        productQuantity.setText(finalStringCounter);
        setButtonResponses();

        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentProduct = dataSnapshot.getValue(ProductList.class);

                productViewName.setText(currentProduct.getItemName());

                Locale locale = new Locale("en", "KE");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                productViewPrice.setText(fmt.format(Float.parseFloat(currentProduct.getItemPrice())));
                productViewRating.setText(currentProduct.getItemRating());

                Glide.with(getBaseContext())
                        .load(currentProduct.getItemImage())
                        .into(productViewImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            setColors(productImage);
//        }


        findViewById(R.id.btn_category_grid_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.product_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCartFromProduct = new Intent(ProductViewActivity.this, CartActivity.class);
                startActivity(toCartFromProduct);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //test size and color inputs first
                if (finalProductSize == "") {
                    Toast.makeText(ProductViewActivity.this, "Select a size", Toast.LENGTH_SHORT).show();
                }
                else if (finalProductColor == "") {
                    Toast.makeText(ProductViewActivity.this, "Select a color", Toast.LENGTH_SHORT).show();
                } else {
                    sendToCart();
                }
            }
        });

    }

    private void sendToCart() {
        //create unique ID for CART purposes
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String cartProductID = productID + "_" + timeStamp;

        new CartDatabase(ProductViewActivity.this).addToCart(new Cart(
                cartProductID,
                currentProduct.getItemName(),
                finalStringCounter,
                currentProduct.getItemPrice(),
                "20",
                currentProduct.getItemImage(),
                finalProductSize,
                finalProductColor
        ));


//                showItemAddedDialog(v);
        //set the layout for the toast first
        View itemViewAddedToCartToast = getLayoutInflater().inflate(R.layout.item_added_confirm_basket,null);
        ImageView cartToastImage = itemViewAddedToCartToast.findViewById(R.id.cart_toast_image);
        TextView cartToastDetails = itemViewAddedToCartToast.findViewById(R.id.cart_toast_details);
        TextView cartToastPrice = itemViewAddedToCartToast.findViewById(R.id.cart_toast_price);

        Glide.with(getBaseContext()).load(currentProduct.getItemImage()).into(cartToastImage);
        cartToastDetails.setText(finalStringCounter + " " + currentProduct.getItemName());

        Locale locale = new Locale("en", "KE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        Float totalDialogPrice = Float.parseFloat(currentProduct.getItemPrice())*(Float.parseFloat(finalStringCounter));
        cartToastPrice.setText(fmt.format(totalDialogPrice));

        //Initiate the toast instance now and show it
        Toast toastAddItem = new Toast(getApplicationContext());
        //set the custom layout now
        toastAddItem.setView(itemViewAddedToCartToast);
        toastAddItem.setDuration(Toast.LENGTH_SHORT);
        toastAddItem.setGravity(Gravity.CENTER,0, 0);
        toastAddItem.show();
    }

    private void setButtonResponses() {

        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityCounter != 15) {
                    quantityCounter ++;
                    finalStringCounter = String.valueOf(quantityCounter);
                    productQuantity.setText(finalStringCounter);
                }
                else {
                    Toast.makeText(ProductViewActivity.this, "Maximum quantity reached. Clear in checkout before reordering", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityCounter == 1) {
                    Toast.makeText(ProductViewActivity.this, "Minimum quantity reached.", Toast.LENGTH_SHORT).show();
                }
                else {
                    quantityCounter --;
                    finalStringCounter = String.valueOf(quantityCounter);
                    productQuantity.setText(finalStringCounter);
                }
            }
        });

        //size picker buttons
        findViewById(R.id.size_small_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.product_size_small).setVisibility(View.VISIBLE);
                findViewById(R.id.product_size_medium).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_size_large).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_size_extra_large).setVisibility(View.INVISIBLE);

                finalProductSize = "Small";
            }
        });

        findViewById(R.id.size_medium_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.product_size_small).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_size_medium).setVisibility(View.VISIBLE);
                findViewById(R.id.product_size_large).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_size_extra_large).setVisibility(View.INVISIBLE);

                finalProductSize = "Medium";
            }
        });

        findViewById(R.id.size_large_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.product_size_small).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_size_medium).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_size_large).setVisibility(View.VISIBLE);
                findViewById(R.id.product_size_extra_large).setVisibility(View.INVISIBLE);

                finalProductSize = "Large";
            }
        });

        findViewById(R.id.size_extra_large_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.product_size_small).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_size_medium).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_size_large).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_size_extra_large).setVisibility(View.VISIBLE);

                finalProductSize = "Extra Large";
            }
        });

        //color picker buttons
        findViewById(R.id.color_picker_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.product_color_red).setVisibility(View.VISIBLE);
                findViewById(R.id.product_color_purple).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_violet).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_teal).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_yellow).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_blue).setVisibility(View.INVISIBLE);

                finalProductColor = "#F44336";
            }
        });

        findViewById(R.id.color_picker_purple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.product_color_red).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_purple).setVisibility(View.VISIBLE);
                findViewById(R.id.product_color_violet).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_teal).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_yellow).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_blue).setVisibility(View.INVISIBLE);

                finalProductColor = "#9C27B0";
            }
        });

        findViewById(R.id.color_picker_violet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.product_color_red).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_purple).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_violet).setVisibility(View.VISIBLE);
                findViewById(R.id.product_color_teal).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_yellow).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_blue).setVisibility(View.INVISIBLE);

                finalProductColor = "#673AB7";
            }
        });

        findViewById(R.id.color_picker_teal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.product_color_red).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_purple).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_violet).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_teal).setVisibility(View.VISIBLE);
                findViewById(R.id.product_color_yellow).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_blue).setVisibility(View.INVISIBLE);

                finalProductColor = "#009688";
            }
        });

        findViewById(R.id.color_picker_yellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.product_color_red).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_purple).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_violet).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_teal).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_yellow).setVisibility(View.VISIBLE);
                findViewById(R.id.product_color_blue).setVisibility(View.INVISIBLE);

                finalProductColor = "#FFEB3B";
            }
        });

        findViewById(R.id.color_picker_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.product_color_red).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_purple).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_violet).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_teal).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_yellow).setVisibility(View.INVISIBLE);
                findViewById(R.id.product_color_blue).setVisibility(View.VISIBLE);

                finalProductColor = "#03A9F4";
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setColors(int productImage) {
        Palette palette = Palette.from(getBitMap(productImage)).generate();
        productCardOne.setCardBackgroundColor(palette.getLightVibrantColor(getResources().getColor(R.color.colorPrimary,null)));
        cartButton.setCardBackgroundColor(palette.getLightVibrantColor(getResources().getColor(R.color.colorPrimary,null)));
    }

    public Bitmap getBitMap(int productImage) {
        Bitmap image = BitmapFactory.decodeResource(this.getResources(),productImage);
        return image;
    }

    public void showItemAddedDialog(View view) {
        itemAddedDialog.setContentView(R.layout.item_added_confirm_basket);

        itemAddedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        itemAddedDialog.show();
    }
}
