package kr.dsm.wherehere;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import kr.dsm.wherehere.dto.Map;
import kr.dsm.wherehere.http.HttpConst;

/**
 * Created by BeINone on 2017-04-01.
 */

public class PostActivity extends AppCompatActivity {

    private AsyncHttpClient mAsyncHttpClient;

    private Map mMap;

    private RecyclerView mRecyclerView;
    private TextView mTitleTV;
    private TextView mContentTV;
    private ImageButton mRecommendIB;
    private Button mCommentBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        init();
    }

    private void init() {
        mAsyncHttpClient = new AsyncHttpClient();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_post);
        mTitleTV = (TextView) findViewById(R.id.tv_post_title);
        mContentTV = (TextView) findViewById(R.id.tv_post_content);
        mRecommendIB = (ImageButton) findViewById(R.id.ib_post_recommend);
        mCommentBtn = (Button) findViewById(R.id.btn_post_comment);

        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, CommentActivity.class);
                startActivity(intent);
            }
        });

        mRecommendIB.setColorFilter(ContextCompat.getColor(this, R.color.star), PorterDuff.Mode.MULTIPLY);
        mRecommendIB.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (mAsyncHttpClient != null) {
                                                    if (mRecommendIB.isSelected()) {
                                                        RequestParams params = new RequestParams();
                                                        params.put("purpose", "postunrec");
                                                        params.put("postNum", mMap.getPostNum());
                                                        mAsyncHttpClient.get(HttpConst.SERVER_URL + "/getinfo.do", params,
                                                                new AsyncHttpResponseHandler() {
                                                                    @Override
                                                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                                        mRecommendIB.setSelected(false);
                                                                        mRecommendIB.setImageResource(R.drawable.ic_star_border_white_48dp);
                                                                        mRecommendIB.setColorFilter(ContextCompat.getColor(PostActivity.this, R.color.star), PorterDuff.Mode.MULTIPLY);
                                                                    }

                                                                    @Override
                                                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                                                    }
                                                                });
                                                    }
                                                } else {
                                                    RequestParams params = new RequestParams();
                                                    params.put("purpose", "postrec");
                                                    params.put("postNum", mMap.getPostNum());
                                                    mAsyncHttpClient.get(HttpConst.SERVER_URL + "/getinfo.do", params,
                                                            new AsyncHttpResponseHandler() {
                                                                @Override
                                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                                    mRecommendIB.setSelected(true);
                                                                    mRecommendIB.setImageResource(R.drawable.ic_star_white_48dp);
                                                                    mRecommendIB.setColorFilter(ContextCompat.getColor(PostActivity.this, R.color.star), PorterDuff.Mode.MULTIPLY);
                                                                }

                                                                @Override
                                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                                                }
                                                            });
                                                }
                                            }
                                        }

        );

        int postNum = getIntent().getIntExtra(getString(R.string.extra_postnum), -1);
        if (postNum != -1)

        {
            if (MapStorage.getMapSparseArray() != null) {
                mMap = MapStorage.getMapSparseArray().get(postNum);
            }
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (mMap != null) {
//            mRecyclerView.setAdapter(new ImageAdapter(this, mMap.getImages()));
            mTitleTV.setText(mMap.getTitle());
            mContentTV.setText(mMap.getContent());
        }
    }

}
