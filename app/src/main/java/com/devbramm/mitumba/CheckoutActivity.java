package com.devbramm.mitumba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devbramm.mitumba.CheckoutActivityFragments.ConfirmFragment;
import com.devbramm.mitumba.CheckoutActivityFragments.PaymentFragment;
import com.devbramm.mitumba.CheckoutActivityFragments.ShippingFragment;
import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.devbramm.mitumba.Database.CartDatabase;
import com.devbramm.mitumba.Models.OrderRequest;
import com.devbramm.mitumba.Models.PaymentMethodsModels.CashOnDelivery;
import com.devbramm.mitumba.Models.PaymentMethodsModels.MpesaPaymentModel;
import com.devbramm.mitumba.Models.PaymentMethodsModels.VisaPayModel;
import com.devbramm.mitumba.paymentOptionsFragments.PayOnDeliveryFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CheckoutActivity extends AppCompatActivity {

    ConstraintLayout backStep, nextStep;
    int stepCounter = 0;
    public static int deliveryOptionSelected;
    public static FirebaseDatabase database;
    public static DatabaseReference ordersRequestReference;
    public static DatabaseReference paymentRequestReference;
    public static String checkoutOrderName;
    public static String checkoutPhone;
    public static String checkoutAddress = "";
    public static String checkoutCount;
    public static String checkoutAdditionalInfo;
    public static boolean shippingOptionsSelected = false;
    public static String selectedCheckoutPaymentMethod = "";

    //vars to hold payment details
    //cashOnDelivery
    public static String checkoutCashDeliveryName;
    public static String checkoutCashDeliveryPhone;
    //mpesa
    public static String checkoutMpesaPhone;
    //visa
    public static String checkoutVisaName;
    public static String checkoutVisaMMYY;
    public static String checkoutVisaCVV;
    public static String checkoutVisaNumber;

    ProgressDialog orderRequestProcessingDialog;

    //vars to show progress at top of process
    ImageView progressShipping, progressPayment, progressConfirm;
    View progressShippingDone, progressPaymentDone;
    TextView progessShippingText, progressPaymentText, progressConfirmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        backStep = findViewById(R.id.back_step);
        nextStep = findViewById(R.id.next_step);

        progressShipping = findViewById(R.id.checkout_shipping_icon);
        progressPayment = findViewById(R.id.checkout_shipping_payment_icon);
        progressConfirm = findViewById(R.id.checkout_confirm_icon);
        progressShippingDone = findViewById(R.id.checkout_shipping_done_icon);
        progressPaymentDone = findViewById(R.id.checkout_payment_done_icon);
        progessShippingText = findViewById(R.id.checkout_shipping_text);
        progressPaymentText = findViewById(R.id.checkout_payment_text);
        progressConfirmText = findViewById(R.id.checkout_confirm_text);

        orderRequestProcessingDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        ordersRequestReference =  database.getReference("customer_orders").child(CommonUtils.currentUserUid);


        findViewById(R.id.btn_checkout_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (stepCounter == 0) {
            //load First Fragment
            loadFragment(new ShippingFragment());
            stepCounter = 1;

            progressShipping.setColorFilter(getResources().getColor(R.color.colorAccent));
            progessShippingText.setTextColor(getResources().getColor(R.color.colorAccent));
        }

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepCounter == 0) {
                    //load First Fragment
                    loadFragment(new ShippingFragment());
                    stepCounter = 1;
                }
                else if (stepCounter == 1) {
                    if (checkoutAddress == "") {
                        Toast.makeText(CheckoutActivity.this, "Select an address or create a new one first in addresses menu", Toast.LENGTH_SHORT).show();
                    } else {

                        progressShippingDone.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        progressPayment.setColorFilter(getResources().getColor(R.color.colorAccent));
                        progressPaymentText.setTextColor(getResources().getColor(R.color.colorAccent));

                        // load Second Fragment
                        loadFragment(new PaymentFragment());
                        Toast.makeText(CheckoutActivity.this, "Please select your preferred Payment option", Toast.LENGTH_SHORT).show();
                        stepCounter = 2;
                    }

                }
                else if (stepCounter == 2) {
                    // load Third Fragment
                    if (selectedCheckoutPaymentMethod == "") {
                        Toast.makeText(CheckoutActivity.this, "Select a payment Method", Toast.LENGTH_SHORT).show();
                    } else {
                        //submit the request to firebase now
                        orderRequestProcessingDialog.setMessage("Please Wait");
                        orderRequestProcessingDialog.show();
                        createNewOrderRequest();
                    }
                }
            }
        });

        backStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepCounter == 3) {
                    //load First Fragment
                    loadFragment(new PaymentFragment());
                    Toast.makeText(CheckoutActivity.this, "Please select your preferred Payment option", Toast.LENGTH_SHORT).show();
                    stepCounter = 2;
                }
                else if (stepCounter == 2) {
                    // load First Fragment
                    loadFragment(new ShippingFragment());
                    stepCounter = 1;
                }
            }
        });
    }

    private void createNewOrderRequest() {

        Random randomCartSuffix = new Random();
        final String cartUID = System.currentTimeMillis() + String.format("%04d", randomCartSuffix.nextInt(10000));

        Random randomPaymentSuffix = new Random();
        final String paymentUID = System.currentTimeMillis() + String.format("%04d", randomPaymentSuffix.nextInt(10000));

        paymentRequestReference =  database.getReference("customer_payments").child(selectedCheckoutPaymentMethod).child(CommonUtils.currentUserUid);

        OrderRequest newOrderRequest = new OrderRequest(checkoutOrderName,checkoutPhone,checkoutAddress,String.valueOf(CommonUtils.cartCheckoutSubtotal),paymentUID, "waiting_payment",selectedCheckoutPaymentMethod,CommonUtils.cartItems);

        ordersRequestReference.child(cartUID).setValue(newOrderRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (selectedCheckoutPaymentMethod == "cash_on_delivery") {
                        CashOnDelivery cashOnDeliveryDetails = new CashOnDelivery(cartUID,String.valueOf(CommonUtils.cartCheckoutSubtotal),checkoutCashDeliveryName,checkoutCashDeliveryPhone);

                        paymentRequestReference.child(paymentUID).setValue(cashOnDeliveryDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                orderRequestProcessingDialog.dismiss();
                                //remove everything from the cart now
                                new CartDatabase(getBaseContext()).cleanCart();
                                loadFragment(new ConfirmFragment());
                            }
                        });
                    } else if (selectedCheckoutPaymentMethod == "mpesa_pay") {
                        MpesaPaymentModel mpesaPayDetails = new MpesaPaymentModel(cartUID,String.valueOf(CommonUtils.cartCheckoutSubtotal),checkoutMpesaPhone);

                        paymentRequestReference.child(paymentUID).setValue(mpesaPayDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                orderRequestProcessingDialog.dismiss();
                                //remove everything from the cart now
                                new CartDatabase(getBaseContext()).cleanCart();
                                loadFragment(new ConfirmFragment());
                            }
                        });
                    } else if (selectedCheckoutPaymentMethod == "visa_pay") {
                        VisaPayModel visaPayDetails = new VisaPayModel(cartUID,String.valueOf(CommonUtils.cartCheckoutSubtotal),checkoutVisaNumber,checkoutVisaMMYY,checkoutVisaCVV,checkoutVisaName);

                        paymentRequestReference.child(paymentUID).setValue(visaPayDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                orderRequestProcessingDialog.dismiss();
                                //remove everything from the cart now
                                new CartDatabase(getBaseContext()).cleanCart();
                                loadFragment(new ConfirmFragment());
                            }
                        });
                    }
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.fragment_frame, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}

