package com.devbramm.mitumba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.devbramm.mitumba.paymentOptionsFragments.AirtelMoneyFragment;
import com.devbramm.mitumba.paymentOptionsFragments.EquitelFragment;
import com.devbramm.mitumba.paymentOptionsFragments.MpesaFragment;
import com.devbramm.mitumba.paymentOptionsFragments.VisaPayFragment;

public class PaymentOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);

        findViewById(R.id.default_mpesa_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new MpesaFragment());
            }
        });

        findViewById(R.id.default_airtel_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AirtelMoneyFragment());
            }
        });

        findViewById(R.id.default_equitel_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new EquitelFragment());
            }
        });

        findViewById(R.id.default_visa_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new VisaPayFragment());
            }
        });

        findViewById(R.id.btn_payment_options_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.payment_options_default_fragment, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
