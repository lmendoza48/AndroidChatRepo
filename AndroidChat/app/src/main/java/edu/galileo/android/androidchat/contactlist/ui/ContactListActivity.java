package edu.galileo.android.androidchat.contactlist.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.contactlist.ContactListPresenter;
import edu.galileo.android.androidchat.contactlist.ui.adapters.ContactListAdapter;
import edu.galileo.android.androidchat.contactlist.ui.adapters.OnItemClickListener;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.lib.GlideImageLoader;
import edu.galileo.android.androidchat.lib.ImageLoading;

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
       // contactListPresenter.onCreate();
        setupToolBar();

    }

    private void setupRecyclerView() {
        receclyrViewContacts.setLayoutManager(new LinearLayoutManager(this));
        receclyrViewContacts.setAdapter(adapter);
    }

    /**
     * metodo para cargar el adapter y mostrar el avatar de mi img
     */
    private void setupAdapter() {
        ImageLoading loader = new GlideImageLoader(this.getApplicationContext());
        User user = new User();
        user.setEmail("luis@gmail.com");
        user.setOnline(true);
        adapter = new ContactListAdapter(Arrays.asList(new User[]{user}), loader, this);
    }

    /**
     * metodo para cargar el toolbar
     */
    private void setupToolBar() {
      //  toolbar.setTitle(contactListPresenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);
    }
/**
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
*/
    /**
     * metodo para agregar los contactos con el manejo del click
     */
    @OnClick(R.id.fab)
    public void addContact(){

    }

    /**
     * metodo que me permite agregar un usario
     * @param user
     */
    @Override
    public void onContactAdd(User user) {

    }

    /**
     * metodo para cambiar a los contacto
     * @param user
     */
    @Override
    public void onContactChanged(User user) {

    }

    /**
     * metodo para eliminar a los contactos
     * @param user
     */
    @Override
    public void onContactRemoved(User user) {

    }

    @Override
    public void onItemClick(User user) {

    }

    @Override
    public void onItemLongClick(User user) {

    }
}
