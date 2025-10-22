package model;

import java.util.Objects;

/**
 * Modelo de dados para o resultado de uma publicacao.
 */
public class Publicacao {
    private final String publicacaoId;
    private final String status;
    private final String plataforma;

    public Publicacao(String publicacaoId, String status, String plataforma) {
        this.publicacaoId = publicacaoId;
        this.status = status;
        this.plataforma = plataforma;
    }

    public String getPublicacaoId() {
        return publicacaoId;
    }

    public String getStatus() {
        return status;
    }

    public String getPlataforma() {
        return plataforma;
    }

    @Override
    public String toString() {
        return "Publicacao [ID=" + publicacaoId + ", Status='" + status + "', Plataforma='" + plataforma + "']";
    }
    
    // Simplificando equals/hashCode para foco no exercicio, mas idealmente seriam inclusos.
}