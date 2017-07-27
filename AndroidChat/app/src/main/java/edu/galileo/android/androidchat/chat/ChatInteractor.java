package edu.galileo.android.androidchat.chat;

/**
 * Created by Alejandro on 27/7/2017.
 */
public interface ChatInteractor {
    void sendMessage(String msg);
    void setChatRecipient(String recipient);

    void subscribe();
    void unsubscribe();
    void destroyListener();
}
