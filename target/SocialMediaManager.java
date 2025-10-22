package target;

import model.Conteudo;
import model.Estatisticas;
import model.Publicacao;
import model.RespostaUnificada;

public interface SocialMediaManager {
    
    RespostaUnificada<Publicacao> publicarConteudo(Conteudo conteudo);

    RespostaUnificada<Estatisticas> obterEstatisticas(String postId);
    
    String getPlatformName();
}