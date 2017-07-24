package edu.galileo.android.androidchat.contactlist;

/**
 * Created by Alejandro on 20/7/2017.
 */
public class ContactListInteractorImpl implements ContactListInteractor {
    ContactListRepository repository;

    public ContactListInteractorImpl() {
        repository = new ContactListRepositoryImpl();
    }

    @Override
    public void subscribe() {
        repository.subscribeContactListEvents();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribeContactListEvents();
    }

    @Override
    public void destroyListener() {
        repository.destroyListenerContactListEvents();
    }

    @Override
    public void removeContact(String email) {
        repository.removedContact(email);
    }
}
