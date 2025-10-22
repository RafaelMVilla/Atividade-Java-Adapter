package api;

public class TwitterApi {

    public String tweetar(String[] conteudo) {
        String texto = conteudo[0];
        String mediaUrl = conteudo[1];
        if (texto == null || texto.length() > 280) {
            throw new IllegalArgumentException("Twitter: Texto invalido ou muito longo.");
        }
        System.out.println("  [TWITTER API] Tuitando: '" + texto.substring(0, Math.min(20, texto.length())) + "...' com midia: " + mediaUrl);
        return "TWT-" + System.currentTimeMillis(); 
    }

    public int[] obterMetrics(String tweetId) {
        System.out.println("  [TWITTER API] Buscando metricas para: " + tweetId);
        return new int[]{ (int)(Math.random() * 500), (int)(Math.random() * 50), (int)(Math.random() * 20) };
    }
}