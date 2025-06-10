package com.example.bookreading.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookreading.R;
import com.example.bookreading.models.Livro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditBookActivity extends AppCompatActivity {

    private EditText edtTitulo, edtAutor, edtNota, edtComentario;
    private CheckBox cbFavorito;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String livroId = null; // para edição

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtAutor = findViewById(R.id.edtAutor);
        edtNota = findViewById(R.id.edtNota);
        edtComentario = findViewById(R.id.edtComentario);
        cbFavorito = findViewById(R.id.cbFavorito);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Verifica se está editando
        if (getIntent().hasExtra("livro")) {
            Livro livro = (Livro) getIntent().getSerializableExtra("livro");
            livroId = livro.getId();
            edtTitulo.setText(livro.getTitulo());
            edtAutor.setText(livro.getAutor());
            edtNota.setText(String.valueOf(livro.getNota()));
            edtComentario.setText(livro.getComentario());
            cbFavorito.setChecked(livro.isFavorito());
        }

        btnSalvar.setOnClickListener(v -> salvarLivro());
    }

    private void salvarLivro() {
        String titulo = edtTitulo.getText().toString();
        String autor = edtAutor.getText().toString();
        int nota = Integer.parseInt(edtNota.getText().toString());
        String comentario = edtComentario.getText().toString();
        boolean favorito = cbFavorito.isChecked();
        String uid = mAuth.getCurrentUser().getUid();

        Livro livro = new Livro(livroId, uid, titulo, autor, nota, comentario, favorito);

        if (livroId == null) {
            db.collection("livros").add(livro)
                    .addOnSuccessListener(doc -> {
                        Toast.makeText(this, "Livro adicionado!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
        } else {
            db.collection("livros").document(livroId).set(livro)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Livro atualizado!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
        }
    }
}
