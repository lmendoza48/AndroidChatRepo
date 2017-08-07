package edu.galileo.android.androidchat.login;

import edu.galileo.android.androidchat.login.events.LoginEvents;

/**
 * Created by Alejandro on 8/5/2017.
 */
public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void onResume();
    void onPause();
    void checkForAuthenticatedUsers();
    void validateLogin(String email, String password);
    void registerNewUsers(String email, String password, String name);
    void onEventMainThread(LoginEvents events);

}
