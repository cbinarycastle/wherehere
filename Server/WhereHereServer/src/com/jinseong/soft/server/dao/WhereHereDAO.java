package com.jinseong.soft.server.dao;

import com.jinseong.soft.server.model.Comment;
import com.jinseong.soft.server.model.Post;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by dsm_025 on 2017-03-31.
 */
public class WhereHereDAO {
    private final String password = "4112665aA";
    private final String user = "root";
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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auth", user, password);
            st = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPostData(Post post) {
        String sql = "insert into post(title, content, writer, x, y, age, image) + values ("
                + post.getTitle() + ", " + post.getTitle() + ", " + post.getContent() + ", "
                + post.getWriter() + ", " + post.getX() + ", " + post.getY() + ", " + post.getAge() + ", " + post.getImage() + ")";
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPostData(Comment comment) {
        String sql = "insert into post(writer, content, ownno) + values ("
                + comment.getWriter() + ", " + comment.getContent() + ", " + comment.getOwnNo() + ")";
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Post selectPostData(int num) {
        Post data = null;
        String sql = "select * from post where postnum = " + num;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            String title = rs.getString("title");
            String content = rs.getString("content");
            String writer = rs.getString("writer");
            double x = rs.getInt("x");
            double y = rs.getInt("y");
            int age = rs.getInt("age");
            String image = rs.getString("image");

            data = new Post(title, content, writer, x, y, age, image);
            data.setRecommend(rs.getInt("image"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
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
                commentList.add(new Comment(writer, content, ownNo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentList;
    }

    public String selectImagePath(int postNum){
        String sql = "select image from post where postnum = " + postNum;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            return rs.getString("image");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void upCountRecommend(int postNum){
        String sql = "update post set recommend = recommend + 1 where postnum = " + postNum;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Post> selectPostRankingData(int postNum) {
        ArrayList<Post> postList = new ArrayList<>();
        String sql = "select * from comment order by recommend ASC";
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
                postList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postList;
    }
}
