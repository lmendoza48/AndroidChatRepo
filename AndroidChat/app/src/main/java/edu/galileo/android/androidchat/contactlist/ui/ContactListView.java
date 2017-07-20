package edu.galileo.android.androidchat.contactlist.ui;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by Alejandro on 17/7/2017.
 */
public interface ContactListView {
    void onContactAdd(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
