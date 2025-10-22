package api;

import java.util.Random;

/**
 * API Original do LinkedIn (Simulada).
 * Nota: Usa classes de configuracao diferentes e retorna objeto Stats proprio.
 */
public class LinkedInApi {

    public static class ShareConfig {
        public String companyId;
        public String postText;
        public String linkUrl;
    }
    
    public static class LinkedInStats {
        public int impressions;
        public int clicks;
    }

    public String shareUpdate(ShareConfig config) {
        if (config.companyId == null || config.postText == null) {
            throw new NullPointerException("LinkedIn: ID da empresa e texto sao obrigatorios.");
        }
        System.out.println("  [LINKEDIN API] Compartilhando atualizacao para " + config.companyId + ": " + config.postText);
        // Simula o retorno de um ID.
        return "LKD-" + System.currentTimeMillis();
    }

    public LinkedInStats fetchMetrics(String updateId) {
        System.out.println("  [LINKEDIN API] Buscando metricas para: " + updateId);
        LinkedInStats stats = new LinkedInStats();
        stats.impressions = (int)(Math.random() * 20000);
        stats.clicks = (int)(Math.random() * 100);
        return stats;
    }
}