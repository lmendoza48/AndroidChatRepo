package edu.galileo.android.androidchat.contactlist.ui.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.domain.AvatarHelper;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.lib.ImageLoading;

/**
 * Created by Alejandro on 17/7/2017.
 * clase que me permite colocar en mi layout todos mi contactos y mostrar en mi View
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private List<User> contactList;
    private ImageLoading imageLoading; // para poder cargar la imagen
    private OnItemClickListener itemClickListener;

    public ContactListAdapter(List<User> contactList, ImageLoading imageLoading, OnItemClickListener itemClickListener) {
        this.contactList = contactList;
        this.imageLoading = imageLoading;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_contact, parent, false);
        return new ViewHolder(view);
    }

    /**
     * metodo para realizar algunas accion sobre el ViewHolder, es decir me permite asignar los valores
     * y poder visualizarlos en mi view de los contacto de mi app
     * @param holder toma los valores de mi elementos dl layout es decir mis input y otros
     * @param position la posicion en el cual se encuenta en mi lista
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = contactList.get(position);
        holder.setClickListener(user, itemClickListener);

        String email = user.getEmail();
        boolean online = user.isOnline();
        String status = online ? "online" : "offLine";
        int color = online ? Color.GREEN : Color.RED;

        holder.txtUser.setText(email);
        holder.txtStatus.setText(status);
        holder.txtStatus.setTextColor(color);

        imageLoading.load(holder.imageView, AvatarHelper.getAvatarUrl(email)); // para mostrar la imagen
    }

    /**
     * metodo que me devuelve la cuenta de mi lista
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    /**
     * metodo para agregar un contacto
     * si estoy en la lista no lo agrego con contains
     * @param user
     */
    public void add(User user) {
        if (!contactList.contains(user)){
            contactList.add(user);
            notifyDataSetChanged();
        }
    }

    /**
     * metodo para actualizar el estado de un usario
     * @param user
     */
    public void update(User user) {
        if (contactList.contains(user)){
            int index = contactList.indexOf(user);
            contactList.set(index,user);
            notifyDataSetChanged();
        }
    }

    /**
     * metodo para eliminar un usario
     * @param user
     */
    public void remove(User user) {
        if (contactList.contains(user)){
            contactList.remove(user);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageView)
        CircleImageView imageView;
        @Bind(R.id.txtUser)
        TextView txtUser;
        @Bind(R.id.txtStatus)
        TextView txtStatus;
        private View view;
        /**
         * contructor de mi clase ViewHolder
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }
        /**
         * metodo para recibir los eventos del click
         */
        private void setClickListener(final User user, final OnItemClickListener listener){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(user);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(user);
                    return false;
                }
            });
        }
    }
}
