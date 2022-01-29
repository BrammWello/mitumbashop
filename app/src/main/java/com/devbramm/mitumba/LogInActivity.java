package com.devbramm.mitumba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    TextInputEditText signInEmail, signInPass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        signInEmail = findViewById(R.id.sign_in_email);
        signInPass = findViewById(R.id.sign_in_pass);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.to_sign_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignUp = new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(toSignUp);
                finish();
            }
        });

        final ProgressDialog mDialog = new ProgressDialog(LogInActivity.this);

        findViewById(R.id.sign_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check details first

                if (signInEmail.getText().toString().isEmpty()) {
                    signInEmail.setError("Your Phone Number Please");
                } else if (signInPass.getText().toString().isEmpty()) {
                    signInPass.setError("Your Email Please");
                } else {
                    String providedUserEmail, providedUserPass;

                    providedUserEmail = signInEmail.getText().toString();
                    providedUserPass = signInPass.getText().toString();

                    loginCustomer(providedUserEmail,providedUserPass);

                    mDialog.setMessage("Please Wait");
                    mDialog.show();
                }
            }
        });
    }

    private void loginCustomer(String providedUserEmail, String providedUserPass) {
        mAuth.signInWithEmailAndPassword(providedUserEmail, providedUserPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser  = mAuth.getCurrentUser();
                            CommonUtils.currentUsermEmail = currentUser.getEmail();
                            CommonUtils.currentUserNames = currentUser.getDisplayName();
                            CommonUtils.currentUsermPhone = currentUser.getPhoneNumber();
                            CommonUtils.currentUserUid = currentUser.getUid();

                            Intent toHomePage = new Intent(LogInActivity.this,HomePageActivity.class);
                            startActivity(toHomePage);
                            finish();

                        } else {
                            Toast.makeText(LogInActivity.this, "Sorry. There was trouble getting you in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
