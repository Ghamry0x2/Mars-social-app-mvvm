package com.example.mars.repos;

import com.example.mars.entities.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import static com.example.mars.utils.Constants.POSTS;
import static com.example.mars.utils.Helpers.logErrorMessage;

public class PostsRepository {
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference postsRef = rootRef.collection(POSTS);
    MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    public MutableLiveData<List<Post>> getAllPosts() {

        postsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Post> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.toObject(Post.class));
                    }
                    posts.setValue(list);
                } else {
                    logErrorMessage(task.getException().getMessage());
                }
            }

        });
        return posts;
    }

    public void addPost(Post post) {
        DocumentReference docRef = postsRef.document();
        docRef.set(post);
        List<Post> ps = posts.getValue();
        ps.add(0, post);
        posts.setValue(ps);
    }
}
