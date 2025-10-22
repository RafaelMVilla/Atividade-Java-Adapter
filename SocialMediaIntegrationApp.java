
import factory.SocialMediaAdapterFactory;
import factory.SocialMediaPlatform;
import model.Conteudo;
import model.Estatisticas;
import model.Publicacao;
import model.RespostaUnificada;
import target.SocialMediaManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class SocialMediaIntegrationApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, String> publicacoesAtivas = new HashMap<>(); 

    public static void main(String[] args) {
        System.out.println("=== Sistema Unificado de Agendamento e Publicacao (Adapter Pattern) ===");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Publicar Conteudo");
            System.out.println("2. Obter Estatisticas (Publicacoes Ativas)");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opcao: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        publicarConteudoMenu();
                        break;
                    case 2:
                        obterEstatisticasMenu();
                        break;
                    case 3:
                        running = false;
                        System.out.println("Saindo do sistema. Tchau!");
                        break;
                    default:
                        System.out.println("Opcao invalida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero.");
            }
        }
    }

    private static void publicarConteudoMenu() {
        System.out.println("\n--- PUBLICAR CONTEUDO ---");
        SocialMediaPlatform plataforma = selecionarPlataforma();
        if (plataforma == null) return;

        System.out.print("Digite o texto do post: ");
        String texto = scanner.nextLine();
        System.out.print("Digite a URL da imagem/link (Deixe vazio se nao houver): ");
        String imagemUrl = scanner.nextLine();
        
        Conteudo conteudo = new Conteudo(texto, imagemUrl.isEmpty() ? null : imagemUrl);

        SocialMediaManager manager = SocialMediaAdapterFactory.getAdapter(plataforma);
        
        System.out.println("\n[CLIENTE] Tentando publicar em " + manager.getPlatformName() + "...");
        
        // Mensagem de processamento antes de chamar a API 
        System.out.println("Aguarde... processando a solicitacao e aguardando a resposta da API.");
        
        // Chamada unificada 
        RespostaUnificada<Publicacao> resposta = manager.publicarConteudo(conteudo);

        // Tratamento da Resposta Unificada 
        if (resposta.isSucesso()) {
            Publicacao pub = resposta.getDados();
            System.out.println("\n--- SUCESSO DE PUBLICACAO ---");
            System.out.println("Mensagem: " + resposta.getMensagem());
            System.out.println(pub);
            publicacoesAtivas.put(pub.getPublicacaoId(), pub.getPlataforma()); 
        } else {
            System.out.println("\n--- FALHA NA PUBLICACAO ---");
            System.out.println("Erro: " + resposta.getMensagem());
            System.out.println("Plataforma: " + manager.getPlatformName());
        }
    }

    private static void obterEstatisticasMenu() {
        if (publicacoesAtivas.isEmpty()) {
            System.out.println("\nNenhuma publicacao ativa para checar estatisticas. Publique algo primeiro (Opcao 1).");
            return;
        }

        System.out.println("\n--- OBTER ESTATISTICAS ---");
        System.out.println("Publicacoes ativas disponiveis:");
        int index = 1;
        String[] postIds = publicacoesAtivas.keySet().toArray(new String[0]);
        for (String id : postIds) {
            System.out.println(index++ + ". ID: " + id + " (Plataforma: " + publicacoesAtivas.get(id) + ")");
        }

        System.out.print("Digite o numero da publicacao para checar (ou 0 para cancelar): ");
        try {
            int postChoice = Integer.parseInt(scanner.nextLine());
            if (postChoice == 0) return;
            if (postChoice < 1 || postChoice > postIds.length) {
                System.out.println("Opcao invalida.");
                return;
            }

            String postId = postIds[postChoice - 1];
            String plataformaStr = publicacoesAtivas.get(postId);
            SocialMediaPlatform plataforma = SocialMediaPlatform.valueOf(plataformaStr.toUpperCase());

            SocialMediaManager manager = SocialMediaAdapterFactory.getAdapter(plataforma);

            System.out.println("\n[CLIENTE] Tentando obter estatisticas para ID " + postId + " em " + manager.getPlatformName() + "...");
            
            // Mensagem de processamento antes de chamar a API 
            System.out.println("Aguarde... processando a solicitacao e aguardando a resposta da API.");

            // Chamada unificada 
            RespostaUnificada<Estatisticas> resposta = manager.obterEstatisticas(postId);

            // Tratamento da Resposta Unificada 
            if (resposta.isSucesso()) {
                System.out.println("\n--- SUCESSO DE ESTATISTICAS ---");
                System.out.println("Mensagem: " + resposta.getMensagem());
                System.out.println(resposta.getDados());
            } else {
                System.out.println("\n--- FALHA NAS ESTATISTICAS ---");
                System.out.println("Erro: " + resposta.getMensagem());
                System.out.println("ID: " + postId);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Entrada invalida ou plataforma desconhecida.");
        }
    }

    private static SocialMediaPlatform selecionarPlataforma() {
        System.out.println("Selecione a plataforma:");
        SocialMediaPlatform[] platforms = SocialMediaPlatform.values();
        for (int i = 0; i < platforms.length; i++) {
            System.out.println((i + 1) + ". " + platforms[i]);
        }
        System.out.print("Escolha (1-" + platforms.length + "): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > platforms.length) {
                System.out.println("Opcao de plataforma invalida.");
                return null;
            }
            return platforms[choice - 1];
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida. Digite um numero.");
            return null;
        } catch (UnsupportedOperationException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
}