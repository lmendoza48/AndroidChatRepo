package edu.galileo.android.androidchat.contactlist;

import org.greenrobot.eventbus.Subscribe;

import java.util.ListIterator;

import edu.galileo.android.androidchat.contactlist.ContactListPresenter;
import edu.galileo.android.androidchat.contactlist.event.ContactListEvent;
import edu.galileo.android.androidchat.contactlist.ui.ContactListView;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.lib.EventBus;
import edu.galileo.android.androidchat.lib.GreenRobotEventBus;

/**
 * Created by Alejandro on 17/7/2017.
 */
public class ContactListPresenterImpl implements ContactListPresenter{
    private EventBus eventBus;
    private ContactListView view;
    private ContactListInteractor interactor;
    private ContactListSessionInteractor sessionInteractor;

    public ContactListPresenterImpl(ContactListView view) {
        this.view = view;
        eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new ContactListInteractorImpl();
        this.sessionInteractor = new ContactListSessionInteractorImpl();
    }

    /**
     * Para colocar la sesion en Pausa
     */
    @Override
    public void onPause() {
        sessionInteractor.changedConnectionStatus(User.OFFNLINE);
        interactor.unsubscribe();
    }

    /**
     * metodo para resumir la conexion es decir colocar online
     */
    @Override
    public void onResume() {
        sessionInteractor.changedConnectionStatus(User.ONLINE);
        interactor.subscribe();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        interactor.destroyListener();// destruimos la sesion para no tener ningun memory leak
        view = null;
    }

    /**
     * metodo para cerrar session
     */
    @Override
    public void singOff() {
        sessionInteractor.changedConnectionStatus(User.OFFNLINE);
        interactor.unsubscribe();
        interactor.destroyListener();
        sessionInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return sessionInteractor.getCurrentUserEmail();
    }



    @Override
    public void removeContact(String email) {
        interactor.removeContact(email);
    }

    /**
     * metodo para el manejo de los diferentes escenarios
     * @param event tipo de evento
     */
    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent event) {
        User user = event.getUser();
        switch (event.getEventType()){
            case ContactListEvent.onContactAdded:
                onContactAdded(user);
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;
            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;

        }
    }

    /**
     * Metodos para el manejo de los diferentes eventos
     * @param user
     */
    private void onContactAdded(User user){
        if (view != null){
            view.onContactAdd(user);
        }
    }
    private void onContactChanged(User user){
        if (view != null){
            view.onContactChanged(user);
        }
    }
    private void onContactRemoved(User user){
        if (view != null){
            view.onContactRemoved(user);
        }
    }
}
