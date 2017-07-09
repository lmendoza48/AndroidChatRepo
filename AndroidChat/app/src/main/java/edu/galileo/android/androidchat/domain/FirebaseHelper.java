package edu.galileo.android.androidchat.domain;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by Alejandro on 8/5/2017.
 * clase para centralizar toda la logica de negocio
 */
public class FirebaseHelper {
    private Firebase dataReference;
    private FirebaseAuth  myAuthentiUser;
    private final static String SEPARETER = "___";
    private final static String USER_PATH ="users";
    private final static String CONTACT_PATH ="contacts";
    private final static String CHAT_PATH ="chats";
    private final static String FIREBASE_URL= "https://chatandroid-b3f8b.firebaseio.com"; // "https://androidchat-4206f.firebaseio.com";

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
     */
    public FirebaseHelper() {
        this.dataReference = new Firebase(FIREBASE_URL);
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
        FirebaseUser usersAutentic =  myAuthentiUser.getCurrentUser();
        String email = null;
        if(usersAutentic != null){
            //Map<String, Object> providerData = usersAutentic.getProviderData();
            email = usersAutentic.getEmail();
        }
        return email;
    }
/*-----------------------------------------------------------------------*/
/*metodo para obtner el email del usuario el .child es el que me permite recojer los datos del json es decir de una de las ramas
* del json que devuelve la base de datos firebase */
    public Firebase getUserReference(String email){
        Firebase userReference = null;
        if(email != null){
            String emailKey = email.replace(".","_");
            userReference = dataReference.getRoot().child(USER_PATH).child(emailKey);
        }
        return userReference;
    }

    /**
     * recargar del metodo getUserReference
     * @return
     */
    public Firebase getMyUserReference(){
        return getUserReference(getAuthUserEmail());
    }

    /**
     * Metodo que me devuelve los contactos
     * @param email
     * @return
     */
    public Firebase getContactReference(String email){
        return getUserReference(email).child(CONTACT_PATH);
    }

    public Firebase getMyContactReference(){
        return getContactReference(getAuthUserEmail());
    }

    /*metodo que me devuelve la referencia de un solo contacto
    *MainEmail = correo del usuario
    *ChildEmail = correo del contacto*/
    public Firebase getOneContactReference(String mainEmail, String childEmail){
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
    public void signOff(){
        notifyContactsOfConnectionChange(User.OFFNLINE,true);
    }
    private void notifyContactsOfConnectionChange(final boolean online, final boolean signOff) {
    final String myEmail = getAuthUserEmail();
        getMyContactReference().addListenerForSingleValueEvent(new ValueEventListener() {
            /*aqui lo q estoy haciendo es avisando a mis contacto que estoy conectado o desconectado*/
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String email = child.getKey();
                    Firebase reference = getOneContactReference(email,myEmail);
                    reference.setValue(online);
                }
                /*solo si quiero cerrar sesion luego de abrirla*/
                if (signOff){
                 dataReference.unauth();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}
