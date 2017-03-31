package com.jinseong.soft.server.controller;

import com.jinseong.soft.server.dao.WhereHereDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by dsm_025 on 2017-03-31.
 */
public class GetImagePathController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/text");
        PrintWriter out = resp.getWriter();
        out.print(getImagePathFromDB(Integer.valueOf(req.getParameter("postnum"))));
        out.flush();
    }

    private String getImagePathFromDB(int postNum){
        WhereHereDAO dao = WhereHereDAO.getInstance();
        return dao.selectImagePath(postNum);
    }
}
