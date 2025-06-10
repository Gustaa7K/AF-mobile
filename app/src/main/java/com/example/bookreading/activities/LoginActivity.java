package com.example.bookreading.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookreading.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCadastro = findViewById(R.id.btnCadastro);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> logarUsuario());
        btnCadastro.setOnClickListener(v -> cadastrarUsuario());
    }

    private void logarUsuario() {
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login realizado!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Erro: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void cadastrarUsuario() {
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
