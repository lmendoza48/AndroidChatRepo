package edu.galileo.android.androidchat.chat.ui.adapter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.contactlist.ui.ContactListActivity;
import edu.galileo.android.androidchat.entities.ChatMessage;

/**
 * Created by Alejandro on 27/7/2017.
 */
public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    private Context context;
    private List<ChatMessage> chatMessage;

    public ChatAdapter(Context context, List<ChatMessage> chatMessage) {
        this.context = context;
        this.chatMessage = chatMessage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat, parent, false);
        return new ViewHolder(view);
    }

    /**
     * metodo para colocar los datos en mi view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatmessage = chatMessage.get(position);
        String msg = chatmessage.getMsg();
        holder.txtMessage.setText(msg);
        holder.txtDate.setText(chatmessage.getDatteFormated());

        //notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int notificadionId = 001;
        SimpleDateFormat dateU = new SimpleDateFormat("MMMM dd,yyyy HH:mm");

        //*************************

        int gravity = Gravity.LEFT;
        // pregunto si no lo estoy enviando yo el msg cambio el color
        if (!chatmessage.isSentByMe()){
            gravity = Gravity.RIGHT;
        }
        // aqui estoy configurando
        // todo en mi layout para ver el msj dependiendo de quien lo envia
        holder.txtMessage.setBackgroundResource(!chatmessage.isSentByMe() ? R.drawable.bubble2 : R.drawable.bubble1);
        holder.txtMessage.setPadding(30,5,30,5);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.txtMessage.getLayoutParams();
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)holder.txtDate.getLayoutParams();
        params.gravity =  gravity;
        params2.gravity = gravity;
        holder.txtMessage.setLayoutParams(params);
        holder.txtDate.setLayoutParams(params2);
    }

    @Override
    public int getItemCount() {
        return chatMessage.size();
    }

    public void add(ChatMessage msg) {
        if (!chatMessage.contains(msg)){
            chatMessage.add(msg);
            notifyDataSetChanged();
        }
    }

    /**
     * metodo para recibir el color y cambiar dependiendo de
     * quien es el q escribe
     * @param color
     * @return
     */
    private int fetchColor(int color){
        TypedValue typeValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typeValue.data,
                    new int[]{color});
        int returnColor = a.getColor(0,0);
        a.recycle();
        return returnColor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtMessage)
        TextView txtMessage;
        @Bind(R.id.txtDate)
        TextView txtDate;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
