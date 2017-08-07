package edu.galileo.android.androidchat.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import edu.galileo.android.androidchat.domain.FirebaseHelper;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.lib.EventBus;
import edu.galileo.android.androidchat.lib.GreenRobotEventBus;
import edu.galileo.android.androidchat.login.LoginRepository;
import edu.galileo.android.androidchat.login.events.LoginEvents;

/**
 * Created by Alejandro on 21/5/2017.
 * logica de negocio donde escribo la informacion del login
 */
public class LoginRepositoryImpl implements LoginRepository {
    private FirebaseHelper firebaseHelper;
    private FirebaseAuth myAuthentiUser;

    public LoginRepositoryImpl() {
        this.firebaseHelper = FirebaseHelper.getInstance();
        this.myAuthentiUser = FirebaseAuth.getInstance();
    }

    /**
     * metodo que me permite verificar contra firebase la autenticacion de usuario
     * @param email
     * @param password
     */
    @Override
    public void signIn(String email, String password) {
        if(!email.isEmpty()) {
            myAuthentiUser.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        getMyAuthenticUser();
                    } else {
                        postEvent(LoginEvents.onSignInError, task.getException().getMessage());
                    }
                    Log.e("LoginRepositoryImpl", "SignIn");
                }
            });
        }else{
            postEvent(LoginEvents.onSignInErrorSend); // esto se puede mejorar
        }
    }

    /**
     * metodo que me permite ingresar un nuevo usuario
     * @param email
     * @param password
     */
    @Override
    public void signUp(final String email, final String password,final String nameUser) {
        if (!email.isEmpty() && !password.isEmpty()) {
            final User userConect = new User();
            myAuthentiUser.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        userConect.setEmail(email);
                        userConect.setOnline(User.ONLINE);
                        userConect.setNameUsers(nameUser);
                        saveOfUser(userConect);
                        postEvent(LoginEvents.onSignUpSucces);
                        signIn(email,password);
                    } else {
                        postEvent(LoginEvents.onSignUpError, task.getException().getMessage());
                    }
                    Log.e("LoginRepositoryImpl", "SignUp");
                }
            });
        }else{
            postEvent(LoginEvents.onSignInErrorSend); // esto se puede mejorar
        }
    }

    /**
     * metodo para guardar usuario luego de crearlo y iniciar sesion
     * TODO: solo inicio sesion por aqui cuando estoy agregando el usuario
     * @param user
     */
    private void saveOfUser(final User user) {
        final DatabaseReference userReference = firebaseHelper.getMyUserReference();
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userReference.child(firebaseHelper.EMAIL_PATH).setValue(user.getEmail());
                userReference.child(firebaseHelper.STATE_PATH).setValue(user.isOnline());
                userReference.child(firebaseHelper.NAME_USER).setValue(user.getNameUsers());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                postEvent(LoginEvents.onSignInErrorSend);
            }
        });
    }

    /**
     * metodo para verificar si existe una sesion activa
     */
    @Override
    public void checkSession() {
       if (myAuthentiUser.getCurrentUser()!=null){
          getMyAuthenticUser();
        }else{
           postEvent(LoginEvents.onFailedToRecoverSession);
       }
    }

    /**
     * metodo para obtener datos Usuario autenticado
     */
    public void getMyAuthenticUser() {
        FirebaseUser myCurrentUser = myAuthentiUser.getCurrentUser();
        if (myCurrentUser != null){
            firebaseHelper.changeUserCOnectionStatus(User.ONLINE); // para actualizar estado y para guardar estatus
            postEvent(LoginEvents.onSignInSucces);
        }else{
            postEvent(LoginEvents.onSignInError);
        }
    }

    /**
     * metodo que recibe los parametros del evento para hacer EventBus
     * es decir devuelve y muestra el mensaje
     * @param event
     */
    private void postEvent(int event){
        postEvent(event, null);
    }
    private void postEvent(int event, String messageError){
        LoginEvents loginEvents = new LoginEvents();
        loginEvents.setEventType(event);
        if ( messageError != null){
            loginEvents.setErrorMessage(messageError);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvents);
    }
}
