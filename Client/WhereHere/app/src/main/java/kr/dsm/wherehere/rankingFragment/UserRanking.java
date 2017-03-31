package kr.dsm.wherehere.rankingFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import kr.dsm.wherehere.ListViewAdapter;
import kr.dsm.wherehere.R;

/**
 * Created by hojak on 2017-04-01.
 */

public class UserRanking extends Fragment{
    private AsyncHttpClient mHttpClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_ranking, null);


        ListView listView;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter();

        listView = (ListView) view.findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        RequestParams params = new RequestParams();
        params.put("purpose", "ranking");
        params.put("postnum", "2");

        mHttpClient = new AsyncHttpClient();
        mHttpClient.get("http://192.168.20.209:8080/getinfo.do", params, new JsonHttpResponseHandler() {
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

        for(int i=0; i< 20; ++i) {
            listAdd(adapter, R.drawable.user, "hojak99", 1);
        }

        return view;
    }

    public void listAdd(ListViewAdapter adapter, int icon, String id, int count){
        adapter.addItem(ContextCompat.getDrawable(getContext(), icon), id,"게시글 : "+count);
    }
}
