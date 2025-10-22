package adapter;

import api.LinkedInApi;
import model.Conteudo;
import model.Estatisticas;
import model.Publicacao;
import model.RespostaUnificada;
import target.SocialMediaManager;

/**
 * Tarefa 1: Implementacao do Adapter para LinkedIn.
 * Adapta LinkedInApi para a interface SocialMediaManager.
 */
public class LinkedInAdapter implements SocialMediaManager {
    
    // Uso de Composition over Inheritance (Requisito 2)
    private final LinkedInApi api; 

    public LinkedInAdapter() {
        this.api = new LinkedInApi();
    }

    @Override
    public RespostaUnificada<Publicacao> publicarConteudo(Conteudo conteudo) {
        try {
            // Transformacao do modelo de dados (Conteudo -> ShareConfig)
            LinkedInApi.ShareConfig config = new LinkedInApi.ShareConfig();
            config.companyId = "ACME_CORP_ID_12345"; // Simula um ID de empresa necessario
            config.postText = conteudo.getTexto();
            config.linkUrl = conteudo.getImagemUrl(); // Usa imagemUrl como linkUrl
            
            // Chamada a API original
            String newPostId = api.shareUpdate(config);
            
            // Mapeamento de retorno para modelo unificado
            Publicacao publicacao = new Publicacao(newPostId, "COMPARTILHADO", getPlatformName());
            return RespostaUnificada.sucesso("Atualizacao no LinkedIn compartilhada com sucesso.", publicacao);
            
        } catch (NullPointerException e) {
            // Tratamento de erro granular (Requisito 3)
            return RespostaUnificada.falha("Erro de validacao no LinkedIn: " + e.getMessage());
        } catch (Exception e) {
            return RespostaUnificada.falha("Erro desconhecido ao publicar no LinkedIn: " + e.getMessage());
        }
    }

    @Override
    public RespostaUnificada<Estatisticas> obterEstatisticas(String postId) {
        try {
            // Chamada a API original
            LinkedInApi.LinkedInStats metrics = api.fetchMetrics(postId); 
            
            // Mapeamento de retorno (LinkedInStats -> Estatisticas)
            // LinkedIn: impressions, clicks
            // Mapeamos impressions para views, e clicks para likes (conversao de conceito)
            Estatisticas estatisticas = new Estatisticas(metrics.clicks, metrics.impressions, 0); // Comentarios 0
            
            return RespostaUnificada.sucesso("Metricas do LinkedIn obtidas com sucesso.", estatisticas);
            
        } catch (Exception e) {
            return RespostaUnificada.falha("Erro ao obter metricas do LinkedIn: " + e.getMessage());
        }
    }

    @Override
    public String getPlatformName() {
        return "LinkedIn";
    }
}