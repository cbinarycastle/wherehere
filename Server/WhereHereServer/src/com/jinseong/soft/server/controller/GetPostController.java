package com.jinseong.soft.server.controller;

import com.jinseong.soft.server.dao.WhereHereDAO;
import com.jinseong.soft.server.model.Post;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by dsm_025 on 2017-03-31.
 */
public class GetPostController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(getJsonFromDB(Integer.valueOf(req.getParameter("postnum"))));
        out.flush();
    }

    private String getJsonFromDB(int postNum){
        WhereHereDAO dao = WhereHereDAO.getInstance();
        Post post = dao.selectPostData(postNum);
        return bindJsonFromPost(post).toString();
    }

    private JSONObject bindJsonFromPost(Post post){
        JSONObject object = new JSONObject();
        object.put("postnum", post.getPostnum());
        object.put("content", post.getContent());
        object.put("title", post.getTitle());
        object.put("writer", post.getWriter());
        object.put("x", post.getX());
        object.put("y", post.getY());
        object.put("recommend", post.getRecommend());
        return object;
    }
}
