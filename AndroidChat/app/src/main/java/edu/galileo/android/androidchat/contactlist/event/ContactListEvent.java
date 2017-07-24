package edu.galileo.android.androidchat.contactlist.event;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by Alejandro on 17/7/2017.
 * clase para el manejo de los eventos, eliminar , agregar y cambiar estatus
 */
public class ContactListEvent {
    public final static int onContactAdded = 0;
    public final static int onContactChanged = 1;
    public final static int onContactRemoved = 2;

    private User user;
    private int eventType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
