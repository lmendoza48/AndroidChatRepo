package edu.galileo.android.androidchat.addContacts.events;

/**
 * Created by Alejandro on 24/7/2017.
 */
public class AddContactEvent {
    public final static int addContactSucces = 0;
    public final static int errorEmptyAddContact = 1;
    public final static int onFailedError = 2;// atributo para saber si existe una sesion abierta y poder recuperarla
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
