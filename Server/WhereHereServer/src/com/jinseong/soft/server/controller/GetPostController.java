package com.jinseong.soft.server.controller;

import com.jinseong.soft.server.dao.WhereHereDAO;
import com.jinseong.soft.server.model.Post;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-03-31.
 */
public class GetPostController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GetPost 요청이 들어옴");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/text");
        PrintWriter out = resp.getWriter();
        out.print(getJsonFromDB());
        out.flush();
    }

    private String getJsonFromDB(){
        WhereHereDAO dao = WhereHereDAO.getInstance();
        ArrayList<Post> post = dao.selectPostData();
        String data = bindJsonFromPost(post).toString();
        System.out.println(data);
        return data;
    }

    private JSONArray bindJsonFromPost(ArrayList<Post> postList){
        JSONArray array = new JSONArray();
        for(Post post :  postList){
            JSONObject object = new JSONObject();
            object.put("postnum", post.getPostnum());
            object.put("title", post.getTitle());
            object.put("content", post.getContent());
            object.put("writer", post.getWriter());
            object.put("x", post.getX());
            object.put("y", post.getY());
            object.put("age", post.getAge());
            object.put("image", new JSONArray(post.getImage()));
            object.put("recommend", post.getRecommend());
            object.put("unrecommend", post.getUnrecommend());
            array.put(object);
        }
        System.out.println(array.toString());
        return array;
    }
}
