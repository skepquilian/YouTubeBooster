package com.example.youtubebooster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePageActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    DatabaseReference mdatabase;
   // YouTubeThumbnailView youTubeThumbnailView;
    YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nagivation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navgiation);
         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navgiation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment itemfragmentSelected = null;
            switch(item.getItemId()){
                case R.id.nav_home:
                    itemfragmentSelected = new HomeFragment();
                    break;
                case R.id.nav_boost:
                    itemfragmentSelected = new BoostChannelFragment();
                    break;
                case R.id.nav_settings:
                    itemfragmentSelected = new SettingsFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,itemfragmentSelected).commit();
            return true;
        }
    };
}