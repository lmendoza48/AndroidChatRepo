package edu.galileo.android.androidchat.lib;

/**
 * Created by Alejandro on 27/5/2017.
 */
public class GreenRobotEventBus implements EventBus {
    de.greenrobot.event.EventBus eventBus;

    /**
     * Clase privada para hacer singleton el cual me devuelve un atributo que llama a la clase GreenRobotEventBus
     */
    private static class SingletonHolder{
        private static final GreenRobotEventBus INSTANCE = new GreenRobotEventBus();
    }

    public static GreenRobotEventBus getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public GreenRobotEventBus() {
        this.eventBus = de.greenrobot.event.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
