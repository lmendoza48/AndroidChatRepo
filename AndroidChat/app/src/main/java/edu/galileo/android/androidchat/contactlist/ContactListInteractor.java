package edu.galileo.android.androidchat.contactlist;

/**
 * Created by Alejandro on 17/7/2017.
 */
public interface ContactListInteractor {
    void subscribe();
    void unsubscribe();
    void destroyListener();
    void removeContact(String email);
}
