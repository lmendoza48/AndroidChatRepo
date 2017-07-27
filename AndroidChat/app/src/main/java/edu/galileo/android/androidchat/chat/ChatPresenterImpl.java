package edu.galileo.android.androidchat.chat;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.androidchat.chat.ui.ChatView;
import edu.galileo.android.androidchat.chat.events.ChatEvent;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.lib.EventBus;
import edu.galileo.android.androidchat.lib.GreenRobotEventBus;

/**
 * Created by Alejandro on 27/7/2017.
 */
public class ChatPresenterImpl implements ChatPresenter {
    private EventBus eventBus;
    private ChatView view;
    private ChatInteractor interactor;
    private ChatSessionInteractor sessionInteractor;

    public ChatPresenterImpl(ChatView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new ChatInteractorImpl();
        this.sessionInteractor = new ChatSessionInteractorImpl();
    }

    /**
     * metodo que me permite desuscribirme
     */
    @Override
    public void onPause() {
        interactor.unsubscribe();
        sessionInteractor.changeConnectionStatus(User.OFFNLINE);
    }

    @Override
    public void onResume() {
        interactor.subscribe();
        sessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        interactor.destroyListener();
        view = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        interactor.setChatRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        interactor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if (view != null){
            view.onMessageReceived(event.getMessage());
        }
    }
}
