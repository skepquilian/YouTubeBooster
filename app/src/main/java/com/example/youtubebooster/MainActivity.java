package com.example.youtubebooster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button login_button;
    EditText email_ed_login,password_ed_login;
    TextView register_tv;
    DatabaseReference userdatabaseRef;
    Intent intent;
    boolean doubleBackToExitPressedOnce = false;

    FirebaseAuth mAuth;
    public static String PREF_SHARED ="MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userdatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        email_ed_login = findViewById(R.id.email_ed_lg);
        password_ed_login = findViewById(R.id.password_ed_lg);
        register_tv = findViewById(R.id.signup_textview);
        login_button = findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();
        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(in);
                finish();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String getEmail = email_ed_login.getText().toString().trim();
        String getPassword = password_ed_login.getText().toString().trim();

        if(getEmail.isEmpty()){
            email_ed_login.setError("email field is empty");
            email_ed_login.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()){
            email_ed_login.setError("email is invalid");
            email_ed_login.requestFocus();
            return;
        }
        if (getPassword.isEmpty()){
            password_ed_login.setError("password field is empty");
            password_ed_login.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,HomePageActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"Login Failed Try Again",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}