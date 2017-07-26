package edu.galileo.android.androidchat.addContacts;

/**
 * Created by Alejandro on 24/7/2017.
 */
public class AddContactInteractorImpl implements AddContactInteractor {
    private AddContactRepository repository;

    public AddContactInteractorImpl() {
        repository = new AddContactRepositoryImpl();
    }

    @Override
    public void execute(String email) {
        repository.addContact(email);
    }
}
