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
    private JSONObject jsonObject;
    private WhereHereDAO dao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        dao = WhereHereDAO.getInstance();
//        PrintWriter writer = resp.getWriter();

        System.out.println(req.getParameter("data"));
        if((jsonData = req.getParameter("data")) != null) {
            System.out.println("Write Post Data 를 받음");
            jsonObject = new JSONObject(jsonData);
            System.out.println("Json Data : " + jsonObject);

            String root = req.getSession().getServletContext().getRealPath("/");
            String pathname = root + "/images";
            System.out.println("이미지 저장 폴더 경로 : " + pathname);

            File f = new File(pathname);
            if (!f.exists()) {
                // 폴더가 존재하지 않으면 폴더 생성
                f.mkdirs();
            }

            String encType = "UTF-8";
            int maxFilesize = 5 * 1024 * 1024;

            MultipartRequest mr = new MultipartRequest(req, pathname, maxFilesize,
                    encType, new DefaultFileRenamePolicy());

            System.out.println(mr.getFile("image.jpg"));
            insertToDB();
        }else{
            System.out.println("Json Data가 잘못되었습니다.");
//            writer.print("Json Data가 잘못되었습니다.");
//            writer.flush();
        }

    }

    private void insertToDB() {
        Post post = new Post(jsonObject.getString("title"), jsonObject.getString("content"), jsonObject.getString("writer"),
                jsonObject.getDouble("x"), jsonObject.getDouble("y"), jsonObject.getInt("age"), jsonObject.getJSONArray("image").toString());
        dao.insertPostData(post);
    }

    protected void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            System.out.println("image 요청 들어옴");


            String encType = "UTF-8";
            int maxFilesize = 5 * 1024 * 1024;
            String pathname = null;
            // MultipartRequest(request, 저장경로[, 최대허용크기, 인코딩케릭터셋, 동일한 파일명 보호 여부])
            MultipartRequest mr = new MultipartRequest(req, pathname, maxFilesize,
                    encType, new DefaultFileRenamePolicy());

            File file1 = mr.getFile("temp.txt");

            System.out.println(req.getParameter("title")); // null
            System.out.println(mr.getParameter("title")); // 입력된 문자
            String base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(
                Paths.get("temp.txt")));
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }
}
