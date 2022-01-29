package com.devbramm.mitumba.CheckoutActivityFragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.devbramm.mitumba.Adapters.AddressRequestViewHolder;
import com.devbramm.mitumba.CheckoutActivity;
import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.devbramm.mitumba.Interface.AddressItemClickListener;
import com.devbramm.mitumba.Models.AddressRequest;
import com.devbramm.mitumba.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingFragment extends Fragment {

    ConstraintLayout contactDetailsLayout;
    CardView mitumbaExpressOption, mitumbaNoHurryOption;
    ImageView mitumbaExpressSelected, mitumbaNoHurrySelected;
    FirebaseAuth mAuth;

    EditText checkoutContactName;
    EditText checkoutContactPhone;
    EditText checkoutAddressInput;
    EditText checkoutOrderCounty;
    EditText checkoutOrderAdditionalInfo;

    Dialog addressItemsCheckoutDialog;

    DatabaseReference addressesReferenceDialog;

    FirebaseRecyclerAdapter<AddressRequest, AddressRequestViewHolder> adapterCheckoutAddresses;
    RecyclerView addressItemsCheckoutRecycler;

    public ShippingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //contactDetailsLayout.setVisibility(View.VISIBLE);

        if(CheckoutActivity.shippingOptionsSelected) {
            checkoutContactName.setText(CheckoutActivity.checkoutOrderName);
            checkoutContactPhone.setText(CheckoutActivity.checkoutPhone);
            checkoutAddressInput.setText(CheckoutActivity.checkoutAddress);
            checkoutOrderCounty.setText(CheckoutActivity.checkoutCount);
            checkoutOrderAdditionalInfo.setText(CheckoutActivity.checkoutAdditionalInfo);
        }

        if(CheckoutActivity.deliveryOptionSelected == 1) {
            contactDetailsLayout.setVisibility(View.VISIBLE);
            mitumbaExpressSelected.setVisibility(View.VISIBLE);
            mitumbaNoHurrySelected.setVisibility(View.INVISIBLE);
        } else if (CheckoutActivity.deliveryOptionSelected == 2) {
            contactDetailsLayout.setVisibility(View.VISIBLE);
            mitumbaExpressSelected.setVisibility(View.INVISIBLE);
            mitumbaNoHurrySelected.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_shipping, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        checkoutContactName = view.findViewById(R.id.checkout_name);
        checkoutContactPhone = view.findViewById(R.id.checkout_phone);
        checkoutAddressInput = view.findViewById(R.id.checkout_address);
        checkoutOrderCounty = view.findViewById(R.id.checkout_city);
        checkoutOrderAdditionalInfo = view.findViewById(R.id.checkout_town);


        addressItemsCheckoutDialog = new Dialog(view.getContext());

        addressItemsCheckoutDialog.setContentView(R.layout.checkout_address_picker_dialog);
        addressItemsCheckoutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        addressItemsCheckoutRecycler = addressItemsCheckoutDialog.findViewById(R.id.checkout_address_picker_recycler);
        addressItemsCheckoutRecycler.setLayoutManager(new LinearLayoutManager(addressItemsCheckoutDialog.getContext()));


        addressesReferenceDialog = FirebaseDatabase.getInstance().getReference("customer_addresses").child(CommonUtils.currentUserUid);


        mitumbaExpressOption = view.findViewById(R.id.mitumba_express_card);
        mitumbaNoHurryOption = view.findViewById(R.id.mitumba_no_hurry_card);
        contactDetailsLayout = view.findViewById(R.id.contact_details_layout);
        mitumbaExpressSelected = view.findViewById(R.id.mitumba_express_selected);
        mitumbaNoHurrySelected = view.findViewById(R.id.mitumba_no_hurry_selected);



        mitumbaExpressOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckoutActivity.deliveryOptionSelected = 1;
                loadAddressesForCheckout();

                contactDetailsLayout.setVisibility(View.VISIBLE);
                mitumbaExpressSelected.setVisibility(View.VISIBLE);
                mitumbaNoHurrySelected.setVisibility(View.INVISIBLE);
            }
        });

        mitumbaNoHurryOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckoutActivity.deliveryOptionSelected = 2;

                contactDetailsLayout.setVisibility(View.VISIBLE);
                mitumbaExpressSelected.setVisibility(View.INVISIBLE);
                mitumbaNoHurrySelected.setVisibility(View.VISIBLE);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void loadAddressesForCheckout() {
        Query addressesQuery = addressesReferenceDialog.orderByKey();
        FirebaseRecyclerOptions addressesOptions = new FirebaseRecyclerOptions.Builder<AddressRequest>().setQuery(addressesQuery,AddressRequest.class).build();



        adapterCheckoutAddresses = new FirebaseRecyclerAdapter<AddressRequest, AddressRequestViewHolder>(addressesOptions) {
            @Override
            protected void onBindViewHolder(@NonNull AddressRequestViewHolder addressRequestViewHolder, int i, @NonNull final AddressRequest addressRequest) {
                addressRequestViewHolder.addressItemName.setText(addressRequest.getAddressRequestFirstName() + " " + addressRequest.getAddressRequestLastName());
                addressRequestViewHolder.addressItemLocation.setText(addressRequest.getAddressRequestLocation());
                addressRequestViewHolder.addressItemAdditionalInfo.setText(addressRequest.getAddressRequestAdditionalInfo());
                addressRequestViewHolder.addressItemCounty.setText(addressRequest.getAddressRequestCounty());

                addressRequestViewHolder.addressItemPhones.setText("Tel 1: " + addressRequest.getAddressRequestMobilePhone());

                //test to see if there are additional phones before setting text
                if (addressRequest.getAddressRequestAdditionalPhone().isEmpty()) {
                    addressRequestViewHolder.addressItemPhones.setText("Tel 1: " + addressRequest.getAddressRequestMobilePhone());
                } else {
                    addressRequestViewHolder.addressItemPhones.setText("Tel 1: " + addressRequest.getAddressRequestMobilePhone() + ", Tel 2:" + addressRequest.getAddressRequestAdditionalPhone());
                }

                //test to see if its the preferred location
                if (addressRequest.getAddressRequestDefaultAddress()) {
                    addressRequestViewHolder.addressItemPreferredLocation.setVisibility(View.VISIBLE);
                } else if (!addressRequest.getAddressRequestDefaultAddress()) {
                    addressRequestViewHolder.addressItemPreferredLocation.setVisibility(View.INVISIBLE);
                }

                addressRequestViewHolder.setAddressItemClickListener(new AddressItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        CheckoutActivity.checkoutOrderName = addressRequest.getAddressRequestFirstName() + " " + addressRequest.getAddressRequestLastName();
                        checkoutContactName.setText(CheckoutActivity.checkoutOrderName);
                        CheckoutActivity.checkoutPhone = addressRequest.getAddressRequestMobilePhone();
                        checkoutContactPhone.setText(CheckoutActivity.checkoutPhone);
                        CheckoutActivity.checkoutAddress = addressRequest.getAddressRequestLocation();
                        checkoutAddressInput.setText(CheckoutActivity.checkoutAddress);
                        CheckoutActivity.checkoutCount = addressRequest.getAddressRequestCounty();
                        checkoutOrderCounty.setText(CheckoutActivity.checkoutCount);
                        CheckoutActivity.checkoutAdditionalInfo = addressRequest.getAddressRequestAdditionalInfo();
                        checkoutOrderAdditionalInfo.setText(CheckoutActivity.checkoutAdditionalInfo);
                        CheckoutActivity.shippingOptionsSelected = true;

                        addressItemsCheckoutDialog.dismiss();
                    }
                });

            }

            @NonNull
            @Override
            public AddressRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.address_line_item,parent,false);

                return new AddressRequestViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
//                adapterCheckoutAddresses.stopListening();
            }
        };
        addressItemsCheckoutRecycler.setAdapter(adapterCheckoutAddresses);
        adapterCheckoutAddresses.startListening();


        addressItemsCheckoutDialog.show();


    }

}
