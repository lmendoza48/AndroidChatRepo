package edu.galileo.android.androidchat.chat.ui;

import edu.galileo.android.androidchat.entities.ChatMessage;

/**
 * Created by Alejandro on 27/7/2017.
 */
public interface ChatView {
    void onMessageReceived(ChatMessage msg);
}
