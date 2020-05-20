package com.example.task.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.task.MainActivity;
import com.example.task.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    private String verificationId;
    private EditText editText, editForCode;
    private TextView editCountryCode;
    private LinearLayout forSmsCode;
    private ConstraintLayout forPhoneNumber;
    private LottieAnimationView animation;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        editText = findViewById(R.id.edit_phone);
        editForCode = findViewById(R.id.edit_for_code);
        forPhoneNumber = findViewById(R.id.for_number);
        forSmsCode = findViewById(R.id.for_sms_code);
        editCountryCode = findViewById(R.id.country_code);
        animation=findViewById(R.id.ojidanie);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone", "onVerificationCompleted");
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    verifyCode(code);
                }


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Phone", "onVerificationFailed");

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.e("Phone", "onCodeSent");
                verificationId = s;
                animation.setVisibility(View.GONE);
                forSmsCode.setVisibility(View.VISIBLE);
            }
        };
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signIn(credential);
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                } else {
                    Log.e("taskeror", "error" + task.getException().getMessage());
                    Toast.makeText(PhoneActivity.this, "Repeat please", Toast.LENGTH_LONG);
                }

            }
        });
    }

    public void onClickCont(View view) {
        String phone = editCountryCode.getText().toString().trim() + editText.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks);
        if (editText!=null){
        forPhoneNumber.setVisibility(View.GONE);
    }}

    public void onClickVer(View view) {
        String code = editForCode.getText().toString().trim();
        if (code.isEmpty() || code.length() < 6) {
            editForCode.requestFocus();
            return;
        }
        verifyCode(code);
        finish();
    }
}
