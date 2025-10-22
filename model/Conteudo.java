package model;

import java.util.Objects;

/**
 * Modelo de dados para o conteúdo a ser publicado.
 * Implementa hashCode e equals para segurança e comparacao.
 */
public class Conteudo {
    private final String texto;
    private final String imagemUrl;

    public Conteudo(String texto, String imagemUrl) {
        this.texto = texto;
        this.imagemUrl = imagemUrl;
    }

    public String getTexto() {
        return texto;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    @Override
    public String toString() {
        return "Conteudo [texto='" + (texto != null ? texto : "N/A") + "', imagemUrl='" + (imagemUrl != null ? imagemUrl : "N/A") + "']";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conteudo conteudo = (Conteudo) o;
        return Objects.equals(texto, conteudo.texto) && Objects.equals(imagemUrl, conteudo.imagemUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texto, imagemUrl);
    }
}