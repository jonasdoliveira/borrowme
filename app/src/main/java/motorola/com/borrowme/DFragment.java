package motorola.com.borrowme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import motorola.com.borrowme.database.dao.CollectionDAO;
import motorola.com.borrowme.database.entities.CollectionEntity;

public class DFragment extends DialogFragment{

    //key for intent parameter
    public static final String KEY = "KEY";

    private int meuParametro;
    private InsertCallback clbk;

    private EditText edtDialogText;

    public static DFragment newInstance(int parametro) {
        DFragment myDialog = new DFragment ();

        Bundle args = new Bundle();
        args.putInt(KEY, parametro);
        myDialog.setArguments(args);
        myDialog.fetchArguments();
        return myDialog;
    }

    private void fetchArguments() {
        meuParametro = getArguments().getInt(KEY, -1);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Cria o Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Infla meu layout personalizado
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_new_collection_fragment, null));
        //adiciona o título
        builder.setTitle("Add Collection");
        //adiciona os botões
        AlertDialog.Builder ok = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                CollectionEntity new_collection = new CollectionEntity(edtDialogText.getText().toString());
                CollectionDAO.getInstance(getActivity()).insert(new_collection);
                clbk.collectionCreated();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Nothing to see here
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        MapComponents(dialog);

        return dialog;

    }

    private void MapComponents(AlertDialog dialog) {
        edtDialogText = (EditText) dialog.findViewById(R.id.dialog_new_collection_name);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        clbk = (InsertCallback) activity;
    }

    interface InsertCallback {
        public void collectionCreated();
    }

}

