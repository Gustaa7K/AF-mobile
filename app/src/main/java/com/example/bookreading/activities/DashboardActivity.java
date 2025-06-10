package com.example.bookreading.activities;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookreading.R;
import com.example.bookreading.models.Livro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvTotalLivros, tvMediaNotas, tvTotalFavoritos;
    private ProgressBar pbTotalLivros, pbMediaNotas, pbFavoritos;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // TextViews
        tvTotalLivros = findViewById(R.id.tvTotalLivros);
        tvMediaNotas = findViewById(R.id.tvMediaNotas);
        tvTotalFavoritos = findViewById(R.id.tvTotalFavoritos);

        // ProgressBars
        pbTotalLivros = findViewById(R.id.pbTotalLivros);
        pbMediaNotas = findViewById(R.id.pbMediaNotas);
        pbFavoritos = findViewById(R.id.pbFavoritos);

        carregarEstatisticas();
    }

    private void carregarEstatisticas() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String uid = mAuth.getCurrentUser().getUid();

        db.collection("livros")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(query -> {
                    int total = query.size();
                    int somaNotas = 0;
                    int favoritos = 0;

                    for (QueryDocumentSnapshot doc : query) {
                        Livro l = doc.toObject(Livro.class);
                        somaNotas += l.getNota();
                        if (l.isFavorito()) favoritos++;
                    }

                    double media = total > 0 ? somaNotas / (double) total : 0.0;

                    // Textos
                    tvTotalLivros.setText("Livros lidos: " + total);
                    tvMediaNotas.setText("Média de notas: " + String.format("%.1f", media));
                    tvTotalFavoritos.setText("Favoritos: " + favoritos);

                    // ProgressBars
                    pbTotalLivros.setProgress(Math.min(total * 10, 100)); // 10 livros = 100%
                    pbMediaNotas.setProgress((int) (media * 10));         // 5.0 → 50
                    pbFavoritos.setProgress(total > 0 ? (favoritos * 100 / total) : 0);

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao carregar estatísticas", Toast.LENGTH_SHORT).show();
                });
    }
}
