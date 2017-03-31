package kr.dsm.wherehere;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        mMapFragment = new MapFragment();
        rankingFragment = new RankingFragment();

        fragmentManager.beginTransaction().replace(R.id.content, mMapFragment).commit();

        RequestParams params = new RequestParams("purpose", "ranking");

        mHttpClient = new AsyncHttpClient();
        mHttpClient.get("http://192.168.20.7:8080/getinfo.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println(response.get("content"));
                    System.out.println(response.get("title"));
                    System.out.println(response.get("writer"));
                    System.out.println(response.get("x"));
                    System.out.println(response.get("y"));
                    System.out.println(response.get("recommand"));
                    System.out.println(response.get("postnum"));
                }catch (JSONException j){
                    j.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable error) {
                System.out.println("Http get Fail");
            }
        });

        fragmentManager = getSupportFragmentManager();

        rankingFragment = new RankingFragment();
        setContentView(R.layout.activity_main);

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
                    fragmentTransaction.replace(R.id.content, new WritePostActivity()).commit();
                    return true;
            }
            return false;
        }
    };

}
