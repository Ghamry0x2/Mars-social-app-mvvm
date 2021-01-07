package com.example.mars.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mars.R;
import com.example.mars.entities.User;
import com.example.mars.viewmodels.AuthViewModel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import static com.example.mars.utils.Constants.USER;

public class PostsActivity extends AppCompatActivity {
    AuthViewModel authViewModel;
    User user;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        initViewModels();
        getUserFromIntent();
        initAppBar();
    }

    private void getUserFromIntent() {
        user = (User) getIntent().getSerializableExtra(USER);
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

    private void initViewModels() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}