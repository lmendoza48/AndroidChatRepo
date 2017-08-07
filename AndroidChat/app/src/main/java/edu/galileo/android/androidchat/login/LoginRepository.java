package edu.galileo.android.androidchat.login;

/**
 * Created by Alejandro on 8/5/2017.
 */
public interface LoginRepository {
    void signIn(String email, String password);
    void signUp(String email, String password, String name);
    void checkSession();
}
