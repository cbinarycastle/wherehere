package com.jinseong.soft.server.controller;

import com.jinseong.soft.server.dao.WhereHereDAO;
import com.jinseong.soft.server.model.Comment;
import com.jinseong.soft.server.model.Post;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dsm_025 on 2017-03-31.
 */
public class WriteCommentController extends HttpServlet {
    private String jsonData;
    private JSONObject jsonObject;
    private WhereHereDAO dao;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dao = WhereHereDAO.getInstance();
        jsonData = req.getParameter("data");
        System.out.println("Json Data : " + jsonData);

        jsonObject = new JSONObject(jsonData);
        insertToDB();
        System.out.println("DB 저장을 완료했습니다.");
    }

    private void insertToDB(){
        Comment comment = new Comment(jsonObject.getString("writer"), jsonObject.getString("content"), jsonObject.getInt("ownNo"));
        dao.insertCommentData(comment);
    }
}