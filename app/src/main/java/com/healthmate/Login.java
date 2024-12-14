package com.healthmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.healthmate.database.AppDatabase;
import com.healthmate.database.bean.Utente;

public class Login extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Associa il layout del fragment
        View view = inflater.inflate(R.layout.activity_login, container, false);

        // Trova i riferimenti ai componenti del layout
        EditText usernameEditText = view.findViewById(R.id.emailEditText);
        EditText passwordEditText = view.findViewById(R.id.passwordEditText);
        Button loginButton = view.findViewById(R.id.loginButton);

        // Imposta il click listener sul pulsante
        loginButton.setOnClickListener(v -> {
            AppDatabase db = AppDatabase.getDatabase(requireContext());
            AccountDAO userDao = db.accountDAO();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // FIXME: Operazioni sul database su thread separato
            db.getOperationExecutor().submit(() -> {
                Utente user = userDao.getPaziente(username, password);
                if (user == null) {
                    user = userDao.getMedico(username, password);
                }

                // Usa il thread principale per aggiornare l'UI
                Utente finalUser = user;
                requireActivity().runOnUiThread(() -> {
                    if (finalUser != null) {
                        // Autenticazione riuscita
                        Intent intent = new Intent(requireActivity(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    } else {
                        // Autenticazione fallita
                        Toast.makeText(requireContext(), "Nome utente o password errati", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });

        return view;
    }
}
