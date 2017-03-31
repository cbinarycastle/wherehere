package kr.dsm.wherehere;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by dsm_024 on 2017-03-31.
 */

public class WritePostActivity extends Fragment {
    private Button photoBtn;
    private Button postBtn;
    private EditText titleInput;
    private EditText contentInput;

    private String base64Image;

    private int RESULT_LOAD_IMG = 1;

    private Bitmap slectePic;

    private String reqUrl = "http://192.168.20.7/writepost.do";
    private AsyncHttpClient client;
    private View view;

    RequestParams params = new RequestParams();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_write_post, null);

        client = new AsyncHttpClient();

        photoBtn = (Button) view.findViewById(R.id.photo);
        photoBtn.setOnClickListener(showGallery);

        postBtn = (Button) view.findViewById(R.id.post);
        postBtn.setOnClickListener(postArticle);

        titleInput = (EditText) view.findViewById(R.id.title);
        contentInput = (EditText) view.findViewById(R.id.content);

//        fragmentManager = getSupportFragmentManager();
//
//        rankingFragment = new RankingFragment();
//        setContentView(R.layout.activity_main);
//
//        fragmentManager.beginTransaction().replace(R.id.content, new RankingFragment()).commit();
//
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return view;
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
            String title = titleInput.getText().toString();
            String content = contentInput.getText().toString();

            params.put("title", title);
            params.put("title", content);
            params.put("writer", "test");
            params.put("age", 19);
            params.put("x", 1.0);
            params.put("y", 50.5);
            params.put("image", base64Image);

            client.post(reqUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    // Initiated the request
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // Successfully got a response
                    Log.d("onSuccess", "req success");
                    for(int i = 0; i < headers.length; i++) {
                        Log.i(headers[i].getName(), headers[i].getValue());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                {
                    // Response failed :(
                    Log.d("onFailure", "req fail");
                    for(int i = 0; i < headers.length; i++) {
                        Log.i(headers[i].getName(), headers[i].getValue());
                    }
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
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_LOAD_IMG) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                slectePic = selectedImage;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                slectePic.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byte_arr = stream.toByteArray();
                base64Image = Base64.encodeToString(byte_arr, Base64.NO_WRAP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
        }
    }
}


