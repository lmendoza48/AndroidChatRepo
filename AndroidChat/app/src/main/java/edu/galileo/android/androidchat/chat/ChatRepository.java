package edu.galileo.android.androidchat.chat;

/**
 * Created by Alejandro on 27/7/2017.
 */
public interface ChatRepository {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void subscribe();
    void unsubscribe();
    void destroyListener();
    void changeConnectionStatus(boolean online);

}
