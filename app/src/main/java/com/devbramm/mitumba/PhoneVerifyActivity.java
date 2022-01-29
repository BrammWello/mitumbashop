package com.devbramm.mitumba;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.TimeUnit;

public class PhoneVerifyActivity extends AppCompatActivity {

    Button verifyPhoneBtn;
    Dialog confirmDialog;
    TextView phoneNumberToSendCode;
    EditText codeEntered;

    String codeSent;

    FirebaseAuth mAuth;

    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);

        confirmDialog = new Dialog(this);
        verifyPhoneBtn = findViewById(R.id.verify_phone_btn);
        codeEntered = findViewById(R.id.code_entered);

        mAuth = FirebaseAuth.getInstance();

        mDialog = new ProgressDialog(PhoneVerifyActivity.this);

        verifyPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeEntered.getText().toString().isEmpty()) {
                    codeEntered.setError("Nothing Entered");
                } {
                    String codeEnteredDigits = codeEntered.getText().toString();
                    mDialog.setMessage("The last Sec...");
                    mDialog.show();
                    verifySentCode(codeEnteredDigits);
                }
            }
        });

        phoneNumberToSendCode = findViewById(R.id.number_to_send_code);

        phoneNumberToSendCode.setText("We sent a code to " + CommonUtils.registerUserPhone);

        sendVerificationCode();
    }

    private void sendVerificationCode() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                CommonUtils.registerUserPhone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText(PhoneVerifyActivity.this, "We are processing your request", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(PhoneVerifyActivity.this, "Verification Not Sent. Please Try Again", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            Toast.makeText(PhoneVerifyActivity.this, "Request Has been sent to Your Phone", Toast.LENGTH_SHORT).show();

            codeSent = s;
        }
    };

    private void verifySentCode(String codeEnteredDigits) {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, codeEnteredDigits);
            signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {

        final FirebaseUser registeringUser = mAuth.getCurrentUser();

        registeringUser.updatePhoneNumber(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(CommonUtils.registerUserName)
                                    .build();

                            registeringUser.updateProfile(profileUpdate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser currentUser  = mAuth.getCurrentUser();
                                                CommonUtils.currentUsermEmail = currentUser.getEmail();
                                                CommonUtils.currentUserNames = currentUser.getDisplayName();
                                                CommonUtils.currentUsermPhone = currentUser.getPhoneNumber();
                                                CommonUtils.currentUserUid = currentUser.getUid();

                                                mDialog.dismiss();
                                                showPopUp();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void showPopUp() {
        Button btnGetStarted;
        confirmDialog.setContentView(R.layout.phone_confirmation_dialog);

        btnGetStarted = confirmDialog.findViewById(R.id.get_started_dialog_btn);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(PhoneVerifyActivity.this, HomePageActivity.class);
                startActivity(toMain);
            }
        });

        confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmDialog.show();
    }


}
