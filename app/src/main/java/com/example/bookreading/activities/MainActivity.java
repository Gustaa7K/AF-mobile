package com.example.bookreading.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreading.R;
import com.example.bookreading.adapters.LivroAdapter;
import com.example.bookreading.models.Livro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private LivroAdapter adapter;
    private List<Livro> listaLivros = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerViewLivros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LivroAdapter(listaLivros, livro -> {
            // Clique para editar
            Intent intent = new Intent(MainActivity.this, EditBookActivity.class);
            intent.putExtra("livro", livro);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        Button btnAdicionarLivro = findViewById(R.id.btnAdicionarLivro);
        btnAdicionarLivro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditBookActivity.class);
            startActivity(intent);
        });

        Button btnDashboard = findViewById(R.id.btnDashboard);
        btnDashboard.setOnClickListener(v -> {
            startActivity(new Intent(this, DashboardActivity.class));
        });

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Você saiu!", Toast.LENGTH_SHORT).show();
            finish();
        });

        carregarLivros();
    }

    private void carregarLivros() {
        String uid = mAuth.getCurrentUser().getUid();
        db.collection("livros")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(query -> {
                    listaLivros.clear();
                    for (QueryDocumentSnapshot doc : query) {
                        Livro livro = doc.toObject(Livro.class);
                        livro.setId(doc.getId());
                        listaLivros.add(livro);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erro ao carregar livros", Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLivros(); // recarrega ao voltar de edição
    }
}
