package kr.dsm.wherehere.dto;

/**
 * Created by hojak on 2017-04-01.
 */

public class User {

    private String userId;
    private int postCount;

    public User(String id, int count) {
        this.userId = id;
        this.postCount = count;
    }

    public String getUserId(){
        return userId;
    }

    public int getPostCount(){
        return postCount;
    }
}
