package kr.dsm.wherehere;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by dsm_024 on 2017-03-31.
 */

public class WritePostActivity extends AppCompatActivity {
    private Button photoBtn;
    private Button postBtn;
    private int RESULT_LOAD_IMG = 1;
    private Bitmap slectePic;
    private String reqUrl = "http://192.168.20.7:8080/writepost.do";

    private AsyncHttpClient client;

    RequestParams params = new RequestParams();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsyncHttpClient client = new AsyncHttpClient();


        photoBtn = (Button) findViewById(R.id.photo);
        photoBtn.setOnClickListener(showGallery);

        postBtn = (Button) findViewById(R.id.post);
        postBtn.setOnClickListener(postArticle);

        setContentView(R.layout.activity_write_post);


//        fragmentManager = getSupportFragmentManager();
//
//        rankingFragment = new RankingFragment();
//        setContentView(R.layout.activity_main);
//
//        fragmentManager.beginTransaction().replace(R.id.content, new RankingFragment()).commit();
//
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    Button.OnClickListener showGallery = new View.OnClickListener() {
        public void onClick(View v) {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
    };

    Button.OnClickListener postArticle = new View.OnClickListener() {
        public void onClick(View v) {
            params.put("test", "momo");

            client.post(reqUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    // Initiated the request
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // Successfully got a response
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                        error)
                {
                    // Response failed :(
                }

                @Override
                public void onRetry(int retryNo) {
                    // Request was retried
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    // Progress notification
                }

                @Override
                public void onFinish() {
                    // Completed the request (either success or failure)
                }
            } );
        }
    };

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                slectePic = selectedImage;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
        }
    }
}


