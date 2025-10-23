Visão Geral do Projeto

Este projeto implementa um sistema unificado de gerenciamento e agendamento de conteúdo para múltiplas redes sociais (Twitter, Instagram e LinkedIn) utilizando o padrão de design Adapter em Java. O objetivo principal é fornecer uma interface única e coesa para o cliente (a agência de marketing), isolando-o das APIs originais heterogêneas e incompatíveis.

O sistema também incorpora os padrões Factory Method e Strategy para gerenciar a criação e a seleção dinâmica dos Adapters, além de um sistema de resposta unificado baseado em Generics. Decidi também manter tags durante a execução para verificar o que está sendo executado no momento pelo código, assim identificando qual momento e o que é utilizado facilitadno a visualização da resposta do código criado.

______________________________________________________________

FUNCIONAMENTO DO CÓDIGO:

Iniciaização pelo cliente:
A classe SocialMediaIntegrationApp atua como o Cliente, responsável por receber a entrada do usuário.

________________________
Seleção do adapter:
Ao receber o comando de publicar ou checar estatísticas, o Cliente solicita o serviço à SocialMediaAdapterFactory. A Fábrica usa a plataforma escolhida (Strategy) para criar ou fornecer a instância correta do Adapter (TwitterAdapter, InstagramAdapter, etc.).

________________________
Comunicação unificada:
O Cliente faz a chamada (e.g., publicarConteudo()) através da interface SocialMediaManager (o Target), sem ter conhecimento da API específica que será usada.

________________________
O Trabalho do Adapter:
O Adapter é o coração do sistema, ele pega o modelo de dados unificado e o converte para formato de dados que a API origianl exige.
O Adapter usando composição chama o método da API original passando os dados traduzidos.

________________________
Resposta da API Original:
A classe simule a execução da ação (publicação ou métricas) e retorna o resultado em seu formato nativo e incompatível.

________________________
Tradução e Empacotamento:
O Adapter pega o retorno incompatível da API e converte de volta para o modelo de domínio unificado.
O resultado final é empacotado na estrutura genérica, informando se a operação foi um sucesso ou uma falha.

________________________
Resultado para o CLiente:
O CLiente recebe a resposta unificada e exibe o status final de forma limpa e padronizada no terminal, as tags facilitaram a visualização desse fluxo descrito acima.
______________________________________________________________