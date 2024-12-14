package com.healthmate.view.visite;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.UUID;

public class AddRefertoDialogFragment extends DialogFragment {
    private static final int PICK_PDF_JPEG = 1;
    private EditText editTextNome;
    private EditText editTextDescrizione;
    private EditText editTextAllegato;
    private Button buttonAllegato;
    private Uri selectedFileUri;
    private boolean isEditMode = false;
    private Referto existingReferto;

    public interface AddRefertoListener {
        void onRefertoAdded(Referto referto);
        void onRefertoUpdated(Referto referto);
    }

    // Factory method for creating a new referto
    public static AddRefertoDialogFragment newInstance() {
        return new AddRefertoDialogFragment();
    }

    // Factory method for editing an existing referto
    public static AddRefertoDialogFragment newInstance(Referto referto) {
        AddRefertoDialogFragment fragment = new AddRefertoDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("referto", referto);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Check if we're editing an existing referto
        if (getArguments() != null && getArguments().containsKey("referto")) {
            existingReferto = (Referto) getArguments().getSerializable("referto");
            isEditMode = true;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_referto, null);

        editTextNome = view.findViewById(R.id.editTextNomeReferto);
        editTextDescrizione = view.findViewById(R.id.editTextDescrizioneReferto);
        buttonAllegato = view.findViewById(R.id.buttonAllegato);

        // If in edit mode, populate existing data
        if (isEditMode && existingReferto != null) {
            editTextNome.setText(existingReferto.getNome());
            editTextDescrizione.setText(existingReferto.getDescrizione());
            buttonAllegato.setText(existingReferto.getAllegato());
        }

        buttonAllegato.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"application/pdf", "image/jpeg"});
            startActivityForResult(Intent.createChooser(intent, "Seleziona PDF o JPEG"), PICK_PDF_JPEG);
        });

        builder.setView(view)
                .setTitle(isEditMode ? "Modifica Referto" : "Aggiungi Nuovo Referto")
                .setPositiveButton(isEditMode ? "Aggiorna" : "Salva", (dialog, which) -> {
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
        String allegato = selectedFileUri != null
                ? getFileNameFromUri(selectedFileUri)
                : (isEditMode ? existingReferto.getAllegato() : generateDefaultFileName());


        if (nome.isEmpty()) {
            editTextNome.setError("Nome richiesto");
            return false;
        }

        if (descrizione.isEmpty()) {
            editTextDescrizione.setError("Descrizione richiesta");
            return false;
        }

        if (allegato.isEmpty()){
            editTextAllegato.setError("Allegato richiesto");
            return false;
        }
        return true;
    }

    private void salvaReferto() {
        String nome = editTextNome.getText().toString().trim();
        String descrizione = editTextDescrizione.getText().toString().trim();
        String allegato = getFileNameFromUri(selectedFileUri);

        Referto refertoToSave;
        if (isEditMode) {
            refertoToSave = existingReferto;
            refertoToSave.setNome(nome);
            refertoToSave.setDescrizione(descrizione);
            refertoToSave.setAllegato(allegato);
        } else {
            refertoToSave = new Referto();
            refertoToSave.setNome(nome);
            refertoToSave.setDescrizione(descrizione);
            refertoToSave.setAllegato(allegato);
            refertoToSave.setCartellaclinica_id(1); // Default cartellaClinica ID
        }

        AppDatabase.getDatabase(requireContext()).getOperationExecutor().execute(() -> {
            CartellaClinicaDAO cartellaClinicaDAO = AppDatabase.getDatabase(requireContext()).cartellaClinicaDAO();

            if (isEditMode) {
                cartellaClinicaDAO.updateReferto(refertoToSave);
            } else {
                refertoToSave.setId(cartellaClinicaDAO.lastId() + 1);
                System.out.println(cartellaClinicaDAO.showReferti());
                try{
                    cartellaClinicaDAO.addReferto(refertoToSave);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

                AddRefertoListener listener = (AddRefertoListener) getTargetFragment();
                if (listener != null) {
                    if (isEditMode) {
                        listener.onRefertoUpdated(refertoToSave);
                    } else {
                        listener.onRefertoAdded(refertoToSave);
                    }
                }
            });
    }

    private int generateUniqueId() {
        // Generate a unique integer ID
        return Math.abs(UUID.randomUUID().hashCode());
    }

    private String generateDefaultFileName() {
        // Generate a default file name if no file is selected
        return "Referto_" + System.currentTimeMillis() + ".pdf";
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
                if (cursor != null) cursor.close();
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