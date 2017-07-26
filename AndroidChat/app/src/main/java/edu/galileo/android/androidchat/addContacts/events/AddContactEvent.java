package edu.galileo.android.androidchat.addContacts.events;

/**
 * Created by Alejandro on 24/7/2017.
 */
public class AddContactEvent {
    boolean errorMesage = false;

    public boolean isErrorMesage() {
        return errorMesage;
    }

    public void setErrorMesage(boolean errorMesage) {
        this.errorMesage = errorMesage;
    }
}
