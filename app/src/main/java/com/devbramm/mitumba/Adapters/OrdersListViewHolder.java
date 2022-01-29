package com.devbramm.mitumba.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbramm.mitumba.Interface.OrderItemClickListener;
import com.devbramm.mitumba.R;

public class OrdersListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView orderItemID;
    public TextView orderItemsList;
    public TextView orderItemTotal;
    public TextView orderItemStatus;
    public TextView orderItemDate;

    private OrderItemClickListener orderItemClickListener;

    public OrdersListViewHolder(@NonNull View itemView) {
        super(itemView);

        orderItemID = itemView.findViewById(R.id.order_list_item_id);
        orderItemsList = itemView.findViewById(R.id.order_items_list);
        orderItemTotal = itemView.findViewById(R.id.order_list_item_total);
        orderItemStatus = itemView.findViewById(R.id.order_list_item_status);
        orderItemDate = itemView.findViewById(R.id.order_items_date);

        itemView.setOnClickListener(this);
    }

    public void setOrderItemClickListener(OrderItemClickListener orderItemClickListener) {
        this.orderItemClickListener = orderItemClickListener;
    }

    @Override
    public void onClick(View v) {
        orderItemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
