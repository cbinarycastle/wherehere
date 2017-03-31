package com.jinseong.soft.server.controller;

import com.jinseong.soft.server.dao.WhereHereDAO;
import com.jinseong.soft.server.model.Comment;
import com.jinseong.soft.server.model.Post;
import jdk.nashorn.internal.objects.annotations.Where;
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
public class GetInformController extends HttpServlet {
    WhereHereDAO dao = WhereHereDAO.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("데이터 요청이 들어왔습니다.");
        PrintWriter writer = resp.getWriter();

        if(req.getParameter("purpose").equals("ranking")){
            System.out.println("Ranking Data Request");

            ArrayList<Post> postList = dao.selectPostRankingData(Integer.valueOf(req.getParameter("postnum")));

            writer.println(bindJsonFromPost(postList).toString());
            writer.flush();
        }else{
            System.out.println("Recommend Up Recieve");
            dao.upCountCommentRecommend(Integer.valueOf(req.getParameter("postnum")));
        }
    }

    private JSONArray bindJsonFromPost(ArrayList<Post> postList){
        JSONArray array = new JSONArray();
        for(Post post :  postList){
            JSONObject object = new JSONObject();
            object.put("title", post.getTitle());
            String content = post.getContent();
            String writer = post.getWriter();
            double x = post.getX();
            double y = post.getY();
            int age = post.getAge();
            String image = post.getImage();
            array.put(post);
        }
        return array;
    }
}
