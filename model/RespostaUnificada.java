package model;

import java.util.Objects;

public class RespostaUnificada<T> {
    private final boolean sucesso;
    private final String mensagem;
    private final T dados;

    private RespostaUnificada(boolean sucesso, String mensagem, T dados) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public static <T> RespostaUnificada<T> sucesso(String mensagem, T dados) {
        return new RespostaUnificada<>(true, mensagem, dados);
    }


    public static <T> RespostaUnificada<T> falha(String mensagem) {
        return new RespostaUnificada<>(false, mensagem, null); 
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public T getDados() {
        return dados;
    }

    @Override
    public String toString() {
        return "RespostaUnificada{" +
                "sucesso=" + sucesso +
                ", mensagem='" + mensagem + '\'' +
                ", dados=" + (dados != null ? dados.toString() : "NULO") +
                '}';
    }
}