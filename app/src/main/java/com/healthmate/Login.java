package com.healthmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.healthmate.database.AppDatabase;
import com.healthmate.database.bean.Utente;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

                AccountDAO userDao = db.accountDAO();

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                /*
                 * FIXME: Le operazioni sul database vanno eseguite su un Thread separato.
                 *  Usare `db.getOperationExecutor().submit(() -> <Operazione da eseguire>)`.
                 */
                Utente user = userDao.getPaziente(username, password);

                if (user != null) {
                    // Autenticazione riuscita
                    // Reindirizza l'utente alla schermata principale
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                user = userDao.getMedico(username, password);

                if (user != null) {
                    // Autenticazione riuscita
                    // Reindirizza l'utente alla schermata principale
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Autenticazione fallita
                    // Mostra un messaggio di errore all'utente
                    Toast.makeText(Login.this, "Nome utente o password errati", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
