package edu.galileo.android.androidchat.login;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.androidchat.lib.EventBus;
import edu.galileo.android.androidchat.lib.GreenRobotEventBus;
import edu.galileo.android.androidchat.login.ui.LoginView;
import edu.galileo.android.androidchat.login.events.LoginEvents;

/**
 * Created by Alejandro on 15/5/2017.
 * clase que me perimte implementar la interfaz del presenter de mi app+
 * logica para mostrar la vista "View"
 */
public class LoginPresenterImpl implements LoginPresenter {
   private LoginView loginView;
   private LoginInteractor loginInteractor;
   private EventBus eventBus;

    /*a traves de mi interactuar me comunico con mi view*/
    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    /*
    * Metodo para validar usuario*/
    @Override
    public void checkForAuthenticatedUsers() {
        if(loginView != null){
            loginView.disableInput();
            loginView.showProgress();
        }
        loginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if(loginView != null){
            loginView.disableInput();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUsers(String email, String password, String name) {
        if(loginView != null){
            loginView.disableInput();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email,password,name);
    }

    /**
     * metodo que me va a permitir saber en que evento estoy
     * @param events type events
     */
    @Override
    @Subscribe
    public void onEventMainThread(LoginEvents events) {
        switch (events.getEventType()){
            case LoginEvents.onSignUpSucces:
                onSignUpSucces();
                break;
            case LoginEvents.onSignInSucces:
                onSignInSucces();
                break;
            case LoginEvents.onSignInError:
                onSignInError(events.getErrorMessage());
                break;
            case LoginEvents.onSignUpError:
                onSignUpError(events.getErrorMessage());
                break;
            case LoginEvents.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
            case LoginEvents.onSignInErrorSend:
                onSignSuccesWithoutError();
                break;
        }

    }

    /**
     * metodo cuando falla al carga sesion
     */
    private void onFailedToRecoverSession() {
        if(loginView != null){
            loginView.hideProgress();
            loginView.enableInput();
        }
        Log.e("ErrorLoginPresenterImpl","onFailedToRecoverSession");
    }

    /**
     * metodo que si todo esta bien carga la session y pasa a mi otra actividad
     */
    private void onSignInSucces(){
        if(loginView != null){
            loginView.navigationToMainScreen();
        }
    }

    private void onSignUpSucces(){
        if(loginView != null){
            loginView.newUsersSuccess();
        }
    }

    /**
     * metodo para comprobar q no este vacio los campos
     */
    private void onSignSuccesWithoutError(){
        if(loginView != null){
            loginView.hideProgress();
            loginView.enableInput();
            loginView.newErrorMesaggeSing();
        }
    }

    private void onSignInError(String error){
        if(loginView != null){
            loginView.hideProgress();
            loginView.enableInput();
            loginView.loginError(error);
        }
    }

    private void onSignUpError(String error){
        if(loginView != null){
            loginView.hideProgress();
            loginView.enableInput();
            loginView.newUsersErrors(error);
        }
    }
}
