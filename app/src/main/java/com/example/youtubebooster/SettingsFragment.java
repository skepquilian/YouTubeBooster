package com.example.youtubebooster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {
    TextView signBtn,update_PhoneNumber;
    private Switch darkModeSwitch;
    View view;
    private String m_Text = "";
    DatabaseReference insertMoneyRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        update_PhoneNumber = view.findViewById(R.id.update_phone_number);
        insertMoneyRef = FirebaseDatabase.getInstance().getReference().child("Users");
        signBtn = view.findViewById(R.id.sign_out);
        if (new DarkModePrefManager(getContext()).isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        //function for enabling dark mode
        setDarkModeSwitch();
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Sign out from this Account");
                builder.setIcon(R.drawable.ic_baseline_arrow_back_24);
                builder.setMessage("Are you sure you want to sign Out?");
                builder.setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(),RegisterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //DO nothing

                    }
                }).show();

            }
        });
    update_PhoneNumber.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Momo number for Payment");

// Set up the input
            final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
            input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    if(m_Text.length() < 10){
                        input.setError("Number is not valid to 10");
                        input.requestFocus();

                    }
                    if(m_Text.length() == 10){
                        String getUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        insertMoneyRef.child(getUserId).child("phone").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    insertMoneyRef.child(getUserId).child("phone").setValue(m_Text);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT);
                            }
                        });
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    });

        return  view;
    }
    private void setDarkModeSwitch(){
        darkModeSwitch =view.findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setChecked(new DarkModePrefManager(getContext()).isNightMode());
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DarkModePrefManager darkModePrefManager = new DarkModePrefManager(getContext());
                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            }
        });
    }


    }
//}
