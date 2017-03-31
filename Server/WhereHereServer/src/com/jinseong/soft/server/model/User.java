package com.jinseong.soft.server.model;

/**
 * Created by dsm_025 on 2017-04-01.
 */
public class User {
    private String id;
    private int postcount;

    public User(String id, int postcount) {
        this.id = id;
        this.postcount = postcount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPostcount() {
        return postcount;
    }

    public void setPostcount(int postcount) {
        this.postcount = postcount;
    }
}
