package adapter;

import api.LinkedInApi;
import model.Conteudo;
import model.Estatisticas;
import model.Publicacao;
import model.RespostaUnificada;
import target.SocialMediaManager;


public class LinkedInAdapter implements SocialMediaManager {
    
    private final LinkedInApi api; 

    public LinkedInAdapter() {
        this.api = new LinkedInApi();
    }

    private void simularLatencia() {
        try {
            System.out.println("  [LATENCIA - " + getPlatformName() + "] Simulando 2 segundos de latencia de rede...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        }
    }

    @Override
    public RespostaUnificada<Publicacao> publicarConteudo(Conteudo conteudo) {
        try {
            LinkedInApi.ShareConfig config = new LinkedInApi.ShareConfig();
            config.companyId = "ACME_CORP_ID_12345"; 
            config.postText = conteudo.getTexto();
            config.linkUrl = conteudo.getImagemUrl(); 
            
            simularLatencia();

            String newPostId = api.shareUpdate(config);
            
            Publicacao publicacao = new Publicacao(newPostId, "COMPARTILHADO", getPlatformName());
            return RespostaUnificada.sucesso("Atualizacao no LinkedIn compartilhada com sucesso.", publicacao);
            
        } catch (NullPointerException e) {
            return RespostaUnificada.falha("Erro de validacao no LinkedIn: " + e.getMessage());
        } catch (Exception e) {
            return RespostaUnificada.falha("Erro desconhecido ao publicar no LinkedIn: " + e.getMessage());
        }
    }

    @Override
    public RespostaUnificada<Estatisticas> obterEstatisticas(String postId) {
        try {
            simularLatencia();
            
            LinkedInApi.LinkedInStats metrics = api.fetchMetrics(postId); 
            
            Estatisticas estatisticas = new Estatisticas(metrics.clicks, metrics.impressions, 0); 
            
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