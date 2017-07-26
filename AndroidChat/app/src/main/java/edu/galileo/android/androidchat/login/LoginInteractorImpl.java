package edu.galileo.android.androidchat.login;

import edu.galileo.android.androidchat.login.LoginInteractor;
import edu.galileo.android.androidchat.login.LoginRepository;
import edu.galileo.android.androidchat.login.LoginRepositoryImpl;

/**
 * Created by Alejandro on 21/5/2017.
 * para los casos de uso q tenemos
 */
public class LoginInteractorImpl implements LoginInteractor {
    private LoginRepository loginRepository;

    public LoginInteractorImpl() {
        loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkSession() {
        loginRepository.checkSession();
    }

    @Override
    public void doSignIn(String email, String password) {
        loginRepository.signIn(email, password);
    }

    @Override
    public void doSignUp(String email, String password) {
        loginRepository.signUp(email, password);
    }
}
