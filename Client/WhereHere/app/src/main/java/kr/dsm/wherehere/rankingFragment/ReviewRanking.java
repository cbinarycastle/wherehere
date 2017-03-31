package kr.dsm.wherehere.rankingFragment;

/**
 * Created by hojak on 2017-04-01.
 */


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import kr.dsm.wherehere.R;
import kr.dsm.wherehere.dto.Map;
import kr.dsm.wherehere.dto.User;
import kr.dsm.wherehere.http.HttpResponseParser;

public class ReviewRanking extends Fragment {
    //MyActivity 시작
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;
    private AsyncHttpClient mHttpClient;
    List<Map> mapList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_review_ranking, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        RequestParams params = new RequestParams();

        mHttpClient = new AsyncHttpClient();
        mHttpClient.get("http://192.168.20.209:8080/getpost.do", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    mapList = HttpResponseParser.parseLoadPostInfo(response);
                }catch (Exception j){
                    j.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable error) {
                error.printStackTrace();
                System.out.println("Http get Fail" + statusCode);

            }
        });

        if(mapList != null){

            String writerStr;
            String bodyStr;

            for(int i=0; i< mapList.size(); ++i) {
                bodyStr = mapList.get(i).getContent();
                writerStr = mapList.get(i).getWriter();
                myDataset.add(new MyData(writerStr, bodyStr, R.mipmap.insideout));

                System.out.println("성공 : "+ writerStr);
            }
        }

        //myDataset.add(new MyData("#InsideOut",R.mipmap.insideout));
        // myDataset.add(new MyData("#Mini",R.mipmap.mini));
        // myDataset.add(new MyData("#ToyStroy",R.mipmap.toystory));

        return view;
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<MyData> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mBodyTextView;
        public TextView mWriterTextView;

        public ViewHolder(View view) {
            super(view);

            mImageView = (ImageView)view.findViewById(R.id.image);
            mBodyTextView = (TextView)view.findViewById(R.id.bodyTextView);
            mWriterTextView = (TextView) view.findViewById(R.id.writerTextView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<MyData> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recycleview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mWriterTextView.setText("Writer : "+mDataset.get(position).writerText);
        holder.mBodyTextView.setText(mDataset.get(position).bodyText);
        holder.mImageView.setImageResource(mDataset.get(position).img);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

class MyData{
    public String bodyText;
    public int img;
    public String writerText;

    public MyData(String writer, String bodyText, int img){
        this.writerText = writer;
        this.bodyText = bodyText;
        this.img = img;
    }
}