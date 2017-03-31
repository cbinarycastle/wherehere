package kr.dsm.wherehere.http;

import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.dsm.wherehere.dto.Map;
import kr.dsm.wherehere.dto.User;

/**
 * Created by BeINone on 2017-03-31.
 */

public class HttpResponseParser {

    public static SparseArray<Map> parseLoadPlaceJSON(JSONArray rootJSONArray) throws JSONException {
        SparseArray<Map> mapSparseArray = new SparseArray<>();

        for (int index = 0; index < rootJSONArray.length(); index++) {
            JSONObject mapJSONObject = rootJSONArray.getJSONObject(index);

            int postNum = mapJSONObject.getInt("postnum");
            String content = mapJSONObject.getString("content");
            String title = mapJSONObject.getString("title");
            String writer = mapJSONObject.getString("writer");
            double x = mapJSONObject.getDouble("x");
            double y = mapJSONObject.getDouble("y");
            int recommend = mapJSONObject.getInt("recommend");
            int unRecommend = mapJSONObject.getInt("unrecommend");

            List<String> images = new ArrayList<>();
            JSONArray imageJSONArray = mapJSONObject.getJSONArray("image");
            for (int imageIndex = 0; imageIndex < imageJSONArray.length(); imageIndex++) {
                JSONObject imageJSONObject = imageJSONArray.getJSONObject(imageIndex);
                images.add(imageJSONObject.getString("data"));
            }

            mapSparseArray.append(postNum,
                    new Map(postNum, content, title, writer, x, y, images, recommend, unRecommend));
        }

        return mapSparseArray;
    }

    public static List<User> parseLoadUserRankingJSON(JSONArray rootJSONArray) throws JSONException {
        List<User> userList = new ArrayList<>();

        for (int i = 0; i < rootJSONArray.length(); ++i) {
            JSONObject jsonObject = (JSONObject) rootJSONArray.get(i);

            String id = jsonObject.getString("id");
            int postCount = jsonObject.getInt("postcount");

            userList.add(new User(id, postCount));
        }
        return userList;
    }

    public static List<Map> parseLoadPostInfo(JSONArray rootJSONArray) throws JSONException {
        List<Map> mapList = new ArrayList<>();

        for (int index = 0; index < rootJSONArray.length(); index++) {
            JSONObject mapJSONObject = rootJSONArray.getJSONObject(index);

            int postNum = mapJSONObject.getInt("postnum");
            String content = mapJSONObject.getString("content");
            String title = mapJSONObject.getString("title");
            String writer = mapJSONObject.getString("writer");
            double x = mapJSONObject.getDouble("x");
            double y = mapJSONObject.getDouble("y");
            int recommend = mapJSONObject.getInt("recommend");
            int unRecommend = mapJSONObject.getInt("unrecommend");

            List<String> images = new ArrayList<>();
            JSONArray imageJSONArray = mapJSONObject.getJSONArray("image");
            for (int imageIndex = 0; imageIndex < imageJSONArray.length(); imageIndex++) {
                JSONObject imageJSONObject = imageJSONArray.getJSONObject(imageIndex);
                images.add(imageJSONObject.getString("data"));
            }
            mapList.add(new Map(postNum, content, title, writer, x, y, images, recommend, unRecommend));
        }
        return mapList;
    }
}
