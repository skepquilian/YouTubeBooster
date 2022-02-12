package com.example.youtubebooster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyAccountPhone extends AppCompatActivity {
    String verificationcodesent;
    Button verificationBtn;
    EditText verifyEdt;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account_phone);

        verificationBtn = findViewById(R.id.verifybtn);
        verifyEdt = findViewById(R.id.verify_edittext);

         mAuth = FirebaseAuth.getInstance();

        String phone_number_for_verification = getIntent().getStringExtra("phoneNo");
        sendVerificationCodeName(phone_number_for_verification);
    }

    private void sendVerificationCodeName(String phone_number_for_verification) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone_number_for_verification)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationcodesent = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyAccountPhone.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };

    private void verifycode(String usercode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationcodesent,usercode);
        signInByuserCredential(credential);
    }

    private void signInByuserCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(VerifyAccountPhone.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){
               Intent intent = new Intent(VerifyAccountPhone.this,HomePageActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);
               finish();
           }
            }
        });
    }
}