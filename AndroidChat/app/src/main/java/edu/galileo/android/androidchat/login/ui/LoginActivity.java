package edu.galileo.android.androidchat.login.ui;

import android.content.Intent;
import android.media.UnsupportedSchemeException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.addUser.ui.AddUsersActivity;
import edu.galileo.android.androidchat.contactlist.ui.ContactListActivity;
import edu.galileo.android.androidchat.login.LoginPresenter;
import edu.galileo.android.androidchat.login.LoginPresenterImpl;

/**
 * clase principal my activity de login
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.inputEmail)
    EditText inputEmail;
    @Bind(R.id.editTxtPassword)
    EditText inputPassword;
    @Bind(R.id.btnSingin)
    Button btnSignIn;
    @Bind(R.id.btnSingUp)
    Button btnSignUp;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout container;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loginPresenter.onResume();
        loginPresenter.checkForAuthenticatedUsers(); // para validar a los usuario y verificar q no exista ningun session
    }

    @Override
    protected void onPause() {
        loginPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    /*metodo para probar funcionamiento de los botons*/
    /*@OnClick(R.id.btnSingin)
    public void handdleSingIn(){
        Log.e("Look this", inputEmail.getText().toString());
    }*/
   /*-------------------------------------------------------------------*/

    @Override
    public void enableInput() {
         setInputs(true);
    }

    @Override
    public void disableInput() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    /*
    * Para registrar nuevo Usuario
    * */
    @OnClick(R.id.btnSingUp)
    @Override
    public void handleSignUp() {
        /*loginPresenter.registerNewUsers(inputEmail.getText().toString(),
                inputPassword.getText().toString());*///old metod para agregar usuario
        startActivity(new Intent(this, AddUsersActivity.class));
    }
    /*
    * Para ingresar usuario Registrado
    * aqui recorro los metodos en cascada y devuelvo mensaje con EventBuss
    * */
    @OnClick(R.id.btnSingin)
    @Override
    public void handleSignIn() {
        loginPresenter.validateLogin(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    /**
     * metodo para pasar a la otra actividad para ver la lista de contacto
     */
    @Override
    public void navigationToMainScreen() {
        startActivity(new Intent(this, ContactListActivity.class)); // la nueva actividad se recorre con el Intent colocando esta actividad y la nueva
    }

    /**
     * Metodo: Error al tratar de iniciar sesion
     * @param error
     */
    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin),error);
        inputPassword.setError(msgError);
    }

    /**
     * Metodo para mostrar mensaje cuando el usuario se creo con exito
     */
    @Override
    public void newUsersSuccess() {
        throw new UnsupportedOperationException("Operation is not valid in LoginActivity");
    }

    /**
     * metodo para comprobar que los campos no esten vacios
     */
    @Override
    public void newErrorMesaggeSing() {
        Snackbar.make(container,R.string.login_notice_message_signupSend,Snackbar.LENGTH_SHORT).show();
    }

    /**
     * metodo para mostros error en el campo inputs cuando hay un error
     * al crear un usuario
     * @param error
     */
    @Override
    public void newUsersErrors(String error) {
        throw new UnsupportedOperationException("Operation is not valid in LoginActivity");
    }

    /**
     * habilitar o deshabilitar campos
     * @param enable
     */
    private void setInputs(boolean enable){
        inputEmail.setEnabled(enable);
        inputPassword.setEnabled(enable);
        btnSignIn.setEnabled(enable);
        btnSignUp.setEnabled(enable);
    }
}
