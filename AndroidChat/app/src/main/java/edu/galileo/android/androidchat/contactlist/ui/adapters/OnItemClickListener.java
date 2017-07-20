package edu.galileo.android.androidchat.contactlist.ui.adapters;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by Alejandro on 17/7/2017.
 * interfaz para el manejo del click
 * onItemClick metodo para ver los contactos
 * onItemLongClick metodo para eliminar un contacto
 */
public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}
