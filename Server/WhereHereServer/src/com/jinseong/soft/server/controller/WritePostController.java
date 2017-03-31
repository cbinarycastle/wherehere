package com.jinseong.soft.server.controller;

import com.jinseong.soft.server.dao.WhereHereDAO;
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
public class WritePostController extends HttpServlet {
    private String jsonData;
    private JSONObject jsonObject;
    private WhereHereDAO dao;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("글쓰기 요청이 들어옴");
        dao = WhereHereDAO.getInstance();
        jsonData = req.getParameter("data");
        jsonObject = new JSONObject(jsonData);
        insertToDB();
        System.out.println("DB 저장을 완료했습니다.");
    }

    private void insertToDB(){
        Post post = new Post(jsonObject.getString("title"), jsonObject.getString("content"), jsonObject.getString("writter"),
                jsonObject.getDouble("x"), jsonObject.getDouble("y"), jsonObject.getInt("age"), jsonObject.getString("image"));
        dao.insertPostData(post);
    }
}
