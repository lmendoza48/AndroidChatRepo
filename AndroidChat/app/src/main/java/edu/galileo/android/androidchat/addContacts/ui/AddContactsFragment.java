package edu.galileo.android.androidchat.addContacts.ui;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.addContacts.AddContactPresenter;
import edu.galileo.android.androidchat.addContacts.AddContactPresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 * esta clase me permite mostrar un fragment como si fuera una ventada PopUp en mi app
 */
public class AddContactsFragment extends DialogFragment implements AddContactView, DialogInterface.OnShowListener {


    @Bind(R.id.editTextEmail)
    EditText editTextEmail;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private AddContactPresenter presenter;

    public AddContactsFragment() {
        presenter = new AddContactPresenterImpl(this);
    }

    /**
     * metod para la vista y el dialogo = PopUp
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.addcontact_message_title)
                .setPositiveButton(R.string.addcontact_message_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.addcontact_message_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_contacts, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void showInput() {
        editTextEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
        editTextEmail.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
       progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void contactAdded() {
        Toast.makeText(getActivity(), R.string.addcontact_message_contactadded, Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void contactNotAdded() {
        editTextEmail.setText("");
        editTextEmail.setError(getString(R.string.addcontact_error_message));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * metodo cuando muestro el dialogo es decir PopUp
     * @param dialog
     */
    @Override
    public void onShow(DialogInterface dialog) {
        final AlertDialog dialog1 = (AlertDialog) getDialog();
        if (dialog1!= null){
            Button positveButton = dialog1.getButton(Dialog.BUTTON_POSITIVE);
            Button negativeButton = dialog1.getButton(Dialog.BUTTON_NEGATIVE);

            positveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.addContact(editTextEmail.getText().toString());
                }
            });
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        presenter.onShow();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
