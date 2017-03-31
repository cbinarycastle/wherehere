package com.jinseong.soft.server.model;

/**
 * Created by dsm_025 on 2017-03-31.
 */
public class Comment {
    private String writer;
    private String content;
    private int no;
    private int ownNo;
    private int recommend;
    private int unrecommend;

    public Comment(String writer, String content, int ownNo) {
        this.writer = writer;
        this.content = content;
        this.ownNo = ownNo;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getOwnNo() {
        return ownNo;
    }

    public void setOwnNo(int ownNo) {
        this.ownNo = ownNo;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public int getUnrecommend() {
        return unrecommend;
    }

    public void setUnrecommend(int unrecommend) {
        this.unrecommend = unrecommend;
    }
}
