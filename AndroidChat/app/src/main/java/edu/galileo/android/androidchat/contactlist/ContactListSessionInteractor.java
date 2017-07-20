package edu.galileo.android.androidchat.contactlist;

/**
 * Created by Alejandro on 17/7/2017.
 */
public interface ContactListSessionInteractor {
    void signOff();
    String getCurrentUserEmail();
    void changedConnectionStatus(boolean online);
}
