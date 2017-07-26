package edu.galileo.android.androidchat.login.ui;

/**
 * Created by Alejandro on 8/5/2017.
 */
public interface LoginView {
    void enableInput();
    void disableInput();
    void showProgress();
    void hideProgress();

    void handleSignIn();
    void handleSignUp();

    void navigationToMainScreen();
    void loginError(String Error);

    void newUsersSuccess();
    void newErrorMesaggeSing(); // mensaje cuando los campos estan vacios
    void newUsersErrors(String Error);
}
