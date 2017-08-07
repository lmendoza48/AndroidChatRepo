package edu.galileo.android.androidchat.addContacts;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import edu.galileo.android.androidchat.addContacts.events.AddContactEvent;
import edu.galileo.android.androidchat.domain.FirebaseHelper;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.lib.EventBus;
import edu.galileo.android.androidchat.lib.GreenRobotEventBus;

/**
 * Created by Alejandro on 24/7/2017.
 */
public class AddContactRepositoryImpl implements AddContactRepository {
    private EventBus eventBus;
    private FirebaseHelper helper;

    public AddContactRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.helper = FirebaseHelper.getInstance();
    }

    /**
     * metodo para recibir el email a agregar
     * @param email
     */
    @Override
    public void addContact(final String email) {
        if (!email.isEmpty()) {
            final String key = email.replace(".", "_");
            DatabaseReference userReference = helper.getUserReference(email);
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                /**
                 * aqui estoy viendo los datos del nodo al que hago referencia con userReference
                 * @param dataSnapshot
                 */
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class); // para que firebase llene todos lo campos necesarios
                    if (user != null) {
                        //agrego contacto en mi lista y lo coloco como desconectado
                        DatabaseReference myUserContactReference = helper.getMyContactReference();
                        myUserContactReference.child(key).setValue(user.isOnline());

                        /**Aqui estoy agregando al usuario en la lista de contacto de la persona y aparesco como conectado*/
                        String currenUserKey = helper.getAuthUserEmail();
                        currenUserKey = currenUserKey.replace(".", "_");
                        DatabaseReference reverseContactReference = helper.getContactReference(email);
                        reverseContactReference.child(currenUserKey).setValue(User.ONLINE);

                        potEvent(AddContactEvent.addContactSucces);


                    } else {
                        potEvent(AddContactEvent.onFailedError);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    potEvent(AddContactEvent.errorEmptyAddContact);
                }
            });
        }else{
            potEvent(AddContactEvent.errorEmptyAddContact);
        }
    }

    /**
     * metodos para saber si se guardo con exito o existe
     * algun error
     */
    private void potEvent(int event) {
        post(event, null);
    }

    private void post(int eventType, String error) {
        AddContactEvent event = new AddContactEvent();
            event.setEventType(eventType);
            if (error != null){
                event.setErrorMessage(error);
            }
            eventBus.post(event);
    }
}
