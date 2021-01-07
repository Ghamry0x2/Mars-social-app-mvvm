package com.example.mars.viewmodels;


import android.app.Application;

import com.example.mars.entities.User;
import com.example.mars.repos.AuthRepository;
import com.google.firebase.auth.AuthCredential;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    public LiveData<User> authenticatedUserLiveData;
    public LiveData<User> createdUserLiveData;

    public AuthViewModel(Application application) {
        super(application);
        authRepository = new AuthRepository();
    }

    public void signInWithGoogle(AuthCredential googleAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignInWithGoogle(googleAuthCredential);
    }

    public void signInWithFacebook(AuthCredential facebookAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignInWithFacebook(facebookAuthCredential);
    }

    public void createUser(User authenticatedUser) {
        createdUserLiveData = authRepository.createUserInFirestoreIfNotExists(authenticatedUser);
    }

    public void signOut() {
        authRepository.signOut();
    }
}