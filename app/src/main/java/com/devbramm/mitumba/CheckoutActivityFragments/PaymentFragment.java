package com.devbramm.mitumba.CheckoutActivityFragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devbramm.mitumba.CheckoutActivity;
import com.devbramm.mitumba.R;
import com.devbramm.mitumba.paymentOptionsFragments.AirtelMoneyFragment;
import com.devbramm.mitumba.paymentOptionsFragments.EquitelFragment;
import com.devbramm.mitumba.paymentOptionsFragments.MpesaFragment;
import com.devbramm.mitumba.paymentOptionsFragments.PayOnDeliveryFragment;
import com.devbramm.mitumba.paymentOptionsFragments.VisaPayFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    CardView cashOnDelivery, mPesaPay, airtelPay, equitelPay, visaPay;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_payment, container, false);

        cashOnDelivery = view.findViewById(R.id.cash_on_delivery_card);
        mPesaPay = view.findViewById(R.id.mpesa_card);
        //airtelPay = view.findViewById(R.id.airtel_card);
        equitelPay = view.findViewById(R.id.equitel_card);
        visaPay = view.findViewById(R.id.visa_card);

        cashOnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load Fragment
                loadFragment(new PayOnDeliveryFragment());
            }
        });

        mPesaPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load Fragment
                loadFragment(new MpesaFragment());
            }
        });

//        airtelPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //load Fragment
//                loadFragment(new AirtelMoneyFragment());
//            }
//        });

        equitelPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load Fragment
                loadFragment(new EquitelFragment());
            }
        });

        visaPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load Fragment
                loadFragment(new VisaPayFragment());
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.payment_options_fragment, fragment);
        fragmentTransaction.commit(); // save the changes
    }

}