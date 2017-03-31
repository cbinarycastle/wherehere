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
import org.json.JSONException;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import kr.dsm.wherehere.ListViewAdapter;
import kr.dsm.wherehere.R;
import kr.dsm.wherehere.dto.User;
import kr.dsm.wherehere.http.HttpResponseParser;

/**
 * Created by hojak on 2017-04-01.
 */

public class UserRanking extends Fragment{
    private List<User> userList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_ranking, null);

        ListView listView;
        final ListViewAdapter adapter;
        adapter = new ListViewAdapter();

        listView = (ListView) view.findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        RequestParams params = new RequestParams();
        params.put("purpose", "ranking");

        AsyncHttpClient mHttpClient = new AsyncHttpClient();
        mHttpClient.get("http://192.168.20.209:8080/account.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    userList = HttpResponseParser.parseLoadUserRankingJSON(response);

                    if(userList != null){
                        for(int i=0; i< userList.size(); ++i) {
                            adapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.user),
                                    userList.get(i).getUserId(),"게시글 : "+userList.get(i).getPostCount());

                            System.out.println("asdasd");
                        }
                    }

                }catch (JSONException j){
                    j.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable error) {
                System.out.println("Http get Fail" + statusCode);
            }
        });

        return view;
    }


}
