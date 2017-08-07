package edu.galileo.android.androidchat.addContacts.ui;

/**
 * Created by Alejandro on 24/7/2017.
 * vista de mi app para agregar un contact
 */
public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void contactAdded();
    void contactNotAdded();
    void contactEmpty();
}
