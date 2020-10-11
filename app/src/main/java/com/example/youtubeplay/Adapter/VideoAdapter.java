package com.example.youtubeplay.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.youtubeplay.ModuleClass.AudioModuleClass;
import com.example.youtubeplay.ModuleClass.VideoModule;
import com.example.youtubeplay.R;
import com.example.youtubeplay.VideoPlay;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
  Context context;
  ArrayList<VideoModule> arrayListVideo;
  Activity activity;

    public VideoAdapter(Context context, List<VideoModule> arrayListVideo, Activity activity) {
        this.context = context;
        this.arrayListVideo = (ArrayList<VideoModule>) arrayListVideo;
        this.activity = activity;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_video,parent,false);
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        Glide.with(context).load("file://"+arrayListVideo.get(position).getStr_thumb())
                .skipMemoryCache(false)
                .into(holder.imageView);
        holder.rl_selected.setBackgroundColor(Color.parseColor("#FFFFFF"));
        holder.rl_selected.setAlpha(0);

        holder.rl_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, VideoPlay.class);
                        i.putExtra("video",arrayListVideo.get(position).getStr_path());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListVideo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RelativeLayout rl_selected;
        public ViewHolder(View view) {
            super(view);

            imageView=view.findViewById(R.id.iv_image);
            rl_selected=view.findViewById(R.id.rl_selected);
        }
    }
}
