package com.devbramm.mitumba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.btn_your_orders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toOrders = new Intent(SettingsActivity.this, OrdersActivity.class);
                startActivity(toOrders);
            }
        });

        findViewById(R.id.btn_address_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddressBook = new Intent(SettingsActivity.this, AddressBookActivity.class);
                startActivity(toAddressBook);
            }
        });

        findViewById(R.id.btn_favorite_items).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFavoriteItems = new Intent(SettingsActivity.this, FavoritesActivity.class);
                startActivity(toFavoriteItems);
            }
        });

        findViewById(R.id.btn_login_and_security).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginAndSecurity = new Intent(SettingsActivity.this, LoginSecurityActivity.class);
                startActivity(toLoginAndSecurity);
            }
        });

        findViewById(R.id.btn_payment_options).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPaymentOptions = new Intent(SettingsActivity.this, PaymentOptionsActivity.class);
                startActivity(toPaymentOptions);
            }
        });

        findViewById(R.id.btn_subscriptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSubscriptions = new Intent(SettingsActivity.this, SubscriptionsActivity.class);
                startActivity(toSubscriptions);
            }
        });

        findViewById(R.id.btn_faqs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFrequentlyAskedQuestions = new Intent(SettingsActivity.this, FAQSActivity.class);
                startActivity(toFrequentlyAskedQuestions);
            }
        });

        findViewById(R.id.btn_tsandcs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTermsAndConditions = new Intent(SettingsActivity.this, TermsAndConditionsActivity.class);
                startActivity(toTermsAndConditions);
            }
        });

        findViewById(R.id.btn_profile_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
