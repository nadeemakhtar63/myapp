package com.example.youtubeplay;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import androidx.appcompat.widget.Toolbar;
import android.widget.VideoView;

public class VideoPlay extends AppCompatActivity {
VideoView videoPlay;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
    videoPlay=(VideoView) findViewById(R.id.play);

        toolbar=findViewById(R.id.play_toolbar);  setSupportActionBar(toolbar); getSupportActionBar().setTitle("Video Gallery");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    String ur=getIntent().getStringExtra("video");
        //Creating MediaController
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoPlay);

        //specify the location of media file
        Uri uri= Uri.parse(ur);

        //Setting MediaController and URI, then starting the videoView
        videoPlay.setMediaController(mediaController);
        videoPlay.setVideoURI(uri);
        videoPlay.requestFocus();
       videoPlay.start();

    }
}
