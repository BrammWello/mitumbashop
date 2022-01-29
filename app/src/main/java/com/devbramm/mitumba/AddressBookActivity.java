package com.devbramm.mitumba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.devbramm.mitumba.Adapters.AddressRequestViewHolder;
import com.devbramm.mitumba.Adapters.ProductItemViewHolder;
import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.devbramm.mitumba.Models.AddressRequest;
import com.devbramm.mitumba.Models.ProductList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddressBookActivity extends AppCompatActivity {

    Dialog newAddressDialog;
    ProgressBar addressLoadingBar;

    FirebaseDatabase database;
    DatabaseReference addressesReference;
    String selectedCounty = "";
    ProgressDialog newAddressProgressDialog;

    FirebaseRecyclerAdapter<AddressRequest, AddressRequestViewHolder> adapterAddresses;
    RecyclerView addressItemsRecycler;

    @Override
    protected void onStart() {
        super.onStart();
        adapterAddresses.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_bokk);

        addressLoadingBar = findViewById(R.id.address_loading_progress);

        addressItemsRecycler = findViewById(R.id.address_line_recycler);
        addressItemsRecycler.setLayoutManager(new LinearLayoutManager(this));

        newAddressDialog = new Dialog(this);
        newAddressProgressDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        addressesReference = database.getReference("customer_addresses").child(CommonUtils.currentUserUid);

        findViewById(R.id.btn_address_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.add_new_address_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddressDialog(v);
            }
        });

        loadAddressesFromDB();
    }

    private void loadAddressesFromDB() {
        Query addressesQuery = addressesReference.orderByKey();
        FirebaseRecyclerOptions addressesOptions = new FirebaseRecyclerOptions.Builder<AddressRequest>().setQuery(addressesQuery,AddressRequest.class).build();

        adapterAddresses = new FirebaseRecyclerAdapter<AddressRequest, AddressRequestViewHolder>(addressesOptions) {
            @Override
            protected void onBindViewHolder(@NonNull AddressRequestViewHolder addressRequestViewHolder, int i, @NonNull AddressRequest addressRequest) {
                addressRequestViewHolder.addressItemName.setText(addressRequest.getAddressRequestFirstName() + " " + addressRequest.getAddressRequestLastName());
                addressRequestViewHolder.addressItemLocation.setText(addressRequest.getAddressRequestLocation());
                addressRequestViewHolder.addressItemAdditionalInfo.setText(addressRequest.getAddressRequestAdditionalInfo());
                addressRequestViewHolder.addressItemCounty.setText(addressRequest.getAddressRequestCounty());

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
                addressLoadingBar.setVisibility(View.INVISIBLE);
            }
        };
        addressItemsRecycler.setAdapter(adapterAddresses);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapterAddresses.stopListening();
    }

    private void showAddressDialog (View v) {
        newAddressDialog.setContentView(R.layout.new_address_dialog);

        final EditText addressFirstName = newAddressDialog.findViewById(R.id.address_first_name);
        final EditText addressLastName = newAddressDialog.findViewById(R.id.address_last_name);
        final EditText addressLocation = newAddressDialog.findViewById(R.id.address_location);
        final EditText addressAdditionalInfo = newAddressDialog.findViewById(R.id.address_additional_info);
        final EditText addressMobilePhone = newAddressDialog.findViewById(R.id.address_mobile_phone);
        final EditText addressAdditionalPhone = newAddressDialog.findViewById(R.id.address_additional_mobile_phone);
        final CheckBox defaultAddressCheckbox = newAddressDialog.findViewById(R.id.default_address_checkbox);
        ConstraintLayout saveNewAddress = newAddressDialog.findViewById(R.id.save_new_address_btn);

        Spinner countiesSpinner = newAddressDialog.findViewById(R.id.counties_spinner);
        ArrayAdapter<CharSequence> countiesStringAdapter = ArrayAdapter.createFromResource(AddressBookActivity.this,R.array.counties_array,android.R.layout.simple_spinner_item);
        countiesStringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countiesSpinner.setAdapter(countiesStringAdapter);

        countiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCounty = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newAddressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        newAddressDialog.show();

        saveNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputFirstName = addressFirstName.getText().toString();
                String inputLastName = addressLastName.getText().toString();
                String inputLocation = addressLocation.getText().toString();
                String inputAdditionalInfo = addressAdditionalInfo.getText().toString();
                String inputMobilePhone = addressMobilePhone.getText().toString();
                String inputAdditionalPhone = addressAdditionalPhone.getText().toString();

//                boolean defaultAddressChecker = false;
//
//                if (defaultAddressCheckbox.isChecked()) {
//                    defaultAddressChecker = true;
//                } else if (!defaultAddressCheckbox.isChecked()) {
//                    defaultAddressChecker = false;
//                }


                if (inputFirstName.equals("")) {
                    addressFirstName.setError("Required");
                } else if (inputLastName.equals("")) {
                    addressLastName.setError("Required");
                } else if (inputLocation.equals("")) {
                    addressLocation.setError("Required");
                } else if (inputAdditionalInfo.equals("")) {
                    addressAdditionalInfo.setError("Required");
                } else if (inputMobilePhone.equals("")) {
                    addressMobilePhone.setError("Required");
                } else {
                    //assert that county value is not empty
                    if (selectedCounty.equals("")) {
                        Toast.makeText(AddressBookActivity.this, "Select a County", Toast.LENGTH_SHORT).show();
                    } else {
                        newAddressProgressDialog.setMessage("Just a sec...");
                        newAddressProgressDialog.show();

                        sendNewAddressToDB(inputFirstName,inputLastName,inputLocation,inputAdditionalInfo,selectedCounty,inputMobilePhone,inputAdditionalPhone,false);
                    }
                }
            }

//            private void testDefaultAddress() {
//                //test whether there is a preferred location first and remove if result is true
//                Query defaultAddressCheckerRef = addressesReference.orderByChild("addressRequestDefaultAddress").equalTo(true);
//                defaultAddressCheckerRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//
//                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                String keyId = ds.getKey();
//                                DatabaseReference toChangeRef = addressesReference.child(keyId);
//                                Map<String,Object> childUpdater = new HashMap<>();
//                                childUpdater.put("addressRequestDefaultAddress",false);
//                                toChangeRef.updateChildren(childUpdater).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        continueToSendAddress();
//                                    }
//                                });
//                            }
//                        } else if (!dataSnapshot.exists()){
//                            continueToSendAddress();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }

        });
    }

    private void sendNewAddressToDB(String inputFirstName, String inputLastName, String inputLocation, String inputAdditionalInfo, String selectedCounty, String inputMobilePhone, String inputAdditionalPhone, Boolean defaultAddressChecker) {

        AddressRequest newAddressInfo = new AddressRequest(inputFirstName,inputLastName,inputLocation,inputAdditionalInfo,selectedCounty,inputMobilePhone,inputAdditionalPhone,defaultAddressChecker);

        addressesReference.push().setValue(newAddressInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                newAddressProgressDialog.dismiss();
                newAddressDialog.dismiss();
                Toast.makeText(AddressBookActivity.this, "New Address Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                newAddressDialog.dismiss();
                newAddressDialog.dismiss();
                Toast.makeText(AddressBookActivity.this, "Sorry " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
