package com.devbramm.mitumba;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.devbramm.mitumba.Adapters.CartProductsAdapter;
import com.devbramm.mitumba.Adapters.CartViewHolder;
import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.devbramm.mitumba.Database.CartDatabase;
import com.devbramm.mitumba.Helpers.RecyclerItemTouchHelper;
import com.devbramm.mitumba.Interface.RecyclerItemTouchHelperListener;
import com.devbramm.mitumba.Models.Cart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    CardView checkoutBtn;


    CartProductsAdapter cartProductsAdapter;

    RecyclerView cartItemsRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView cartSubTotal;

    ImageView temporaryClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checkoutBtn = findViewById(R.id.checkout_button);
        cartItemsRecyclerView = findViewById(R.id.cart_items_recycler);
        temporaryClear = findViewById(R.id.temporary_clear);
        cartSubTotal = findViewById(R.id.cart_sub_total);

        temporaryClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cartItemsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        cartItemsRecyclerView.setLayoutManager(layoutManager);

        //swipe to delete
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(cartItemsRecyclerView);

        loadCartProducts();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCheckout = new Intent(CartActivity.this,CheckoutActivity.class);
                startActivity(toCheckout);
            }
        });

        findViewById(R.id.btn_checkout_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadCartProducts() {
        CommonUtils.cartItems = new CartDatabase(this).getCarts();
        cartProductsAdapter = new CartProductsAdapter(CommonUtils.cartItems,CartActivity.this);
        cartItemsRecyclerView.setAdapter(cartProductsAdapter);

        //calculate Total Price in Cart
        float total = 0;
        for (Cart order:CommonUtils.cartItems)
        {
            total+=(Float.parseFloat(order.getPrice()))*(Float.parseFloat(order.getQuantity()));
        }

        Locale locale = new Locale("en", "KE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        CommonUtils.cartCheckoutSubtotal = total;

        cartSubTotal.setText(fmt.format(total));
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartViewHolder) {
//            String name = ((CartProductsAdapter)cartItemsRecyclerView.getAdapter()).
        }
    }
}
