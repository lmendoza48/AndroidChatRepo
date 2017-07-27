package edu.galileo.android.androidchat.chat;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import edu.galileo.android.androidchat.chat.events.ChatEvent;
import edu.galileo.android.androidchat.domain.FirebaseHelper;
import edu.galileo.android.androidchat.entities.ChatMessage;
import edu.galileo.android.androidchat.lib.EventBus;
import edu.galileo.android.androidchat.lib.GreenRobotEventBus;

/**
 * Created by Alejandro on 27/7/2017.
 */
public class ChatRepositoryImpl implements ChatRepository {
    private String receiver;
    private EventBus eventBus;
    private FirebaseHelper helper;
    private ChildEventListener chatEventListener;

    public ChatRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void sendMessage(String msg) {
        ChatMessage message = new ChatMessage();
        message.setSender(helper.getAuthUserEmail());//estoy diciendo quien envia el msj
        message.setMsg(msg);

        DatabaseReference chatsReference = helper.getChatsReference(receiver);
        chatsReference.push().setValue(message);
    }

    @Override
    public void setRecipient(String recipient) {
        this.receiver = recipient;
    }

    /**
     * metodo para agregar la logico del chat
     * para pasar los comentarios
     */
    @Override
    public void subscribe() {
        if (chatEventListener == null) {
            chatEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class); // aqui estoy rellenando mi CLase model con los datos q le paso de firebase
                    String msgSenger = chatMessage.getSender();

                    chatMessage.setSentByMe(msgSenger.equals(helper.getAuthUserEmail()));

                    ChatEvent chatEvent = new ChatEvent();
                    chatEvent.setMessage(chatMessage);
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }
        helper.getChatsReference(receiver).addChildEventListener(chatEventListener);
    }

    @Override
    public void unsubscribe() {
        if (chatEventListener != null){
            helper.getChatsReference(receiver).removeEventListener(chatEventListener);
        }
    }

    @Override
    public void destroyListener() {
        chatEventListener = null;
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        helper.changeUserCOnectionStatus(online);
    }
}
