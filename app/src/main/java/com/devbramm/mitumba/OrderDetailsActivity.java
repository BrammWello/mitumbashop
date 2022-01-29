package com.devbramm.mitumba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.devbramm.mitumba.Adapters.OrderItemAdapter;
import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.devbramm.mitumba.Models.Cart;
import com.devbramm.mitumba.Models.OrderRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView orderDetailID, orderDetailPrice, orderDetailStatus, orderDetailNames, orderDetailPhone, orderDetailAddress, orderDetailShippingOption;

    DatabaseReference orderReference;
    String orderID = "";

    OrderRequest currentOrder;
    List<Cart> orderItems;

    OrderItemAdapter orderItemsAdapter;

    RecyclerView orderItemsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderDetailID = findViewById(R.id.order_detail_ID);
        orderDetailPrice = findViewById(R.id.order_detail_price);
        orderDetailStatus = findViewById(R.id.order_detail_status);
        orderDetailNames = findViewById(R.id.order_detail_name);
        orderDetailPhone = findViewById(R.id.order_detail_phone);
        orderDetailAddress = findViewById(R.id.order_detail_address);
        orderDetailShippingOption = findViewById(R.id.order_detail_shipping_option);
        orderItemsRecycler = findViewById(R.id.order_details_item_recycler);

        orderItemsRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        orderItemsRecycler.setHasFixedSize(true);

        findViewById(R.id.btn_order_details_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //getProductDetailsClicked
        if (getIntent() != null) {
            orderID = getIntent().getStringExtra("orderID");
        } else {
            finish();
        }

        orderReference = FirebaseDatabase.getInstance().getReference("customer_orders").child(CommonUtils.currentUserUid).child(orderID);

        orderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentOrder = dataSnapshot.getValue(OrderRequest.class);

                orderDetailID.setText(orderID);
                orderDetailPrice.setText(currentOrder.getTotal());
                orderDetailStatus.setText(currentOrder.getPaymentStatus());
                orderDetailNames.setText(currentOrder.getName());
                orderDetailPhone.setText(currentOrder.getPhone());
                orderDetailAddress.setText(currentOrder.getAddress());

                orderItems = currentOrder.getProducts();

                orderItemsAdapter = new OrderItemAdapter(getBaseContext(),orderItems);
                orderItemsRecycler.setAdapter(orderItemsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
