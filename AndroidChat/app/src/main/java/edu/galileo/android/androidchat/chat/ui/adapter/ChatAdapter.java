package edu.galileo.android.androidchat.chat.ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.androidchat.R;
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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatmessage = chatMessage.get(position);
        String msg = chatmessage.getMsg();
        holder.txtMessage.setText(msg);

        int color =fetchColor(R.attr.colorPrimary);
        int gravity = Gravity.LEFT;
        // pregunto si no lo estoy enviando yo el msg cambio el color
        if (!chatmessage.isSentByMe()){
            color = fetchColor(R.attr.colorAccent);
            gravity = Gravity.RIGHT;
        }
        // aqui estoy configurando
        // todo en mi layout para ver el msj dependiendo de quien lo envia
        holder.txtMessage.setBackgroundColor(color);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.txtMessage.getLayoutParams();
        params.gravity = gravity;
        holder.txtMessage.setLayoutParams(params);
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
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
