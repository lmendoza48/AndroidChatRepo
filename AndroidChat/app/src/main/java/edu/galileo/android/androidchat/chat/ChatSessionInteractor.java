package edu.galileo.android.androidchat.chat;

/**
 * Created by Alejandro on 27/7/2017.
 * interfaz para reibir la session y ver el cambio de estado
 */
public interface ChatSessionInteractor {
    void changeConnectionStatus(boolean online);
}
