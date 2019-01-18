package com.ryan.rokidftpclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ryan.ftp.FileManagerActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.showImg);
    }

    @Override
    protected void onResume() {
        super.onResume();


            //FTPUtils.getInstance().openConnect();
            Glide.with(MainActivity.this).load( "ftp://rokid:rokid@10.88.1.67:1122/Camera/IMG_0480.jpg").into(mImageView);
            Log.e("zhfzhf", "start");
            Glide.with(MainActivity.this)
                    .load( "ftp://rokid:rokid@10.88.1.67:1122/Camera/IMG_0480.jpg")
                    .into(mImageView);
            Log.e("zhfzhf", "end");

        //new MyAsyncTask().execute();

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


//    public class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
//
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            return  getBitmapFromURL("ftp://rokid:rokid@10.88.1.67:1122/Camera/IMG_0480.jpg");
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            if(result != null)
//                mImageView.setImageBitmap(result);
//        }
//    }
//
//
//
//
    public void onBtnClick(View view) {
        Intent intent = new Intent(this, FileManagerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
}
