package com.jinseong.soft.server.controller;

import com.jinseong.soft.server.dao.WhereHereDAO;
import com.jinseong.soft.server.model.Comment;
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
public class GetCommentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(getJsonFromDB(Integer.valueOf(req.getParameter("ownno"))));
        out.flush();
    }

    private String getJsonFromDB(int ownNo){
        WhereHereDAO dao = WhereHereDAO.getInstance();
        ArrayList<Comment> commentList = dao.selectCommentData(ownNo);
        return bindJsonFromPost(commentList).toString();
    }

    private JSONArray bindJsonFromPost(ArrayList<Comment> commentList){
        JSONArray array = new JSONArray();
        for(Comment comment :  commentList){
            JSONObject object = new JSONObject();
            object.put("writer", comment.getWriter());
            object.put("content", comment.getContent());
            array.put(comment);
        }
        return array;
    }
}
