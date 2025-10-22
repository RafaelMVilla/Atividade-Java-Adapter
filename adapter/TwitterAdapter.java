package adapter;

import api.TwitterApi;
import model.Conteudo;
import model.Estatisticas;
import model.Publicacao;
import model.RespostaUnificada;
import target.SocialMediaManager;

/**
 * Tarefa 1: Implementacao do Adapter para Twitter.
 * Adapta TwitterApi para a interface SocialMediaManager.
 */
public class TwitterAdapter implements SocialMediaManager {
    
    // Uso de Composition over Inheritance (Requisito 2)
    private final TwitterApi api; 

    public TwitterAdapter() {
        this.api = new TwitterApi();
    }

    @Override
    public RespostaUnificada<Publicacao> publicarConteudo(Conteudo conteudo) {
        try {
            // Transformacao do modelo de dados (Conteudo -> String[])
            String[] twitterPayload = new String[]{conteudo.getTexto(), conteudo.getImagemUrl()};
            
            // Chamada a API original
            String newPostId = api.tweetar(twitterPayload);
            
            // Mapeamento de retorno para modelo unificado
            Publicacao publicacao = new Publicacao(newPostId, "POSTADO", getPlatformName());
            return RespostaUnificada.sucesso("Tweet publicado com sucesso.", publicacao);
            
        } catch (IllegalArgumentException e) {
            // Tratamento de erro granular (Requisito 3)
            return RespostaUnificada.falha("Erro de validacao no Twitter: " + e.getMessage());
        } catch (Exception e) {
            return RespostaUnificada.falha("Erro desconhecido ao publicar no Twitter: " + e.getMessage());
        }
    }

    @Override
    public RespostaUnificada<Estatisticas> obterEstatisticas(String postId) {
        try {
            // Chamada a API original
            int[] metrics = api.obterMetrics(postId); 
            
            // Mapeamento de retorno (int[] -> Estatisticas)
            // Twitter: metrics[0]=likes, metrics[1]=retweets, metrics[2]=replies
            // Mapeamos retweets + replies para views e usamos likes
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