package com.example.mars.views;

import android.content.Intent;
import android.os.Bundle;

import com.example.mars.entities.User;
import com.example.mars.viewmodels.SplashViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import static com.example.mars.utils.Constants.USER;

public class SplashActivity extends AppCompatActivity {
    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSplashViewModel();
        checkIfUserIsAuthenticated();
    }

    private void initSplashViewModel() {
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
    }

    private void checkIfUserIsAuthenticated() {
        splashViewModel.checkIfUserIsAuthenticated();
        splashViewModel.isUserAuthenticatedLiveData.observe(this, user -> {
            if (!user.isAuthenticated) {
                goToAuthInActivity();
                finish();
            } else {
                getUserFromDatabase(user.uid);
            }
        });
    }

    private void goToAuthInActivity() {
        Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
        startActivity(intent);
    }

    private void getUserFromDatabase(String uid) {
        splashViewModel.setUid(uid);
        splashViewModel.userLiveData.observe(this, user -> {
            goToMainActivity(user);
            finish();
        });
    }

    private void goToMainActivity(User user) {
        Intent intent = new Intent(SplashActivity.this, PostsActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }
}