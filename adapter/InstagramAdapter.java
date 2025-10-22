package adapter;

import api.InstagramApi;
import model.Conteudo;
import model.Estatisticas;
import model.Publicacao;
import model.RespostaUnificada;
import target.SocialMediaManager;

import java.util.HashMap;
import java.util.Map;


public class InstagramAdapter implements SocialMediaManager {
    
    private final InstagramApi api; 

    public InstagramAdapter() {
        this.api = new InstagramApi();
    }

    @Override
    public RespostaUnificada<Publicacao> publicarConteudo(Conteudo conteudo) {
        try {
            Map<String, Object> instaPayload = new HashMap<>();
            instaPayload.put("caption", conteudo.getTexto());
            instaPayload.put("url", conteudo.getImagemUrl());

            if (conteudo.getImagemUrl() == null || conteudo.getImagemUrl().isEmpty()) {
                return RespostaUnificada.falha("Erro de validacao no Instagram: Publicacao de imagem/video requer uma URL.");
            }
            
            String newPostId = api.uploadPhoto(instaPayload);
            
            Publicacao publicacao = new Publicacao(newPostId, "PUBLICADO", getPlatformName());
            return RespostaUnificada.sucesso("Post no Instagram publicado com sucesso.", publicacao);
            
        } catch (IllegalStateException e) {
            return RespostaUnificada.falha("Erro de estado no Instagram: " + e.getMessage());
        } catch (Exception e) {
            return RespostaUnificada.falha("Erro desconhecido ao publicar no Instagram: " + e.getMessage());
        }
    }

    @Override
    public RespostaUnificada<Estatisticas> obterEstatisticas(String postId) {
        try {
            Map<String, Integer> insights = api.getInsights(postId); 
            
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