package edu.galileo.android.androidchat.addUser.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.contactlist.ui.ContactListActivity;
import edu.galileo.android.androidchat.login.LoginPresenter;
import edu.galileo.android.androidchat.login.LoginPresenterImpl;
import edu.galileo.android.androidchat.login.ui.LoginView;

public class AddUsersActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter presenter;

    @Bind(R.id.inputName)
    EditText inputName;
    @Bind(R.id.inputEmail)
    EditText inputEmail;
    @Bind(R.id.inputPassword)
    EditText inputPassword;
    @Bind(R.id.btnRegister)
    Button btnRegister;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.addUsers)
    RelativeLayout addUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);
        ButterKnife.bind(this);

        setTitle(R.string.create_user_title);

        presenter = new LoginPresenterImpl(this);
    }

    /**
     * metodo que se ejecuta despues de onCreate en una actividad
     */
    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    /**
     *metodo que se ejecuta antes de ondestroy
     */
    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }
    /**
     * metodo requerido para destruir la session
     */
    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


    @Override
    public void enableInput() {
        setupInput(true);
    }

    @Override
    public void disableInput() {
        setupInput(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void handleSignIn() {
        throw new UnsupportedOperationException("Operation is not valid in AddUserActivity");
    }

    @OnClick(R.id.btnRegister)
    public void handleSignUp() {
        presenter.registerNewUsers(inputEmail.getText().toString(),
                inputPassword.getText().toString(),inputName.getText().toString());
    }

    @Override
    public void navigationToMainScreen() {
        startActivity(new Intent(this, ContactListActivity.class)); // la nueva actividad se recorre con el Intent colocando esta actividad y la nueva
    }

    @Override
    public void loginError(String Error) {
        throw new UnsupportedOperationException("Operation is not valid in AddUserActivity");
    }

    @Override
    public void newUsersSuccess() {
        Snackbar.make(addUsers, R.string.login_notice_message_signup, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void newErrorMesaggeSing() {
        Snackbar.make(addUsers, R.string.login_notice_message_signupSend,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void newUsersErrors(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup),error);
        inputPassword.setError(msgError);
    }


    private void setupInput(boolean enable) {
        inputEmail.setEnabled(enable);
        inputName.setEnabled(enable);
        inputPassword.setEnabled(enable);
        btnRegister.setEnabled(enable);
    }
}
