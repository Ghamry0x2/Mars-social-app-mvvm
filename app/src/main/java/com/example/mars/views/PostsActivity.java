package com.example.mars.views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        LinearLayout linearLayout = new LinearLayout(actionBar.getThemedContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
                | Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(layoutParams);

        ImageView userAvatar = new ImageView(linearLayout.getContext());
        userAvatar.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        Glide.with(PostsActivity.this)
                .load(user.avatar)
                .centerCrop()
                .circleCrop()
                .into(userAvatar);

        TextView title = new TextView(linearLayout.getContext());
        title.setText(getString(R.string.feeds));
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);
        title.setPadding(50,10, 0, 30);
        title.setTextColor(Color.WHITE);

        linearLayout.addView(userAvatar);
        linearLayout.addView(title);

        actionBar.setCustomView(linearLayout);
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