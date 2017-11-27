package edu.galileo.android.androidchat.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.chat.ui.adapter.ChatAdapter;
import edu.galileo.android.androidchat.chat.ChatPresenter;
import edu.galileo.android.androidchat.chat.ChatPresenterImpl;
import edu.galileo.android.androidchat.domain.AvatarHelper;
import edu.galileo.android.androidchat.entities.ChatMessage;
import edu.galileo.android.androidchat.lib.GlideImageLoader;
import edu.galileo.android.androidchat.lib.ImageLoading;

public class ChatActivity extends AppCompatActivity implements ChatView {

    @Bind(R.id.imageView)
    CircleImageView imageView;
    @Bind(R.id.txtUser)
    TextView txtUser;
    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.messageRecyclerView)
    RecyclerView messageRecyclerView;
    @Bind(R.id.editTxtMessage)
    EditText editTxtMessage;


    private ChatPresenter presenter;
    private ChatAdapter adapter;
    public final static String EMAIL_KEY="email";
    public final static String ONLINE_KEY="online";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();

        presenter = new ChatPresenterImpl(this);
        presenter.onCreate();

        setupToolbar(getIntent());
    }

    /**
     * metodo para implementar los msg q se van a mostrar en mi chat
     * ejmp de prueba:
     * ChatMessage msg1 = new ChatMessage();
     ChatMessage msg2 = new ChatMessage();
     msg1.setSentByMe(true);
     msg2.setSentByMe(false);
     Arrays.asList(new ChatMessage[]{msg1,msg2})
     */
    private void setupAdapter(){
        adapter = new ChatAdapter(this, new ArrayList<ChatMessage>());
    }
    /**
     * metodos de configuracion
     */
    private void setupRecyclerView() {
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(adapter);
    }
     /**
     * metodo para configurar lo q se va a mostrar en mi toolbar
     * es decir en la barra
     * @param item
     */
    private void setupToolbar(Intent item) {
        String recipient = item.getStringExtra(EMAIL_KEY);
        presenter.setChatRecipient(recipient);

        boolean online = item.getBooleanExtra(ONLINE_KEY, false);
        String status = online ? "online" : "offline";
        int color = online ? Color.GREEN : Color.RED;

        txtUser.setText(recipient);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);

        ImageLoading imageloader = new GlideImageLoader(getApplicationContext()); // lo q le mando pude variar
        imageloader.load(imageView, AvatarHelper.getAvatarUrl(recipient));

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    /**
     * metodo para recibir un msj
     * @param msg
     */
    @Override
    public void onMessageReceived(ChatMessage msg) {
        adapter.add(msg);
        messageRecyclerView.scrollToPosition(adapter.getItemCount()+1);
    }

    @OnClick(R.id.btnSendMessage)
    public void sendMessage(){
        presenter.sendMessage(editTxtMessage.getText().toString());
        editTxtMessage.setText("");
    }
}
