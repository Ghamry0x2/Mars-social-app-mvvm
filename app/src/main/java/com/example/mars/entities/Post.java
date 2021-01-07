package com.example.mars.entities;

import java.io.Serializable;

public class Post implements Serializable {
    public String title;
    public String desc;
    @SuppressWarnings("WeakerAccess")
    public String authorId;
    public String authorName;
    public String authorAvatar;

    public Post() {}

    public Post(String title, String desc) {
        this.title = title;
        this.desc = desc;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
    }

    public Post(String title, String desc, String authorId, String authorName, String authorAvatar) {
        this.title = title;
        this.desc = desc;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
    }

    public void setAuthor(User user) {
        this.authorId = user.uid;
        this.authorName = user.name;
        this.authorAvatar = user.avatar;
    }

    public String toString() {
        return "title: " + title
                + " desc: " + desc
                + " authorId: " + authorId
                + " authorName: " + authorName
                + " authorAvatar: " + authorAvatar;
    }
}
