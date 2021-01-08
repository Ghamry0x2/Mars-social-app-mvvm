package com.example.mars.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mars.R;
import com.example.mars.adapters.PostsAdapter;
import com.example.mars.entities.Post;
import com.example.mars.entities.User;
import com.example.mars.viewmodels.AuthViewModel;
import com.example.mars.viewmodels.PostsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.mars.utils.Constants.POST;
import static com.example.mars.utils.Constants.RC_ADD_POST;
import static com.example.mars.utils.Constants.USER;

public class PostsActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;
    private User user;

    private PostsViewModel postsViewModel;
    private PostsAdapter postsAdapter;

    private RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        initViewModels();
        getUserFromIntent();
        initAppBar();
        initFab();
        initRecyclerView();

        loadPosts();
        configureFCM();
    }

    private void configureFCM() {
        FirebaseMessaging.getInstance().subscribeToTopic("posts");
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.postsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adding divider between posts
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                linearLayoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void loadPosts() {
        postsViewModel.getAllPosts();
        postsViewModel.postsLiveData.observe(this, data -> {
            postsAdapter = new PostsAdapter(this, data);
            recyclerView.setAdapter(postsAdapter);
        });
    }

    private void getUserFromIntent() {
        user = (User) getIntent().getSerializableExtra(USER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_ADD_POST && data != null) {
            Post post = (Post) data.getSerializableExtra(POST);
            post.setAuthor(user);
            postsViewModel.createPost(post);
            Toast.makeText(PostsActivity.this, R.string.add_post_success, Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initAppBar() {
        Toolbar appbar = (Toolbar) findViewById(R.id.customToolbar);
        setSupportActionBar(appbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ImageView userAvatar = findViewById(R.id.userAvatar);
        Glide.with(PostsActivity.this)
                .load(user.avatar)
                .centerCrop()
                .circleCrop()
                .into(userAvatar);
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostsActivity.this, AddPostActivity.class);
                startActivityForResult(intent, RC_ADD_POST);
            }
        });
    }

    private void initViewModels() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                authViewModel.signOut();
                startActivity(new Intent(this, AuthActivity.class));
                finish();
                return true;
            case R.id.refresh_feeds_menu:
                postsViewModel.getAllPosts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}