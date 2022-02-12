
 package com.example.youtubebooster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

 public class SplashScreenActivity extends AppCompatActivity {
    private  static  int SPLASH_SCREEN_TIME = 10000;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    DatabaseReference checkFromdataref;
    FirebaseDatabase firebaseDatabase;
    Dialog dialog;

     FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //counter
        long secondsCount = 2000;

        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(() -> {
            if (firebaseUser != null){
                startActivity(new Intent(SplashScreenActivity.this, HomePageActivity.class));
            }
            else {
                startActivity(new Intent(SplashScreenActivity.this, RegisterActivity.class));
            }
            finish();

        }, secondsCount);
    }

 }