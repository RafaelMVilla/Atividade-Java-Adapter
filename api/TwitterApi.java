package api;

/**
 * API Original do Twitter (Simulada).
 * Nota: Usa String[] em vez de Conteudo para simular incompatibilidade.
 */
public class TwitterApi {

    public String tweetar(String[] conteudo) {
        String texto = conteudo[0];
        String mediaUrl = conteudo[1];
        if (texto == null || texto.length() > 280) {
            throw new IllegalArgumentException("Twitter: Texto invalido ou muito longo.");
        }
        System.out.println("  [TWITTER API] Tuitando: '" + texto.substring(0, Math.min(20, texto.length())) + "...' com midia: " + mediaUrl);
        // Simula o retorno de um ID.
        return "TWT-" + System.currentTimeMillis(); 
    }

    public int[] obterMetrics(String tweetId) {
        System.out.println("  [TWITTER API] Buscando metricas para: " + tweetId);
        // Retorna likes, retweets, replies (incompatibilidade)
        return new int[]{ (int)(Math.random() * 500), (int)(Math.random() * 50), (int)(Math.random() * 20) };
    }
}