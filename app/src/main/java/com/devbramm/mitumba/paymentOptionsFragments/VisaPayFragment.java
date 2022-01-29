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
import android.widget.Toast;

import com.devbramm.mitumba.CheckoutActivity;
import com.devbramm.mitumba.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisaPayFragment extends Fragment {

    EditText visaPayNumber, visaPayName, visaPayMonth, visaPayCVV;
    Button visPayConfirmBtn;

    ProgressDialog inputVariables;

    public VisaPayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_visa_pay, container, false);

        visaPayNumber = view.findViewById(R.id.visa_pay_card_number);
        visaPayMonth = view.findViewById(R.id.visa_pay_month_input);
        visaPayCVV = view.findViewById(R.id.visa_pay_cvv_input);
        visaPayName = view.findViewById(R.id.visa_pay_name_input);
        visPayConfirmBtn = view.findViewById(R.id.confirm_visa_pay_btn);

        inputVariables = new ProgressDialog(view.getContext());

        visPayConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String visaNumber = visaPayNumber.getText().toString();
                String visaMonth = visaPayMonth.getText().toString();
                String visaCVV = visaPayCVV.getText().toString();
                String visaName = visaPayName.getText().toString();

                if (visaNumber == "" || visaMonth == "" || visaCVV == "" || visaName == "") {
                    Toast.makeText(view.getContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                } else {
                    CheckoutActivity.selectedCheckoutPaymentMethod = "visa_pay";

                    CheckoutActivity.checkoutVisaNumber = visaNumber;
                    CheckoutActivity.checkoutVisaMMYY = visaMonth;
                    CheckoutActivity.checkoutVisaCVV = visaCVV;
                    CheckoutActivity.checkoutVisaName = visaName;
                }



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        inputVariables.setMessage("Verifying");
                        inputVariables.show();
                    }
                },2000);

                inputVariables.dismiss();

                visPayConfirmBtn.setText("Verified");

            }
        });


    return view;
    }

}
