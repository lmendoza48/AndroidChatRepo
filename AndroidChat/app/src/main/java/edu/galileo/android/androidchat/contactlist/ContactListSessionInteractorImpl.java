package edu.galileo.android.androidchat.contactlist;

/**
 * Created by Alejandro on 20/7/2017.
 */
public class ContactListSessionInteractorImpl implements ContactListSessionInteractor {
    ContactListRepository repository;

    public ContactListSessionInteractorImpl() {
        repository = new ContactListRepositoryImpl();
    }

    @Override
    public void signOff() {
        repository.singOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return repository.getCurrentUserEmail();
    }

    @Override
    public void changedConnectionStatus(boolean online) {
        repository.changedConnectionStatus(online);
    }
}
