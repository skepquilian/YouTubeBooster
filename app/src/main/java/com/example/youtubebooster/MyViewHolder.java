package com.example.youtubebooster;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    ImageView image1;
    TextView textView_title;
    View mView;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }
    public void setImage1(Context context, String image){
        image1 = mView.findViewById(R.id.youtube_player);
        Picasso.with(context).load("https://img.youtube.com/vi/"+image+"/0.jpg").into(image1);
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    public  void setTitle(String title){
       textView_title = mView.findViewById(R.id.title_id);
       textView_title.setText(title);
    }



}
