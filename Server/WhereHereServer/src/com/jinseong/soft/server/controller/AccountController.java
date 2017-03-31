package com.jinseong.soft.server.controller;

import com.jinseong.soft.server.dao.WhereHereDAO;
import com.jinseong.soft.server.model.Post;
import com.jinseong.soft.server.model.User;
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
 * Created by dsm_025 on 2017-04-01.
 */
public class AccountController extends HttpServlet {
    WhereHereDAO dao = WhereHereDAO.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Account 요청이 들어옴");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/text");
        PrintWriter out = resp.getWriter();
        if(req.getParameter("purpose").equals("register")){
            System.out.println("회원가입 요청 됨");
            createAccount(req.getParameter("id"), req.getParameter("password"));
        }else if(req.getParameter("purpose").equals("login")){
            int resultCode = isLoginSucess(req.getParameter("id"), req.getParameter("password")) == true ? 200 : 500;
            resp.setStatus(resultCode);
        }else if(req.getParameter("purpose").equals("uppost")){
            dao.upCountUserPostCount(req.getParameter("id"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        if(req.getParameter("purpose").equals("ranking")){
            System.out.println("Ranking 요청 들어옴");
            out.print(bindJsonFromUserData(dao.selectUserListByPostCount()).toString());
            out.flush();
        }
    }

    private void createAccount(String id, String password){
        dao.insertAccount(id, password);
    }

    private boolean isLoginSucess(String id, String password){
        return dao.isLoginSucess(id, password);
    }


    private JSONArray bindJsonFromUserData(ArrayList<User> userList){
        JSONArray array = new JSONArray();
        for(User user:  userList){
            JSONObject object = new JSONObject();
            object.put("id", user.getId());
            object.put("postcount", user.getPostcount());
            array.put(object);
        }
        System.out.println("Account Ranking JSON : " + array.toString());
        return array;
    }
}
