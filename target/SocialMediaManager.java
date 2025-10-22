package target;

import model.Conteudo;
import model.Estatisticas;
import model.Publicacao;
import model.RespostaUnificada;

/**
 * Interface Target: A interface unificada que o cliente (agencia) ira usar.
 * Tarefa 1: Implementacao dos Adapters Principais (Esta e a base).
 */
public interface SocialMediaManager {
    
    /**
     * Publica o conteudo na plataforma de forma unificada.
     * @param conteudo O objeto Conteudo padronizado.
     * @return RespostaUnificada<Publicacao> com status e dados da publicacao.
     */
    RespostaUnificada<Publicacao> publicarConteudo(Conteudo conteudo);

    /**
     * Obtem as estatisticas de uma publicacao de forma unificada.
     * @param postId O ID da publicacao na plataforma.
     * @return RespostaUnificada<Estatisticas> com status e dados de desempenho.
     */
    RespostaUnificada<Estatisticas> obterEstatisticas(String postId);
    
    /**
     * Retorna o nome da plataforma que o manager esta gerenciando.
     * @return O nome da plataforma (e.g., "Twitter", "Instagram").
     */
    String getPlatformName();
}