package edu.galileo.android.androidchat.contactlist;

/**
 * Created by Alejandro on 17/7/2017.
 * clase que responde contra los eventos
 */
public interface ContactListRepository {
    void singOff();
    String getCurrentUserEmail();
    void removedContact(String email);
    void subscribeContactListEvents();
    void unsubscribeContactListEvents();
    void destroyListenerContactListEvents();
    void changedConnectionStatus(boolean online);

}
