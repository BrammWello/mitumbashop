package com.devbramm.mitumba;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devbramm.mitumba.Adapters.OrdersListViewHolder;
import com.devbramm.mitumba.Adapters.ProductItemViewHolder;
import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.devbramm.mitumba.Interface.OrderItemClickListener;
import com.devbramm.mitumba.Interface.ProductItemClickListener;
import com.devbramm.mitumba.Models.Cart;
import com.devbramm.mitumba.Models.OrderRequest;
import com.devbramm.mitumba.Models.ProductList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class  OrdersActivity extends AppCompatActivity {

    TextView orderPrice, totalOrdersCounter;
    ProgressBar loadingOrderItems;
    FirebaseRecyclerAdapter<OrderRequest, OrdersListViewHolder> ordersAdapter;
    RecyclerView ordersRecycler;
    FirebaseDatabase database;
    DatabaseReference ordersListReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        orderPrice = findViewById(R.id.order_list_item_total);
        loadingOrderItems = findViewById(R.id.loading_order_items);
        totalOrdersCounter = findViewById(R.id.total_orders_counter);

        database = FirebaseDatabase.getInstance();
        ordersListReference = database.getReference("customer_orders").child(CommonUtils.currentUserUid);

        ordersRecycler = findViewById(R.id.orders_list_recycler);
        ordersRecycler.setLayoutManager(new LinearLayoutManager(this));
        //load the items from database now
        loadOrderItems();

//        final ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,orderPrice,"price");

        findViewById(R.id.btn_orders_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadOrderItems() {


        final Calendar calendar = Calendar.getInstance();
        Query ordersQuery = ordersListReference.orderByKey();
        FirebaseRecyclerOptions ordersOptions = new FirebaseRecyclerOptions.Builder<OrderRequest>().setQuery(ordersQuery, OrderRequest.class).build();

        ordersAdapter = new FirebaseRecyclerAdapter<OrderRequest, OrdersListViewHolder>(ordersOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final OrdersListViewHolder ordersListViewHolder, int i, @NonNull final OrderRequest orderRequest) {
                String orderID = ordersAdapter.getRef(i).getKey();

                ordersListViewHolder.orderItemID.setText("ID: " + orderID);

                List<Cart> cartItems = orderRequest.getProducts();

                //calculate Total Price in Cart
                String itemsString = "";
                for (Cart order:cartItems)
                {
                    itemsString+=(order.getProductName() + ", ");
                }

                Locale locale = new Locale("en", "KE");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                ordersListViewHolder.orderItemTotal.setText(fmt.format(Float.parseFloat(orderRequest.getTotal())));

                ordersListViewHolder.orderItemsList.setText(itemsString);

                long rawTimestamp = Long.parseLong(orderID.substring(0,13));

                calendar.setTimeInMillis(rawTimestamp);

                ordersListViewHolder.orderItemDate.setText(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));

                ordersListViewHolder.setOrderItemClickListener(new OrderItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent toProduct = new Intent(OrdersActivity.this, OrderDetailsActivity.class);
                        toProduct.putExtra("orderID",ordersAdapter.getRef(position).getKey());

//                        Pair<View, String> pair1 = Pair.create((View)productItemViewHolder.productImage,)

                        //ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(ProductGridActivity.this, productItemViewHolder.productImage,"sharedName");
                        startActivity(toProduct);
                    }
                });
            }

            @NonNull
            @Override
            public OrdersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.orders_list_item,parent,false);

                return new OrdersListViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                loadingOrderItems.setVisibility(View.INVISIBLE);
            }
        };
        ordersRecycler.setAdapter(ordersAdapter);
        ordersAdapter.startListening();
        totalOrdersCounter.setText("Total Orders: " + ordersAdapter.getItemCount());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ordersAdapter.stopListening();
    }
}
