package api;

import java.util.HashMap;
import java.util.Map;

/**
 * API Original do Instagram (Simulada).
 * Nota: Usa Map<String, Object> em vez de Conteudo e retorna Map para Stats.
 */
public class InstagramApi {

    public String uploadPhoto(Map<String, Object> photoDetails) {
        String caption = (String) photoDetails.get("caption");
        String imageUrl = (String) photoDetails.get("url");
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new IllegalStateException("Instagram: URL da imagem e obrigatoria.");
        }
        System.out.println("  [INSTAGRAM API] Postando foto com legenda: '" + caption + "' na URL: " + imageUrl);
        // Simula o retorno de um ID.
        return "INSTA-" + System.currentTimeMillis();
    }

    public Map<String, Integer> getInsights(String postId) {
        System.out.println("  [INSTAGRAM API] Buscando insights para: " + postId);
        // Retorna likes, comments, reach (incompatibilidade)
        Map<String, Integer> insights = new HashMap<>();
        insights.put("likesCount", (int)(Math.random() * 1000));
        insights.put("commentsCount", (int)(Math.random() * 100));
        insights.put("reachCount", (int)(Math.random() * 10000));
        return insights;
    }
}