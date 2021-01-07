package com.example.mars.viewmodels;


import android.app.Application;

import com.example.mars.entities.Post;
import com.example.mars.repos.PostsRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PostsViewModel extends AndroidViewModel {
    private PostsRepository postsRepository;
    public LiveData<List<Post>> postsLiveData;

    public PostsViewModel(Application application) {
        super(application);
        postsRepository = new PostsRepository();
    }

    public void getAllPosts() {
        postsLiveData = postsRepository.getAllPosts();
    }

    public void createPost(Post post) {
        postsRepository.addPost(post);
    }
}