package com.healthmate.view.visite;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.healthmate.R;
import com.healthmate.database.AppDatabase;
import com.healthmate.database.bean.Referto;
import com.healthmate.database.dao.CartellaClinicaDAO;

import java.io.Serializable;

public class AddRefertoDialogFragment extends DialogFragment {
    private static final int PICK_PDF_JPEG = 1;
    private EditText editTextNome;
    private EditText editTextDescrizione;
    private Button buttonAllegato;
    private Uri selectedFileUri;

    private CartellaClinicaDAO cartellaClinicaDAO;

    public interface AddRefertoListener {
        void onRefertoAdded(Referto referto);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_referto, null);

        editTextNome = view.findViewById(R.id.editTextNomeReferto);
        editTextDescrizione = view.findViewById(R.id.editTextDescrizioneReferto);
        buttonAllegato = view.findViewById(R.id.buttonAllegato);

        buttonAllegato.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf,image/jpeg");
            startActivityForResult(Intent.createChooser(intent, "Seleziona PDF o JPEG"), PICK_PDF_JPEG);
        });

        builder.setView(view)
                .setTitle("Aggiungi Nuovo Referto")
                .setPositiveButton("Salva", (dialog, which) -> {
                    if (validateInput()) {
                        salvaReferto();
                    }
                })
                .setNegativeButton("Annulla", (dialog, which) -> dismiss());

        return builder.create();
    }

    private boolean validateInput() {
        String nome = editTextNome.getText().toString().trim();
        String descrizione = editTextDescrizione.getText().toString().trim();

        if (nome.isEmpty()) {
            editTextNome.setError("Nome richiesto");
            return false;
        }

        if (descrizione.isEmpty()) {
            editTextDescrizione.setError("Descrizione richiesta");
            return false;
        }

//        if (selectedFileUri == null) {
//            Toast.makeText(getContext(), "Seleziona un allegato PDF o JPEG", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }

    private void salvaReferto() {
        String nome = editTextNome.getText().toString().trim();
        String descrizione = editTextDescrizione.getText().toString().trim();
        String allegato = "shish.pdf";//selectedFileUri.toString();



        Referto nuovoReferto = new Referto();
        nuovoReferto.setNome(nome);
        nuovoReferto.setDescrizione(descrizione);
        nuovoReferto.setAllegato(allegato);
        nuovoReferto.setCartellaclinica_id(1);

        AppDatabase.getDatabase(requireContext()).getOperationExecutor().execute(() -> {
            CartellaClinicaDAO cartellaClinicaDAO = AppDatabase.getDatabase(requireContext()).cartellaClinicaDAO();
//            cartellaClinicaDAO.addReferto(nuovoReferto);
            System.out.println(cartellaClinicaDAO.showReferti());

            requireActivity().runOnUiThread(() -> {
                Bundle result = new Bundle();
                result.putSerializable("referto", nuovoReferto);
                getParentFragmentManager().setFragmentResult("refertoKey", result);
                dismiss();
            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_JPEG && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                selectedFileUri = data.getData();
                buttonAllegato.setText(getFileNameFromUri(selectedFileUri));
            }
        }
    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            android.database.Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}