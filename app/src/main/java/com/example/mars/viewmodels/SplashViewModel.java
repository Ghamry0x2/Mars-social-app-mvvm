package com.example.mars.viewmodels;

import android.app.Application;

import com.example.mars.entities.User;
import com.example.mars.repos.SplashRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SplashViewModel extends AndroidViewModel {
    private SplashRepository splashRepository;

    public LiveData<User> isUserAuthenticatedLiveData;
    public LiveData<User> userLiveData;

    public SplashViewModel(Application application) {
        super(application);
        splashRepository = new SplashRepository();
    }

    public void checkIfUserIsAuthenticated() {
        isUserAuthenticatedLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    public void setUid(String uid) {
        userLiveData = splashRepository.addUserToLiveData(uid);
    }
}
