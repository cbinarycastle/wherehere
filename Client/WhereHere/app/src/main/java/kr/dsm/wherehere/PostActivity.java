package kr.dsm.wherehere;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

/**
 * Created by BeINone on 2017-04-01.
 */

public class PostActivity extends AppCompatActivity {

    private Map mMap;

    private RecyclerView mRecyclerView;
    private TextView mTitleTV;
    private TextView mContentTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_post);
        mTitleTV = (TextView) findViewById(R.id.tv_post_title);
        mContentTV = (TextView) findViewById(R.id.tv_post_content);

        int postNum = getIntent().getIntExtra(getString(R.string.extra_postnum), 0);
        if (postNum != 0) {
            if (MapStorage.getMapSparseArray() != null) {
                mMap = MapStorage.getMapSparseArray().get(postNum);
            }
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (mMap != null) {
            mRecyclerView.setAdapter(new ImageAdapter(this, mMap.getImages()));
            mTitleTV.setText(mMap.getTitle());
            mContentTV.setText(mMap.getContent());
        }
    }

}
