package com.example.bookreading.models;

import java.io.Serializable;

public class Livro implements Serializable {
    private String id;
    private String uid;
    private String titulo;
    private String autor;
    private int nota;
    private String comentario;
    private boolean favorito;

    public Livro() {
    }

    public Livro(String id, String uid, String titulo, String autor, int nota, String comentario, boolean favorito) {
        this.id = id;
        this.uid = uid;
        this.titulo = titulo;
        this.autor = autor;
        this.nota = nota;
        this.comentario = comentario;
        this.favorito = favorito;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public boolean isFavorito() { return favorito; }
    public void setFavorito(boolean favorito) { this.favorito = favorito; }
}
