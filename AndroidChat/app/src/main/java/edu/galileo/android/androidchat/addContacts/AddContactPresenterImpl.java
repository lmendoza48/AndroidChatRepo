package edu.galileo.android.androidchat.addContacts;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.androidchat.addContacts.AddContactPresenter;
import edu.galileo.android.androidchat.addContacts.events.AddContactEvent;
import edu.galileo.android.androidchat.addContacts.ui.AddContactView;
import edu.galileo.android.androidchat.lib.EventBus;
import edu.galileo.android.androidchat.lib.GreenRobotEventBus;

/**
 * Created by Alejandro on 24/7/2017.
 */
public class AddContactPresenterImpl implements AddContactPresenter {
    private AddContactView view;
    private EventBus eventBus;
    private AddContactInteractor interactor;

    public AddContactPresenterImpl(AddContactView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new AddContactInteractorImpl();
    }

    /**
     * metodo para registrar a un usuario
     */
    @Override
    public void onShow() {
        eventBus.register(this);
    }

    /**
     * Metodo para destruir la session
     */
    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);

    }

    /**
     * metodo que me recibe el email para agregarlo
     * @param email
     */
    @Override
    public void addContact(String email) {
        if (view != null){
            view.hideInput();
            view.showProgress();
        }
        interactor.execute(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {
        if (view != null){
            view.hideProgress();
            view.showInput();
            if (event.isErrorMesage()){
                view.contactNotAdded();
            }else{
                view.contactAdded();
            }
        }

    }
}
