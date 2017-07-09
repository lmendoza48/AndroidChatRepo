package edu.galileo.android.androidchat.login;

/**
 * Created by Alejandro on 8/5/2017.
 */
public interface LoginInteractor {
    void checkSession();
    void doSignIn(String email, String password);
    void doSignUp(String email, String password);
}
