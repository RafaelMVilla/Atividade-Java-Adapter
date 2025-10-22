package adapter;

import api.TwitterApi;
import model.Conteudo;
import model.Estatisticas;
import model.Publicacao;
import model.RespostaUnificada;
import target.SocialMediaManager;


public class TwitterAdapter implements SocialMediaManager {
    
    private final TwitterApi api; 

    public TwitterAdapter() {
        this.api = new TwitterApi();
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
            String[] twitterPayload = new String[]{conteudo.getTexto(), conteudo.getImagemUrl()};
            
            simularLatencia();
            
            String newPostId = api.tweetar(twitterPayload);
            
            Publicacao publicacao = new Publicacao(newPostId, "POSTADO", getPlatformName());
            return RespostaUnificada.sucesso("Tweet publicado com sucesso.", publicacao);
            
        } catch (IllegalArgumentException e) {
            return RespostaUnificada.falha("Erro de validacao no Twitter: " + e.getMessage());
        } catch (Exception e) {
            return RespostaUnificada.falha("Erro desconhecido ao publicar no Twitter: " + e.getMessage());
        }
    }

    @Override
    public RespostaUnificada<Estatisticas> obterEstatisticas(String postId) {
        try {
            simularLatencia();
            
            int[] metrics = api.obterMetrics(postId); 
            
            Estatisticas estatisticas = new Estatisticas(metrics[0], metrics[1] + metrics[2], metrics[2]);
            
            return RespostaUnificada.sucesso("Estatisticas do Tweet obtidas com sucesso.", estatisticas);
            
        } catch (Exception e) {
            return RespostaUnificada.falha("Erro ao obter estatisticas do Twitter: " + e.getMessage());
        }
    }

    @Override
    public String getPlatformName() {
        return "Twitter";
    }
}