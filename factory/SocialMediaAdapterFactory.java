package factory;

import adapter.InstagramAdapter;
import adapter.LinkedInAdapter;
import adapter.TwitterAdapter;
import target.SocialMediaManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Tarefa 3: Social Media Factory com Configuracao Dinamica.
 * Implementa o padrao Factory Method de forma Thread-Safe (Requisito 5).
 */
public class SocialMediaAdapterFactory {

    // Mapa thread-safe para armazenar instancias ja criadas (Singleton por tipo)
    private static final ConcurrentMap<SocialMediaPlatform, SocialMediaManager> adapterCache = new ConcurrentHashMap<>();

    /**
     * Cria e retorna uma instancia do Adapter apropriado de forma thread-safe.
     * Implementa o Factory Method e um cache basico.
     * @param platform A plataforma desejada.
     * @return O SocialMediaManager (Adapter) para a plataforma especificada.
     */
    public static SocialMediaManager getAdapter(SocialMediaPlatform platform) {
        // Usa computeIfAbsent para garantir que a criacao da instancia seja atomica
        return adapterCache.computeIfAbsent(platform, p -> {
            System.out.println("\n[FACTORY] Criando nova instancia do Adapter para: " + p);
            switch (p) {
                case TWITTER:
                    return new TwitterAdapter();
                case INSTAGRAM:
                    return new InstagramAdapter();
                case LINKEDIN:
                    return new LinkedInAdapter();
                case TIKTOK:
                    // Exemplo de como expandir
                    throw new UnsupportedOperationException("TikTok Adapter ainda nao implementado.");
                default:
                    throw new IllegalArgumentException("Plataforma desconhecida: " + p);
            }
        });
    }
    
    // Metodo para limpar o cache (util para testes)
    public static void clearCache() {
        adapterCache.clear();
    }
}