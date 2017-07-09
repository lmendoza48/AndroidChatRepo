package edu.galileo.android.androidchat.lib;

/**
 * Created by Alejandro on 27/5/2017.
 * EventBus libreria la cual es un patron de software que me permite
 * comunicar un evento hacia un bus de datos
 * me permitira hacer la comunicacion inversa
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
