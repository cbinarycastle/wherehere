package kr.dsm.wherehere.rankingFragment;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import kr.dsm.wherehere.ListViewAdapter;
import kr.dsm.wherehere.R;

/**
 * Created by hojak on 2017-04-01.
 */

public class ReviewRanking extends Fragment {
    //DATA parsing 관련
    String url = "http://pho901121.cafe24.com/login/db_get_notice_posts.php";
    private static final String TAG_RESULTS = "posts";
    private static final String TAG_WRITER = "writer";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DATE = "regist_day";
    private static final String TAG_CONTENT = "content";
    JSONArray posts = null;
    ArrayList<HashMap<String, String>> noticeList;
    //UI 관련
    private RecyclerView rv;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recyclerview, container, false);
        noticeList = new ArrayList<HashMap<String, String>>();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLinearLayoutManager);

/*
        //폰 내 db와 연결하여 uid 얻어와 보내기 - Fragment 이기 때문에 context가 getActivity()
        helper = new DBhelper(getActivity(), "users2.db",null,DBhelper.DB_VER);
        url += "?uid=" + helper.getId(getActivity());
        Log.e("getuid: ", helper.getId(getActivity()));
*/

        getData(url);

        return view;
    }

    /**
     * JSON 파싱 메소드
     **/
    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                //JSON 받아온다.
                String uri = params[0];
                BufferedReader br = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String json;
                    while ((json = br.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String myJSON) {
                makeList(myJSON); //리스트를 보여줌
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    /**
     * JSON -> LIST 가공 메소드
     **/
    public void makeList(String myJSON) {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            posts = jsonObj.getJSONArray(TAG_RESULTS);
            for (int i = 0; i < posts.length(); i++) {
                //JSON에서 각각의 요소를 뽑아옴
                JSONObject c = posts.getJSONObject(i);
                String title = c.getString(TAG_TITLE);
                String writer = c.getString(TAG_WRITER);
                String date = c.getString(TAG_DATE);
                String content = c.getString(TAG_CONTENT);
                if (content.length() > 50) {
                    content = content.substring(0, 50) + "..."; //50자 자르고 ... 붙이기
                }
                if (title.length() > 16) {
                    title = title.substring(0, 16) + "..."; //18자 자르고 ... 붙이기
                }

                //HashMap에 붙이기
                HashMap<String, String> posts = new HashMap<String, String>();
                posts.put(TAG_TITLE, title);
                posts.put(TAG_WRITER, writer);
                posts.put(TAG_DATE, date);
                posts.put(TAG_CONTENT, content);

                //ArrayList에 HashMap 붙이기
                noticeList.add(posts);
            }
            //카드 리스트뷰 어댑터에 연결
            ReviewAdapter adapter = new ReviewAdapter(getActivity(), noticeList);
            Log.e("onCreate[noticeList]", "" + noticeList.size());
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
