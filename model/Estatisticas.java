package model;

import java.util.Objects;


public class Estatisticas {
    private final int likes;
    private final int views;
    private final int comentarios;

    public Estatisticas(int likes, int views, int comentarios) {
        this.likes = likes;
        this.views = views;
        this.comentarios = comentarios;
    }

    public int getLikes() {
        return likes;
    }

    public int getViews() {
        return views;
    }

    public int getComentarios() {
        return comentarios;
    }

    @Override
    public String toString() {
        return "Estatisticas [Likes=" + likes + ", Views=" + views + ", Comentarios=" + comentarios + "]";
    }
    
}