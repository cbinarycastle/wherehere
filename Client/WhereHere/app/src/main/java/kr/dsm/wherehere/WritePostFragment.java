package kr.dsm.wherehere;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dsm_024 on 2017-03-31.
 */

public class WritePostFragment extends Fragment {
    private Button photoBtn;
    private Button postBtn;
    private EditText titleInput;
    private EditText contentInput;

    private String base64Image;
    private InputStream inputStream;
    List<File> fileList = new ArrayList<File>();


    private int RESULT_LOAD_IMG = 1;

    private Bitmap slectePic;

    private String reqUrl = "http://192.168.20.209:8080/writepost.do";
    private AsyncHttpClient client;
    private View view;
    private Spinner spinner;


    RequestParams  params = new RequestParams ();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_write_post, null);

        client = new AsyncHttpClient();

        photoBtn = (Button) view.findViewById(R.id.photo);
        photoBtn.setOnClickListener(showGallery);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.region, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                tv.setText("position : " + position +
//                        parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        postBtn = (Button) view.findViewById(R.id.post);
        postBtn.setOnClickListener(postArticle);

        titleInput = (EditText) view.findViewById(R.id.title);
        contentInput = (EditText) view.findViewById(R.id.content);

        return view;
    }

    Button.OnClickListener showGallery = new View.OnClickListener() {
        public void onClick(View v) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }
    };

    Button.OnClickListener postArticle = new View.OnClickListener() {
        public void onClick(View v) {
            String title = titleInput.getText().toString();
            String content = contentInput.getText().toString();
            JSONObject jParam = new JSONObject();
            try {
                jParam.put("title", title);
                jParam.put("content", content);
                jParam.put("writer", "test");
                jParam.put("age", 19);
                jParam.put("x", 37.886008);
                jParam.put("y", 127.739903);
//                JSONArray imageArr = new JSONArray();
//                for (File file : fileList) {
//                    JSONObject temp = new JSONObject();
//                    try {
//                        temp.put("data", file, "image.jpg");
//                        temp.put
//                        imageArr.put(temp);
//                    } catch (Exception e) {
//
//                    }
//                }
//                RequestParams imageParam = new RequestParams();
//                imageParam.put("image", inputStream, "image.jpg");
          //      jParam.put("image", base64Image);
            } catch (Exception e) {
                e.printStackTrace();
            }
            params.put("data", jParam);

            client.post(reqUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    // Initiated the request
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // Successfully got a response
                    Log.d("onSuccess", "req success");
                    for (int i = 0; i < headers.length; i++) {
                        Log.i(headers[i].getName(), headers[i].getValue());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    // Response failed :(
                    Log.d("onFailure", "req fail" + statusCode);
//                    for(int i = 0; i < headers.length; i++) {
//                        Log.i(headers[i].getName(), headers[i].getValue());
//                    }
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
            });
        }
    };

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                slectePic = selectedImage;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                slectePic.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byte_arr = stream.toByteArray();

//                base64ImageList.add(Base64.encodeToString(byte_arr, Base64.NO_WRAP));
//                Log.d("asdf","asdfl;kahsdflkjhaslkdjfhlakjsdfhlkajsdhflkajsdhflkajsdfh");
//                Log.d("image data", Base64.encodeToString(byte_arr, Base64.NO_WRAP));
                base64Image = Base64.encodeToString(byte_arr, Base64.NO_WRAP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//                            final Uri imageUri = data.getData();
//                final InputStream imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                slectePic = selectedImage;
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                slectePic.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byte_arr = stream.toByteArray();
//
//            base64ImageList.add("asdfasdfasdfasdfasdfasdf");
//            Log.d("asdf", "asdfl;kahsdflkjhaslkdjfhlakjsdfhlkajsdhflkajsdhflkajsdfh");
////                Log.d("image data", Base64.encodeToString(byte_arr, Base64.NO_WRAP));
////                base64Image = Base64.encodeToString(byte_arr, Base64.NO_WRAP);
//            try {
//
//                final Uri imageUri = data.getData();
//                final InputStream imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);
//                inputStream = imageStream;
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                //    String currImageURI = data.getData();
////                Uri selectedImageUri = data.getData();
////                imagepath = getPath(selectedImageUri);
//                Uri selectedImageUri = data.getData();
//                File imageFile = new File(getPath(selectedImageUri));
//                fileList.add(imageFile);
//
//                try{
//                  //  selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//                }catch (Exception e) {
//                    Log.d("image error", "error");
//                    e.printStackTrace();
//                }


                //  fileList.add(file);
//                slectePic = selectedImage;
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                slectePic.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byte_arr = stream.toByteArray();
//
//
//                base64ImageList.add(Base64.encodeToString(byte_arr, Base64.NO_WRAP));
//                //Log.d("asdf","asdfl;kahsdflkjhaslkdjfhlakjsdfhlkajsdhflkajsdhflkajsdfh");
//                Log.d("image data", Base64.encodeToString(byte_arr, Base64.NO_WRAP));
//                base64Image = Base64.encodeToString(byte_arr, Base64.NO_WRAP);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }

        } else {
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
}


