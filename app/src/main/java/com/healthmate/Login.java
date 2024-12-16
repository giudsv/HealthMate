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

import com.google.android.material.snackbar.Snackbar;
import com.healthmate.database.AppDatabase;
import com.healthmate.database.bean.Utente;

public class Login extends Fragment {

    public Login() {
        super(R.layout.activity_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                String user = null;
                System.out.println("esite coso: " + userDao.getPaziente("alamberti","niente123"));
                try{
                    user = userDao.getPaziente(username, password);
                    if (user == null) {
                        user = userDao.getMedico(username, password);
                    }
                }catch(Exception e){
                    e.printStackTrace(System.err);
                }
                // Usa il thread principale per aggiornare l'UI
                String finalUser = user;
                view.post(() -> {
                    if (finalUser != null) {
                        // Autenticazione riuscita
                        Toast.makeText(requireContext(), "Autenticazione riuscita", Toast.LENGTH_SHORT).show();
                    } else {
                        // Autenticazione fallita
                        Toast.makeText(requireContext(), "Nome utente o password errati", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }
}
