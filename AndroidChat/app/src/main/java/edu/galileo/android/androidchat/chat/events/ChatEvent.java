package edu.galileo.android.androidchat.chat.events;

import edu.galileo.android.androidchat.entities.ChatMessage;

/**
 * Created by Alejandro on 27/7/2017.
 */
public class ChatEvent {
    ChatMessage message;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }
}
