package edu.galileo.android.androidchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alejandro on 27/7/2017.
 * esta clase represente mi model y los atributos son los nodos en mi BD = Firebase
 * con esa anotacion le digo a firebase que no me tome ese valor
 */
@JsonIgnoreProperties({"sentByMe"})
public class ChatMessage {
    private String msg;
    private String sender;
    private boolean sentByMe;
    private Date dateMsg;

    public ChatMessage() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }

    public Date getDateMsg() {
        return dateMsg;
    }

    public void setDateMsg(Date dateMsg) {
        this.dateMsg = dateMsg;
    }

    /**
     * metodo para la comparacion de los elementos el Equals
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof ChatMessage){
            ChatMessage msg = (ChatMessage) obj;
            equal = this.sender.equals(msg.getSender()) && this.msg.equals(msg.getMsg()) && this.sentByMe == msg.sentByMe;
        }
        return equal;
    }

    /**
     * metodo para el formata de la fecha
     * @return
     */
    public String getDatteFormated(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd,yyyy HH:mm");
        return simpleDateFormat.format(dateMsg);
    }
}
