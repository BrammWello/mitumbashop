package com.devbramm.mitumba;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    Button signUpBtn;
    TextInputEditText signUpName, signUpEmail, signUpPhone, signUpPass, signUpPassConfirm;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        //TODO check if the user is signed in first before signing up
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initialize firebase
        mAuth = FirebaseAuth.getInstance();

        signUpName = findViewById(R.id.sign_up_name);
        signUpEmail = findViewById(R.id.sign_up_email);
        signUpPhone = findViewById(R.id.sign_up_phone);
        signUpPass = findViewById(R.id.sign_up_pass);
        signUpPassConfirm = findViewById(R.id.sign_up_pass_confirm);

        signUpBtn = findViewById(R.id.sign_up_btn);

        findViewById(R.id.to_sign_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignIn = new Intent(SignUpActivity.this,LogInActivity.class);
                startActivity(toSignIn);
                finish();
            }
        });

        final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //perform series of checks to prove user has correct details
                mDialog.setMessage("Please Wait as we set up your account");
                mDialog.show();

                if (signUpName.getText().toString().isEmpty()) {
                    signUpName.setError("Your Names Please");
                    mDialog.dismiss();
                } else if (signUpEmail.getText().toString().isEmpty()) {
                    signUpEmail.setError("Your Email Please");
                    mDialog.dismiss();
                } else if (signUpPhone.getText().toString().isEmpty()) {
                    signUpPhone.setError("Your Phone Number Please");
                    mDialog.dismiss();
                } else if (signUpPass.getText().toString().isEmpty()) {
                    signUpPass.setError("Your New Password");
                    mDialog.dismiss();
                } else if (signUpPassConfirm.getText().toString().isEmpty()) {
                    signUpPassConfirm.setError("Please repeat Password");
                    mDialog.dismiss();
                }
//                else if (signUpPass.getText().toString() != signUpPassConfirm.getText().toString()) {
//                    signUpPass.setError("Passwords Do not match");
//                    mDialog.dismiss();
//                }
                else {
                    String newUserNames, newUserEmail, newUserPhone, newUserPass;

                    newUserNames = signUpName.getText().toString();
                    newUserEmail = signUpEmail.getText().toString();
                    newUserPhone = signUpPhone.getText().toString();
                    newUserPass = signUpPass.getText().toString();

                    createNewUser(newUserNames,newUserEmail,newUserPass,newUserPhone);

                    mDialog.dismiss();
                    mDialog.setMessage("Just a sec now..");
                    mDialog.show();
                }


            }
        });


    }

    private void createNewUser(final String newUserNames, String newUserEmail, String newUserPass, final String newUserPhone) {

            mAuth.createUserWithEmailAndPassword(newUserEmail,newUserPass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //continue to update the new user details
                                CommonUtils.registerUserName = newUserNames;
                                CommonUtils.registerUserPhone = newUserPhone;

                                Toast.makeText(SignUpActivity.this, "Nice. Lets verify your number", Toast.LENGTH_SHORT).show();
                                Intent toPhoneVerify = new Intent(SignUpActivity.this,PhoneVerifyActivity.class);
                                startActivity(toPhoneVerify);
                                finish();
                            }
                            else {
                                //Toast.makeText(SignUpActivity.this, "Sorry. an Error occurred somewhere", Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }

}
