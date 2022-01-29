package com.devbramm.mitumba.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.devbramm.mitumba.Interface.AddressItemClickListener;
import com.devbramm.mitumba.R;

public class AddressRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView addressItemName;
    public TextView addressItemLocation;
    public TextView addressItemAdditionalInfo;
    public TextView addressItemPhones;
    public TextView addressItemCounty;
    public ConstraintLayout addressItemPreferredLocation;

    private AddressItemClickListener addressItemClickListener;

    public AddressRequestViewHolder(@NonNull View itemView) {
        super(itemView);

        addressItemName = itemView.findViewById(R.id.address_item_name);
        addressItemLocation = itemView.findViewById(R.id.address_item_location);
        addressItemAdditionalInfo = itemView.findViewById(R.id.address_item_addInfo);
        addressItemPhones = itemView.findViewById(R.id.address_item_phones);
        addressItemCounty = itemView.findViewById(R.id.address_item_county);
        addressItemPreferredLocation = itemView.findViewById(R.id.address_item_preferred_location);

        itemView.setOnClickListener(this);

    }

    public void setAddressItemClickListener(AddressItemClickListener addressItemClickListener) {
        this.addressItemClickListener = addressItemClickListener;
    }

    @Override
    public void onClick(View v) {
        addressItemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
