package kr.dsm.wherehere;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by dsm_024 on 2017-04-01.
 */

public class LoginActivity extends AppCompatActivity {
    RelativeLayout container;

    AnimationDrawable anim;

    TextView registerBtn;
    EditText idInput;
    EditText psInput;
    Button loginBtn;
    Intent intent;

    private String reqUrl = "http://192.168.20.209:8080/account.do";
    private AsyncHttpClient client;

    RequestParams params = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        container = (RelativeLayout) findViewById(R.id.container);
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);
        
        intent = new Intent(this, MainActivity.class);

        getSupportActionBar().hide();

        client = new AsyncHttpClient();

        loginBtn = (Button) findViewById(R.id.loginbtn);
        idInput = (EditText) findViewById(R.id.loginid);
        psInput = (EditText) findViewById(R.id.loginps);
        registerBtn = (TextView) findViewById(R.id.register);

        loginBtn.setOnClickListener(login);
        registerBtn.setOnClickListener(register);



    }

    TextView.OnClickListener register = new View.OnClickListener() {
        public void onClick(View v) {
            params.put("purpose", "register");
            params.put("id", idInput.getText().toString());
            params.put("password", psInput.getText().toString());

            client.post(reqUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    // Initiated the request
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // Successfully got a response
                    Log.d("onSuccess", "req success");
//                    for (int i = 0; i < headers.length; i++) {
//                        Log.i(headers[i].getName(), headers[i].getValue());
//                    }
                    Toast.makeText(getApplicationContext(), "회원가입이 되었어요", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    // Response failed :(
                    Log.d("onFailure", "req fail" + statusCode);
//                    for(int i = 0; i < headers.length; i++) {
//                        Log.i(headers[i].getName(), headers[i].getValue());
//                    }
                    Toast.makeText(getApplicationContext(), "회원가입에 실패했어요 ㅠㅠ", Toast.LENGTH_SHORT).show();

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

    Button.OnClickListener login = new View.OnClickListener() {
        public void onClick(View v) {
            params.put("purpose", "login");
            params.put("id", idInput.getText().toString());
            params.put("password", psInput.getText().toString());

            client.post(reqUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    // Initiated the request
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // Successfully got a response
                    Log.d("onSuccess", "req success");
//                    for (int i = 0; i < headers.length; i++) {
//                        Log.i(headers[i].getName(), headers[i].getValue());
//                    }
                    finish();
                    startActivity(intent);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    // Response failed :(
                    Log.d("onFailure", "req fail" + statusCode);
//                    for(int i = 0; i < headers.length; i++) {
//                        Log.i(headers[i].getName(), headers[i].getValue());
//                    }
                    Toast.makeText(getApplicationContext(), "로그인에 실패했어요 ㅠㅠ", Toast.LENGTH_SHORT).show();
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
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    // Stopping animation:- stop the animation on onPause.
    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }
}
