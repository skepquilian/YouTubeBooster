package com.example.youtubebooster;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference mdatabase;
    TextView textView, y_tube_title;
    VlogModel vlogModel;
    DatabaseReference moneyUpdatedRef;
    YoutubeConfig youtubeConfig;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        vlogModel = new VlogModel();
        moneyUpdatedRef = FirebaseDatabase.getInstance().getReference().child("Users");
        youtubeConfig = new YoutubeConfig();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("VideosUploaded");
        //gettitle = new Gettitle();
        mdatabase.keepSynced(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recyclerview);
        textView = (TextView) view.findViewById(R.id.money_tv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        if(isNetworkAvailable()){
            Snackbar.make(getActivity().findViewById(android.R.id.content),"Are you online",Snackbar.LENGTH_SHORT);
        }
        if(!isNetworkAvailable()){

            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
        FirebaseRecyclerAdapter<VlogModel, MyViewHolder> adapter = new FirebaseRecyclerAdapter<VlogModel, MyViewHolder>
                (VlogModel.class, R.layout.cardview_view, MyViewHolder.class, mdatabase) {
            @Override
            protected void populateViewHolder(MyViewHolder myViewholder, VlogModel vlogModel, int i) {
                myViewholder.setImage1(getContext(), vlogModel.getLinks());
                myViewholder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String getVideo_id = vlogModel.getLinks();
                        String getChannelID = vlogModel.getChannel__id();
                        Intent intent = new Intent(getContext(), YoutubeVideoPlayerActivity.class);
                        intent.putExtra("video_id1", getVideo_id);
                        intent.putExtra("channel_id_g", getChannelID);
                        startActivity(intent);
                    }
                });
            }


        };
        moneyUpdatedRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("money_to_paid")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String getMoney = dataSnapshot.getValue(String.class);
                        textView.setText("Ghc" + getMoney);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        recyclerView.setAdapter(adapter);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}