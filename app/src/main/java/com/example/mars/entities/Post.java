package com.example.mars.entities;

import com.example.mars.utils.Helpers;

import java.io.Serializable;

public class Post implements Serializable, Comparable<Post> {
    public String title;
    public String desc;
    public long createdAt;
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
        this.createdAt = System.currentTimeMillis();
    }

    public Post(String title, String desc, String authorId, String authorName, String authorAvatar, long createdAt) {
        this.title = title;
        this.desc = desc;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.createdAt = createdAt;
    }

    public void setAuthor(User user) {
        this.authorId = user.uid;
        this.authorName = user.name;
        this.authorAvatar = user.avatar;
    }

    public String formattedCreationDate() {
        return Helpers.getTimeAgo(this.createdAt);
    }

    public String formattedTTS() {
        return this.authorName + " added this post " + this.formattedCreationDate()
                + "\nHe said: " + this.title
                + "\nHe also added: " + this.desc;
    }

    public String toString() {
        return "title: " + title
                + " desc: " + desc
                + " authorId: " + authorId
                + " authorName: " + authorName
                + " authorAvatar: " + authorAvatar
                + " createdAt: " + createdAt;
    }

    @Override
    public int compareTo(Post p) {
        if (this.formattedCreationDate() == null || p.formattedCreationDate() == null) {
            return 0;
        }
        return new Long(this.createdAt).compareTo(p.createdAt);
    }
}
