package com.devbramm.mitumba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser == null) {
                    Intent toSignUp = new Intent(SplashScreenActivity.this, SignUpActivity.class);
                    startActivity(toSignUp);
                    finish();
                } else {
                    CommonUtils.currentUserNames = currentUser.getDisplayName();
                    CommonUtils.currentUsermEmail = currentUser.getEmail();
                    CommonUtils.currentUsermPhone = currentUser.getPhoneNumber();
                    CommonUtils.currentUserUid = currentUser.getUid();

                    Intent toHome = new Intent(SplashScreenActivity.this, HomePageActivity.class);
                    startActivity(toHome);
                    finish();
                }

            }
        },SPLASH_TIME_OUT);

    }

}
