package factory;

import adapter.InstagramAdapter;
import adapter.LinkedInAdapter;
import adapter.TwitterAdapter;
import target.SocialMediaManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SocialMediaAdapterFactory {

    private static final ConcurrentMap<SocialMediaPlatform, SocialMediaManager> adapterCache = new ConcurrentHashMap<>();

   
    public static SocialMediaManager getAdapter(SocialMediaPlatform platform) {
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
                    throw new UnsupportedOperationException("TikTok Adapter ainda nao implementado.");
                default:
                    throw new IllegalArgumentException("Plataforma desconhecida: " + p);
            }
        });
    }
    
    public static void clearCache() {
        adapterCache.clear();
    }
}