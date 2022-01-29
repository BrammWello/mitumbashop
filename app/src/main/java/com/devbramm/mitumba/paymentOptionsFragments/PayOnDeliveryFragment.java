package com.devbramm.mitumba.paymentOptionsFragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devbramm.mitumba.CheckoutActivity;
import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.devbramm.mitumba.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayOnDeliveryFragment extends Fragment {

    EditText cashOnDeliveryCheckoutName;
    EditText cashOnDeliveryCheckoutNumber;
    TextView cashOnDeliveryToPay;
    Button cashOnDeliveryBtn;

    ProgressDialog inputVariables;

    public PayOnDeliveryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_pay_on_delivery, container, false);

        cashOnDeliveryCheckoutName = view.findViewById(R.id.delivery_cash_names);
        cashOnDeliveryCheckoutNumber = view.findViewById(R.id.delivery_cash_number);
        cashOnDeliveryToPay = view.findViewById(R.id.delivery_cash_cart_total);
        cashOnDeliveryBtn = view.findViewById(R.id.confirm_cash_pay_btn);

        inputVariables = new ProgressDialog(view.getContext());

        cashOnDeliveryToPay.setText(String.valueOf(CommonUtils.cartCheckoutSubtotal));

        cashOnDeliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cashOnDeliveryCheckoutName.getText().toString() == "" || cashOnDeliveryCheckoutNumber.getText().toString() == "") {
                    Toast.makeText(view.getContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                } else {
                    CheckoutActivity.selectedCheckoutPaymentMethod = "cash_on_delivery";
                    CheckoutActivity.checkoutCashDeliveryName = cashOnDeliveryCheckoutName.getText().toString();
                    CheckoutActivity.checkoutCashDeliveryPhone = cashOnDeliveryCheckoutNumber.getText().toString();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        inputVariables.setMessage("Verifying");
                        inputVariables.show();
                    }
                },2000);

                inputVariables.dismiss();

                cashOnDeliveryBtn.setText("Verified");
            }
        });

        return view;
    }
}
