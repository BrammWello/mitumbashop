package com.devbramm.mitumba.paymentOptionsFragments;


import android.app.Dialog;
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
public class MpesaFragment extends Fragment {

    EditText mpesaPayPhoneInput;
    TextView mpesaToPayAmount;
    Button mpesaPayBtn;
    ProgressDialog inputVariables;

    public MpesaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_mpesa, container, false);

        inputVariables = new ProgressDialog(view.getContext());

        mpesaPayPhoneInput = view.findViewById(R.id.mpesa_pay_phone_input);
        mpesaToPayAmount = view.findViewById(R.id.mpesa_to_pay_amount);
        mpesaPayBtn = view.findViewById(R.id.confirm_mpesa_pay_btn);

        mpesaToPayAmount.setText(String.valueOf(CommonUtils.cartCheckoutSubtotal));

        mpesaPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mpesaPayPhoneInput.getText().toString() == "") {
                    Toast.makeText(view.getContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                }
                else {
                    CheckoutActivity.selectedCheckoutPaymentMethod = "mpesa_pay";
                    CheckoutActivity.checkoutMpesaPhone = mpesaPayPhoneInput.getText().toString();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        inputVariables.setMessage("Verifying");
                        inputVariables.show();
                    }
                },2000);

                inputVariables.dismiss();

                mpesaPayBtn.setText("Verified");
            }
        });

        return view;
    }

}
