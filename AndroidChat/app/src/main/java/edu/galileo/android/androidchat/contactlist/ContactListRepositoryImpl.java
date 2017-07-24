package edu.galileo.android.androidchat.contactlist;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


import edu.galileo.android.androidchat.contactlist.event.ContactListEvent;
import edu.galileo.android.androidchat.domain.FirebaseHelper;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.lib.EventBus;
import edu.galileo.android.androidchat.lib.GreenRobotEventBus;

/**
 * Created by Alejandro on 20/7/2017.
 * Clase para el manejo de mi base de datos
 */
public class ContactListRepositoryImpl implements ContactListRepository {
    private EventBus eventBus;
    private FirebaseHelper helper;
    private ChildEventListener contactEventListener;

    public ContactListRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.helper = FirebaseHelper.getInstance();
    }

    /**
     * para cerrar sesion
     */
    @Override
    public void singOff() {
        helper.signOff();
    }

    /**
     * correo del usuario autenticado
     * @return
     */
    @Override
    public String getCurrentUserEmail() {
        return helper.getAuthUserEmail();
    }

    /**
     * metodo que me permite borrar a un contacto de mi lista
     * @param email del contacto que quiero borrar
     */
    @Override
    public void removedContact(String email) {
        String currentUserEmail = helper.getAuthUserEmail();
        helper.getOneContactReference(currentUserEmail, email).removeValue(); // lo borro de mis contactos
        helper.getOneContactReference(email,currentUserEmail).removeValue(); // lo borro tambn mi correo de su lista de contactos
    }

    @Override
    public void destroyListenerContactListEvents() {
        contactEventListener = null;
    }

    @Override
    public void subscribeContactListEvents() {
        if (contactEventListener == null){
            contactEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.onContactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot, ContactListEvent.onContactRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }
        helper.getMyContactReference().addChildEventListener(contactEventListener);
    }

    private void handleContact(com.google.firebase.database.DataSnapshot dataSnapshot, int type) {
        String email = dataSnapshot.getKey();
        email = email.replace("_","."); // esto se hace xq firebase no guardo los punto sino como piso
        boolean online = ((Boolean) dataSnapshot.getValue()).booleanValue();
        User user = new User();
        user.setEmail(email);
        user.setOnline(online);
        post(type, user);
    }

    /**
     * metodo para recibir el evento al agregar un usuario
     * @param type
     * @param user
     */
    private void post(int type, User user) {
        ContactListEvent event = new ContactListEvent();
        event.setEventType(type);
        event.setUser(user);
        eventBus.post(event);
    }

    @Override
    public void unsubscribeContactListEvents() {
        if (contactEventListener != null){
            helper.getMyContactReference().removeEventListener(contactEventListener);
        }
    }

    @Override
    public void changedConnectionStatus(boolean online) {

    }
}
