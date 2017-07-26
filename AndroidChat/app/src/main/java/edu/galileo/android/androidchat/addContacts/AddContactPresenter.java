package edu.galileo.android.androidchat.addContacts;

import edu.galileo.android.androidchat.addContacts.events.AddContactEvent;

/**
 * Created by Alejandro on 24/7/2017.
 */
public interface AddContactPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}
