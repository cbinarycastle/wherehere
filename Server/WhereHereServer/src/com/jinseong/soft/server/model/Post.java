package com.jinseong.soft.server.model;

/**
 * Created by dsm_025 on 2017-03-31.
 */
public class Post {
    private int postnum;
    private String title;
    private String content;
    private String writer;
    private double x;
    private double y;
    private int age;
    private String image;
    private int recommend;
    private int unrecommand;

    public int getUnrecommand() {
        return unrecommand;
    }

    public void setUnRecommand(int unrecommand) {
        this.unrecommand = unrecommand;
    }

    public Post(String title, String content, String writer, double x, double y, int age, String image) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.x = x;
        this.y = y;
        this.age = age;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPostnum() {
        return postnum;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
}
