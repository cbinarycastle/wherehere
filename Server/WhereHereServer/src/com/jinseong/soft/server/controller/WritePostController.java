package com.jinseong.soft.server.controller;

import com.jinseong.soft.server.dao.WhereHereDAO;
import com.jinseong.soft.server.model.Post;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Created by dsm_025 on 2017-03-31.
 */
public class WritePostController extends HttpServlet {
    private String jsonData;
    private String image;
    private JSONObject jsonObject;
    private WhereHereDAO dao;
    private static int imageCount = 0;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        dao = WhereHereDAO.getInstance();

        System.out.println(req.getParameter("data"));
        if((jsonData = req.getParameter("data")) != null) {
            System.out.println("Write Post Data 를 받음");
            jsonObject = new JSONObject(jsonData);
            System.out.println("Json Data : " + jsonObject);
            image = req.getParameter("image");
            insertToDB();
        }else{
            System.out.println("Json Data가 잘못되었습니다.");
        }

    }

    private void insertToDB() {
        Post post = new Post(jsonObject.getString("title"), jsonObject.getString("content"), jsonObject.getString("writer"),
                jsonObject.getDouble("x"), jsonObject.getDouble("y"), jsonObject.getInt("age"), image);
        dao.insertPostData(post);
    }
}
