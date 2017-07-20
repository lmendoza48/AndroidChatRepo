package edu.galileo.android.androidchat.contactlist;

import edu.galileo.android.androidchat.contactlist.event.ContactListEvent;

/**
 * Created by Alejandro on 17/7/2017.
 */
public interface ContactListPresenter {
    void onCreate();
    void onDestroy();
    void onPause();
    void onResume();

    String getCurrentUserEmail();
    void singOff();
    void removeContact(String email);
    void onEventMainThread(ContactListEvent event);
}
