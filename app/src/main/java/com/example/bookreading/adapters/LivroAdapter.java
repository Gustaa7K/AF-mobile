package com.example.bookreading.adapters;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreading.R;
import com.example.bookreading.models.Livro;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.ViewHolder> {

    private List<Livro> livros;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Livro livro);
    }

    public LivroAdapter(List<Livro> livros, OnItemClickListener listener) {
        this.livros = livros;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitulo, tvAutor, tvNota;
        public ImageButton btnExcluir;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvNota = itemView.findViewById(R.id.tvNota);
            btnExcluir = itemView.findViewById(R.id.btnExcluir);
        }

        public void bind(Livro livro, OnItemClickListener listener, List<Livro> livros, LivroAdapter adapter) {
            tvTitulo.setText(livro.getTitulo());
            tvAutor.setText("Autor: " + livro.getAutor());
            tvNota.setText("Nota: " + livro.getNota());

            itemView.setOnClickListener(v -> listener.onItemClick(livro));

            btnExcluir.setOnClickListener(v -> {
                new AlertDialog.Builder(itemView.getContext())
                        .setTitle("Excluir livro")
                        .setMessage("Deseja excluir \"" + livro.getTitulo() + "\"?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            FirebaseFirestore.getInstance()
                                    .collection("livros")
                                    .document(livro.getId())
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        int pos = getAdapterPosition();
                                        if (pos != RecyclerView.NO_POSITION) {
                                            livros.remove(pos);
                                            adapter.notifyItemRemoved(pos);
                                        }
                                        Toast.makeText(itemView.getContext(), "Livro excluído", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(itemView.getContext(), "Erro ao excluir", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            });
        }
    }

    @NonNull
    @Override
    public LivroAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_livro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivroAdapter.ViewHolder holder, int position) {
        holder.bind(livros.get(position), listener, livros, this);
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }
}
