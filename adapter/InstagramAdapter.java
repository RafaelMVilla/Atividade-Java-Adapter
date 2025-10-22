package adapter;

import api.InstagramApi;
import model.Conteudo;
import model.Estatisticas;
import model.Publicacao;
import model.RespostaUnificada;
import target.SocialMediaManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Tarefa 1: Implementacao do Adapter para Instagram.
 * Adapta InstagramApi para a interface SocialMediaManager.
 */
public class InstagramAdapter implements SocialMediaManager {
    
    // Uso de Composition over Inheritance (Requisito 2)
    private final InstagramApi api; 

    public InstagramAdapter() {
        this.api = new InstagramApi();
    }

    @Override
    public RespostaUnificada<Publicacao> publicarConteudo(Conteudo conteudo) {
        try {
            // Transformacao do modelo de dados (Conteudo -> Map)
            Map<String, Object> instaPayload = new HashMap<>();
            instaPayload.put("caption", conteudo.getTexto());
            instaPayload.put("url", conteudo.getImagemUrl());

            // Instagram requer imagem, se nao tiver, falha
            if (conteudo.getImagemUrl() == null || conteudo.getImagemUrl().isEmpty()) {
                return RespostaUnificada.falha("Erro de validacao no Instagram: Publicacao de imagem/video requer uma URL.");
            }
            
            // Chamada a API original
            String newPostId = api.uploadPhoto(instaPayload);
            
            // Mapeamento de retorno para modelo unificado
            Publicacao publicacao = new Publicacao(newPostId, "PUBLICADO", getPlatformName());
            return RespostaUnificada.sucesso("Post no Instagram publicado com sucesso.", publicacao);
            
        } catch (IllegalStateException e) {
            // Tratamento de erro granular (Requisito 3)
            return RespostaUnificada.falha("Erro de estado no Instagram: " + e.getMessage());
        } catch (Exception e) {
            return RespostaUnificada.falha("Erro desconhecido ao publicar no Instagram: " + e.getMessage());
        }
    }

    @Override
    public RespostaUnificada<Estatisticas> obterEstatisticas(String postId) {
        try {
            // Chamada a API original
            Map<String, Integer> insights = api.getInsights(postId); 
            
            // Mapeamento de retorno (Map<String, Integer> -> Estatisticas)
            // Instagram: likesCount, commentsCount, reachCount
            int likes = insights.getOrDefault("likesCount", 0);
            int views = insights.getOrDefault("reachCount", 0);
            int comentarios = insights.getOrDefault("commentsCount", 0);

            Estatisticas estatisticas = new Estatisticas(likes, views, comentarios);
            
            return RespostaUnificada.sucesso("Insights do Instagram obtidos com sucesso.", estatisticas);
            
        } catch (Exception e) {
            return RespostaUnificada.falha("Erro ao obter insights do Instagram: " + e.getMessage());
        }
    }

    @Override
    public String getPlatformName() {
        return "Instagram";
    }
}