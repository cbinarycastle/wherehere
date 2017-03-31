package kr.dsm.wherehere;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private MapFragment mMapFragment;
    private RankingFragment rankingFragment;
    private AsyncHttpClient mHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        mMapFragment = new MapFragment();
        rankingFragment = new RankingFragment();

        fragmentManager.beginTransaction().replace(R.id.content, rankingFragment).commit();

        RequestParams params = new RequestParams("purpose", "ranking");

        mHttpClient = new AsyncHttpClient();
        mHttpClient.get("http://192.168.20.7:8080/getinfo.do", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("Http get Success  :  "+new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Http get Fail");
            }
        });

        fragmentManager = getSupportFragmentManager();

        rankingFragment = new RankingFragment();
        setContentView(R.layout.activity_main);

        String str = "[{'username':'hojak99', 'message' : 'hi mesg'}]";


        fragmentManager.beginTransaction().replace(R.id.content, new RankingFragment()).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    fragmentTransaction.replace(R.id.content, mMapFragment).commit();
                    return true;
                case R.id.navigation_ranking:
                    fragmentTransaction.replace(R.id.content, rankingFragment).commit();
                    return true;
                case R.id.navigation_review:
                    fragmentTransaction.replace(R.id.content, rankingFragment).commit();
                    return true;
            }
            return false;
        }
    };

}
