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
        resp.setCharacterEncoding("UTF-8");
        System.out.println("Get Info 데이터 요청이 들어왔습니다.");
        PrintWriter writer = resp.getWriter();

        String purpose = req.getParameter("purpose");

        if(purpose.equals("ranking")){
            System.out.println("Ranking Data Request");
            ArrayList<Post> postList = dao.selectPostRankingData(Integer.valueOf(req.getParameter("postnum")));
            writer.println(bindJsonFromPost(postList).toString());
            writer.flush();
        }else if(purpose.equals("postrec")){
            System.out.println("Post Recommend Up Recieve");
            dao.upCountPostRecommend(Integer.valueOf(req.getParameter("postnum")));
        }else if(purpose.equals("postunrec")){
            System.out.println("Post UnRecommend Up Recieve");
            dao.downCountPostRecommend(Integer.valueOf(req.getParameter("postnum")));
        }else if(purpose.equals("comrec")){
            System.out.println("Comment Recommend Up Recieve");
            dao.upCountCommentRecommend(Integer.valueOf(req.getParameter("commentnum")));
        }else if(purpose.equals("comunrec")){
            System.out.println("Comment UnRecommend Up Recieve");
            dao.downCountCommentRecommend(Integer.valueOf(req.getParameter("commentnum")));
        }else{
            writer.print("요청 파라미터가 잘못되었습니다.");
            writer.flush();
        }
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
            object.put("image", post.getImage().replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", ""));
            object.put("recommend", post.getRecommend());
            object.put("unrecommend", post.getUnrecommend());
            array.put(object);
        }
        return array;
    }
}
