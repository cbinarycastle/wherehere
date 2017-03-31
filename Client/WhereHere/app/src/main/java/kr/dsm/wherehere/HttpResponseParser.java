package kr.dsm.wherehere;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BeINone on 2017-03-31.
 */

public class HttpResponseParser {

    public static List<Map> parseLoadPlaceJSON(JSONArray rootJSONArray) throws JSONException {
        List<Map> mapList = new ArrayList<>();

        for (int index = 0; index < rootJSONArray.length(); index++) {
            JSONObject mapJSONObject = rootJSONArray.getJSONObject(index);

            int postNum = mapJSONObject.getInt("postnum");
            String content = mapJSONObject.getString("content");
            String title = mapJSONObject.getString("title");
            String writer = mapJSONObject.getString("writer");
            double x = mapJSONObject.getDouble("x");
            double y = mapJSONObject.getDouble("y");
            String image = mapJSONObject.getString("image");
            int recommend = mapJSONObject.getInt("recommend");
            int unRecommend = mapJSONObject.getInt("unrecommend");

            mapList.add(new Map(postNum, content, title, writer, x, y, image, recommend, unRecommend));
        }

        return mapList;
    }

}
