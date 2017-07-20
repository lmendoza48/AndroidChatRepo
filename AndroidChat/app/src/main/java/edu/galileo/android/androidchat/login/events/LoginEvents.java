package edu.galileo.android.androidchat.login.events;

/**
 * clase encargada del manejo de los eventos
 * Created by Alejandro on 27/5/2017.
 */
public class LoginEvents {
    public final static int onSignInError = 0;
    public final static int onSignUpError = 1;
    public final static int onSignInSucces = 2;
    public final static int onSignUpSucces = 3;
    public final static int onFailedToRecoverSession = 4;// atributo para saber si existe una sesion abierta y poder recuperarla
    public final static int onSignInErrorSend = 5; // atributo para saber si tengo datos al autenticarme o crear un usuario
    private int eventType;
    private String errorMessage;


    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
