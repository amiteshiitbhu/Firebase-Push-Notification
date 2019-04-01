package com.exmple.firebase.pushnotification;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private String appPackageName;
    private boolean isFirstTimeLaunch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFirstTimeLaunch = true;

        String phoneNum = "+918009742403";
        String testVerificationCode = "123456";

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 30L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                MainActivity.this.enableUserManuallyInputCode();
                System.out.println("Code Send");
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                System.out.println("Code Send Not");
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                System.out.println("Code Send failed");
            }

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                appPackageName = extras.getString("appPackageName");
                System.out.println("isFirstTimeLaunch" + appPackageName);
                if (appPackageName != null) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            }
        }


    }

    private void enableUserManuallyInputCode() {
        System.out.println("Code Send Not A");
    }
}
