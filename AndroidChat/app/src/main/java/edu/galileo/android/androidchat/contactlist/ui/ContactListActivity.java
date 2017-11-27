package edu.galileo.android.androidchat.contactlist.ui;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.addContacts.ui.AddContactsFragment;
import edu.galileo.android.androidchat.chat.ui.ChatActivity;
import edu.galileo.android.androidchat.contactlist.ContactListPresenter;
import edu.galileo.android.androidchat.contactlist.ContactListPresenterImpl;
import edu.galileo.android.androidchat.contactlist.ui.adapters.ContactListAdapter;
import edu.galileo.android.androidchat.contactlist.ui.adapters.OnItemClickListener;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.lib.GlideImageLoader;
import edu.galileo.android.androidchat.lib.ImageLoading;
import edu.galileo.android.androidchat.login.ui.LoginActivity;

public class ContactListActivity extends AppCompatActivity implements ContactListView, OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.receclyrViewContacts)
    RecyclerView receclyrViewContacts;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private ContactListAdapter adapter;
    private ContactListPresenter contactListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();
        contactListPresenter = new ContactListPresenterImpl(this);
        contactListPresenter.onCreate();
        setupToolBar();
        closeNotification();
    }

    /**
     * para cerrar la notificacion
     */

    private void closeNotification() {
        if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(001);
        }
    }

    /**
     * metodo para crear el menu los tres punticos
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contactlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * metodo para hacer algo cuando se presione el item
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_layout:
                getClosingSesion();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * metodo para cerrar sesion
     */
    private void getClosingSesion() {
        contactListPresenter.singOff();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                       | Intent.FLAG_ACTIVITY_NEW_TASK
                       | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setupRecyclerView() {
        receclyrViewContacts.setLayoutManager(new LinearLayoutManager(this));
        receclyrViewContacts.setAdapter(adapter);
    }

    /**
     * metodo para cargar el adapter y mostrar el avatar de mi img
     * * Datos de prueba TODO User user = new User();
                            user.setEmail("luis@gmail.com");
                            user.setOnline(true);
     Arrays.asList(new User[]{user})
     */
    private void setupAdapter() {
        ImageLoading loader = new GlideImageLoader(this.getApplicationContext());
        adapter = new ContactListAdapter(new ArrayList<User>(), loader, this);
    }

    /**
     * metodo para cargar el toolbar y mostrar el email
     * del usuario conectado
     */
    private void setupToolBar() {
        toolbar.setTitle(contactListPresenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        contactListPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        contactListPresenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        contactListPresenter.onPause();
        super.onPause();
    }

    /**
     * metodo para agregar los contactos con el manejo del click
     */
    @OnClick(R.id.fab)
    public void addContact(){
        new AddContactsFragment().show(getSupportFragmentManager(), getString(R.string.addcontact_message_title));
    }

    /**
     * metodo que me permite agregar un usario
     * @param user
     */
    @Override
    public void onContactAdd(User user) {
        adapter.add(user);

    }

    /**
     * metodo para cambiar a los contacto
     * @param user
     */
    @Override
    public void onContactChanged(User user) {
        adapter.update(user);
    }

    /**
     * metodo para eliminar a los contactos
     * @param user
     */
    @Override
    public void onContactRemoved(User user) {
        adapter.remove(user);
    }

    /**
     * este metedo es para hacer click sobre un elemento de mi lista se va a abrir el chat
     * @param user
     */
    @Override
    public void onItemClick(User user) {
       // Toast.makeText(this , user.getEmail(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(ChatActivity.EMAIL_KEY,user.getEmail());
        intent.putExtra(ChatActivity.ONLINE_KEY,user.isOnline());
        startActivity(intent);
    }

    /**
     * metodo el cual me permite eliminar un contacto con un click largo
     * @param user
     */
    @Override
    public void onItemLongClick(User user) {
        contactListPresenter.removeContact(user.getEmail());
    }
}
