package kr.dsm.wherehere;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by dsm_024 on 2017-04-01.
 */

public class WriteFirstActivity  extends AppCompatActivity {
    LinearLayout container = (LinearLayout) findViewById(R.id.container);
    AnimationDrawable anim = (AnimationDrawable) container.getBackground();

    EditText titleInput;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        titleInput = (EditText) findViewById(R.id.loginid);

        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);
    }
}
