package edu.galileo.android.androidchat.domain;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by Alejandro on 8/5/2017.
 * clase para centralizar toda la logica de negocio
 */
public class FirebaseHelper {
    private Firebase dataReference;
    private DatabaseReference mDatabase;
    private FirebaseAuth  myAuthentiUser;
    private final static String SEPARETER = "___";
    public  final static String USER_PATH ="users";
    private final static String CONTACT_PATH ="contacts";
    private final static String CHAT_PATH ="chats";
    public static String EMAIL_PATH="email";
    public static String STATE_PATH="online";
    private final static String FIREBASE_URL="https://androidchat-4206f.firebaseio.com"; //"https://chatandroid-b3f8b.firebaseio.com"; // "https://androidchat-4206f.firebaseio.com";

    /**
     * clase para hacer singleton de la clase principal
     */
    private static class SingletonHolder{
        private final static FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    /**
     * metodo para instaciar la clase de FirebaseHelper con singleton
     * @return
     */
    public static FirebaseHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }
    /*------------------------------------------------------------------*/

    /**
     * Metodo para conexion a firebase TODO: URL
     * contrusctor
     */
    public FirebaseHelper() {
        this.dataReference = new Firebase(FIREBASE_URL);
        myAuthentiUser = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Metodo para devolver atributo firebase
     * @return
     */
    public Firebase getDataReference() {
        return dataReference;
    }

    /**
     * metodo que me devuelve el correo del usuario autenticado
     * @return Email
     */
    public String getAuthUserEmail(){
        FirebaseUser user = myAuthentiUser.getCurrentUser();
        String email = null;
        if (user != null){
            email = user.getEmail().toString();
        }
        return email;
    }
/*-----------------------------------------------------------------------*/
/*metodo para obtner el email del usuario el .child es el que me permite recojer los datos del json es decir de una de las ramas
* del json que devuelve la base de datos firebase */
    public DatabaseReference getUserReference(String email){
        DatabaseReference userReference = null;
        if(email != null){
            String emailKey = email.replace(".","_");
            //userReference = dataReference.getRoot().child(USER_PATH).child(emailKey);
            userReference = mDatabase.child(USER_PATH).child(emailKey);
        }
        return userReference;
    }

    /**
     * recargar del metodo getUserReference
     * @return
     */
    public DatabaseReference getMyUserReference(){
        return getUserReference(getAuthUserEmail());
    }

    /**
     * Metodo que me devuelve los contactos
     * @param email
     * @return
     */
    public DatabaseReference getContactReference(String email){
        return getUserReference(email).child(CONTACT_PATH);
    }

    public DatabaseReference getMyContactReference(){
        return getContactReference(getAuthUserEmail());
    }

    /*metodo que me devuelve la referencia de un solo contacto
    *MainEmail = correo del usuario
    *ChildEmail = correo del contacto*/
    public DatabaseReference getOneContactReference(String mainEmail, String childEmail){
     String keyChildEmail = childEmail.replace(".","_");
     return getUserReference(mainEmail).child(CONTACT_PATH).child(keyChildEmail);
    }

    /*metodo para obtner la referencia de los chat*/
    public Firebase getChatsReference(String receiver){
        String keySender = getAuthUserEmail().replace(".", "_");
        String keyReceiver = receiver.replace(".","_");

        String keyChat = keySender + SEPARETER + keyReceiver;
        if(keySender.compareTo(keyReceiver)> 0) {
            keyChat = keyReceiver + SEPARETER + keySender;
        }
        return dataReference.getRoot().child(CHAT_PATH).child(keyChat);
    }
    /* metodo para ver el estatus de conexion*/
    public void changeUserCOnectionStatus(boolean online){
        if (getMyUserReference() != null){
           Map<String,Object> updates = new HashMap<String, Object>();
            updates.put("online",online);
            getMyUserReference().updateChildren(updates);
            notifyContactsOfConnectionChange(online);
        }
    }

    private void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online,false);
    }

    /**
     * metodo para cerrar session
     */
    public void signOff(){
        notifyContactsOfConnectionChange(User.OFFNLINE,true);
    }
    private void notifyContactsOfConnectionChange(final boolean online, final boolean signOff) {
    final String myEmail = getAuthUserEmail();
        getMyContactReference().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            /**
             * metodo para actualizar la informacion del nodo el cual se esta llamando
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                /*aqui lo q estoy haciendo es avisando a mis contacto que estoy conectado o desconectado*/
                for (com.google.firebase.database.DataSnapshot child : dataSnapshot.getChildren()){
                    String email = child.getKey();
                    DatabaseReference reference = getOneContactReference(email,myEmail);
                    reference.setValue(online); // aqui cambio el estatu de conexion mio en los contacto de la otra persona
                    // para ver si quiero cerrar session luego de iniciar
                    if (signOff){
                        myAuthentiUser.signOut();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", "onCancelled:" + databaseError.getDetails());
            }
        });
    }
}
