package com.jinseong.soft.server.dao;

import com.jinseong.soft.server.model.Comment;
import com.jinseong.soft.server.model.Post;
import com.jinseong.soft.server.model.User;
import org.json.JSONArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-03-31.
 */
public class WhereHereDAO {
    private final String PASSWORD = "4112665aA";
    private final String USER = "root";
    private static WhereHereDAO instance = new WhereHereDAO();
    private Statement st = null;
    private Connection connection = null;

    public static WhereHereDAO getInstance() {
        return instance;
    }

    private WhereHereDAO() {
        System.out.println("DB 연결 성공");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wherehere", USER, PASSWORD);
            st = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPostData(Post post) {
        String sql = "insert into post(title, content, writer, x, y, age, image) values (?,?,?,?,?,?,?)";
        try {
            Statement statement = connection.createStatement();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1, post.getTitle());
            preparedStmt.setString(2, post.getContent());
            preparedStmt.setString(3, post.getWriter());
            preparedStmt.setDouble(4, post.getX());
            preparedStmt.setDouble(5, post.getY());
            preparedStmt.setInt(6, post.getAge());
            preparedStmt.setString(7, post.getImage());
            preparedStmt.execute();

        } catch (SQLException e) {
            System.out.println("DB Error");
            e.printStackTrace();
        }
    }

    public void insertCommentData(Comment comment) {
        String sql = "insert into post(writer, content, ownno) values (?, ?, ?)";
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1, comment.getWriter());
            preparedStmt.setString(2, comment.getContent());
            preparedStmt.setInt(3, comment.getOwnNo());
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Post> selectPostData() {
        ArrayList<Post> dataList = new ArrayList<>();
        String sql = "select * from post";
        try {
            System.out.println("Post Data 보내는 중");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");
                double x = rs.getInt("x");
                double y = rs.getInt("y");
                int age = rs.getInt("age");
                String image = rs.getString("image");

                Post data = new Post(title, content, writer, x, y, age, image);
                data.setRecommend(rs.getInt("recommend"));
                data.setUnRecommend(rs.getInt("unrecommend"));
                dataList.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public ArrayList<Comment> selectCommentData(int postNum) {
        ArrayList<Comment> commentList = new ArrayList<>();
        String sql = "select * from comment where ownNo = " + postNum;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String writer = rs.getString("writer");
                String content = rs.getString("content");
                int ownNo = rs.getInt("ownno");
                Comment data = new Comment(writer, content, ownNo);
                data.setRecommend(rs.getInt("recommend"));
                data.setUnrecommend(rs.getInt("unrecommend"));
                commentList.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentList;
    }

//    public String selectImagePath(int postNum){
//        String sql = "select image from post where postnum = " + postNum;
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//            rs.next();
//            return rs.getString("image");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public ArrayList<Post> selectPostRankingData(int postNum) {
        ArrayList<Post> postList = new ArrayList<>();
        String sql = "select * from post order by recommend ASC";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");
                String writer = rs.getString("writer");

                double x = rs.getInt("x");
                double y = rs.getInt("y");
                int age = rs.getInt("age");
                String image = rs.getString("image");

                Post post = new Post(title, content, writer, x, y, age, image);
                post.setRecommend(rs.getInt("recommend"));
                post.setUnRecommend(rs.getInt("unrecommend"));
                postList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception");
        }
        return postList;
    }


    public void upCountPostRecommend(int postNum) {
        String sql = "update post set recommend = recommend + 1 where postnum = " + postNum;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void downCountPostRecommend(int postNum) {
        String sql = "update post set unrecommend = unrecommend + 1 where postnum = " + postNum;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upCountCommentRecommend(int commentnum) {
        String sql = "update comment set recommend = recommend + 1 where commentnum = " + commentnum;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void downCountCommentRecommend(int commentnum) {
        String sql = "update comment set unrecommend = unrecommend + 1 where commentnum = " + commentnum;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAccount(String id, String password) {
        String sql = "insert into user(id, password) values(?, ?)";
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1, id);
            preparedStmt.setString(2, password);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> selectUserListByPostCount(){
        ArrayList<User> list = new ArrayList<>();
        String sql = "select  * from user order by postcount ASC";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
               list.add(new User(rs.getString("id"), rs.getInt("postcount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void upCountUserPostCount(String id){
        String sql = "update user set postcount = postcount + 1 where id = " + id;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoginSucess(String id, String password){
        String sql = "select * from user where id = '" + id + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            if(rs != null && rs.getString("password").equals(password)){
                System.out.println("로그인 성공");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
