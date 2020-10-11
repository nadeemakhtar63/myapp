package com.example.youtubeplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.youtubeplay.Adapter.VideoAdapter;
import com.example.youtubeplay.ModuleClass.VideoModule;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 1;
    RecyclerView recyclerView;
    Toolbar mtoolbar;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ArrayList<VideoModule> arrayListVideo;
    RecyclerView.LayoutManager recyclerviewlayouManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
mtoolbar=findViewById(R.id.list_toolbar);  setSupportActionBar(mtoolbar); getSupportActionBar().setTitle("Video Gallery");
        init();
getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    private void init() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerviewlayouManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(recyclerviewlayouManager);
        arrayListVideo = new ArrayList<>();
 //       fetchVideoFromGallery();

        fn_checkpermission();
    }

    private void fn_checkpermission() {
        /*RUN TIME PERMISSIONS*/

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Log.e("Else", "Else");
            fn_video();
        }

    }

    public void fn_video() {

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name, column_id, thum;

        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media._ID, MediaStore.Video.Thumbnails.DATA};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));
            Log.e("column_id", cursor.getString(column_id));
            Log.e("thum", cursor.getString(thum));

            VideoModule obj_model = new VideoModule();
            obj_model.setBoolean_selected(false);
            obj_model.setStr_path(absolutePathOfImage);
            obj_model.setStr_thumb(cursor.getString(thum));

            arrayListVideo.add(obj_model);

        }


        VideoAdapter videoAdapter = new VideoAdapter(getApplicationContext(), arrayListVideo, MainActivity.this);
        recyclerView.setAdapter(videoAdapter);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_video();
                    } else {
                        Toast.makeText(MainActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
//}
//
//    private void fetchVideoFromGallery() {
//        Uri uri;
//        Cursor cursor;
//        int column_indux_data,thumb;
//        String absulatePathImage=null;
//        uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//        String[] projection={MediaStore.MediaColumns.DATA,
//        MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
//                MediaStore.Video.Media._ID,
//                MediaStore.Video.Thumbnails.DATA,};
//        String orderBy=MediaStore.Images.Media.DATE_TAKEN;
//        cursor=getApplicationContext().getContentResolver().query(uri,projection,null,null,orderBy+"DESC");
//        column_indux_data=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//
//        thumb=cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
//        while (cursor.moveToNext())
//        {
//            absulatePathImage=cursor.getString(column_indux_data);
//            VideoModule videoModule=new VideoModule();
//            videoModule.setBoolean_selected(false);
//            videoModule.setStr_path(absulatePathImage);
//            videoModule.setStr_thumb(cursor.getString(thumb));
//
//            arrayListVideo.add(videoModule);
//
//        }
//        VideoAdapter videoAdapter=new VideoAdapter(getApplicationContext(),arrayListVideo,MainActivity.this);
//        recyclerView.setAdapter(videoAdapter);
  //  }
//}
