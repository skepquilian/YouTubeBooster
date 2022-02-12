package com.example.youtubebooster;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class BoostChannelFragment extends Fragment {
    CardView cardView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_boost_channel,container,false);
        cardView =  v.findViewById(R.id.body_text);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsApp(view);
            }
        });
        return v;
    }

    public void openWhatsApp(View view){
        try {
            String text = "Hello I want to boost my channel";
            String toNumber = "2330202009777";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
