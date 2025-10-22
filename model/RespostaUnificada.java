package model;

import java.util.Objects;

/**
 * Tarefa 2: Sistema de Resposta Unificado.
 * Classe que encapsula a resposta de qualquer operacao usando Generics (T).
 * @param <T> O tipo de dado (Publicacao, Estatisticas, etc.) que a resposta contem.
 */
public class RespostaUnificada<T> {
    private final boolean sucesso;
    private final String mensagem;
    private final T dados;

    private RespostaUnificada(boolean sucesso, String mensagem, T dados) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    // Factory Method para sucesso
    public static <T> RespostaUnificada<T> sucesso(String mensagem, T dados) {
        return new RespostaUnificada<>(true, mensagem, dados);
    }

    // Factory Method para falha
    public static <T> RespostaUnificada<T> falha(String mensagem) {
        // Usa null para os dados em caso de falha, para evitar o uso de Optional em cada camada
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