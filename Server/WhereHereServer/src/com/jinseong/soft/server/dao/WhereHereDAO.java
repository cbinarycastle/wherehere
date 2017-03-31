package com.jinseong.soft.server.dao;

import com.jinseong.soft.server.model.Comment;
import com.jinseong.soft.server.model.Post;

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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wherehere", user, password);
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
        System.out.println(sql);
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            System.out.println("DB Error");
            e.printStackTrace();
        }
    }

    public void insertCommentData(Comment comment) {
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
            data.setRecommend(rs.getInt("recommeand"));
            data.setUnRecommand(rs.getInt("unrecommand"));
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
        System.out.println("select method excute");

        ArrayList<Post> postList = new ArrayList<>();
        String sql = "select * from post order by recommand ASC";
        System.out.println("sql" + sql);
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
                post.setRecommend(rs.getInt("recommand"));
                post.setUnRecommand(rs.getInt("unrecommand"));
                postList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception");

        }
        return postList;
    }


    public void upCountPostRecommend(int postNum){
        String sql = "update post set recommend = recommend + 1 where postnum = " + postNum;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void downCountPostRecommend(int postNum){
        String sql = "update post set unrecommend = unrecommend + 1 where postnum = " + postNum;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upCountCommentRecommend(int commentnum){
        String sql = "update comment set recommend = recommend + 1 where commentnum = " + commentnum;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void downCountCommentRecommend(int commentnum){
        String sql = "update comment set unrecommend = unrecommend + 1 where commentnum = " + commentnum;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
