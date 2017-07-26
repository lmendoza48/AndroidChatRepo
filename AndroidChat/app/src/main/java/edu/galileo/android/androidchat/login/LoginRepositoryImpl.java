package edu.galileo.android.androidchat.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

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
      //  this.dataReference = firebaseHelper.getDataReference();
       // this.myUserReference = firebaseHelper.getMyUserReference();
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
    public void signUp(final String email, final String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            myAuthentiUser.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        postEvent(LoginEvents.onSignUpSucces);
                        signIn(email, password);
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

    @Override
    public void checkSession() {
        // datareference.getAuth es igual a myAuthentiUser.getCurrentUser()
       if (myAuthentiUser.getCurrentUser()!=null){
          getMyAuthenticUser();
        }else{
           postEvent(LoginEvents.onFailedToRecoverSession);
       }
        //Log.e("LoginRepositoryImpl", "checksesion");
       // postEvent(LoginEvents.onFailedToRecoverSession);
    }

    /**
     * metodo para obtener datos de sesion activa
     */
    public void getMyAuthenticUser() {
        User currentUser;
        FirebaseUser myCurrentUser = myAuthentiUser.getCurrentUser();
        if (myCurrentUser != null){
            String email = myCurrentUser.getEmail();
            if (email != null){
                currentUser = new User();
                currentUser.setEmail(email);
            }
        }
        firebaseHelper.changeUserCOnectionStatus(User.ONLINE);
        postEvent(LoginEvents.onSignInSucces);
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
