package kr.dsm.wherehere;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by hojak on 2017-03-31.
 */

public class RankingFragment extends Fragment {

    private AsyncHttpClient mHttpClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ranking, null);

        ListView listView;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter();

        listView = (ListView) view.findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        RequestParams params = new RequestParams();
        params.put("purpose", "ranking");
        params.put("postnum", "2");

        mHttpClient = new AsyncHttpClient();
        mHttpClient.get("http://192.168.20.7:8080/getinfo.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONObject jsonObject = (JSONObject) response.get(0);

                    System.out.println(jsonObject.get("content"));
                    System.out.println(jsonObject.get("title"));
                    System.out.println(jsonObject.get("writer"));
                    System.out.println(jsonObject.get("x"));
                    System.out.println(jsonObject.get("y"));
                    System.out.println(jsonObject.get("recommand"));
                    System.out.println(jsonObject.get("postnum"));
                    System.out.println(jsonObject.get("unrecommand"));
                }catch (Exception j){
                    j.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable error) {
                System.out.println("Http get Fail");
            }
        });

        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.user),
                "hojak99","게시글 : 1");
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.user),
                "rlawlstjd007","게시글 : 1");
        adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.user),
                "graykick","게시글 : 1");

        return view;
    }
}
