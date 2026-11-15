import controle.ctlAssinatura;
import fronteira.viewAssinatura;

import java.util.Scanner;

/**
 * Classe principal para execução do Caso de Uso "Assinar Plano de Refeições"
 * exercitando as mensagens 1 a 65 do Diagrama de Sequência Unificado (Fluxo Principal + FA1, FA2 e FA3).
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--interativo")) {
            executarModoInterativo();
        } else {
            executarSimulacaoCompleta();
        }
    }

    public static void executarSimulacaoCompleta() {
        System.out.println("================================================================================");
        System.out.println("  MINHA REFEIÇÃO - SIMULAÇÃO COMPLETA DO CASO DE USO (MENSAGENS 1 A 65)");
        System.out.println("  Arquitetura BCE (Boundary-Controller-Entity) + Padrões GRASP");
        System.out.println("================================================================================");

        viewAssinatura view = new viewAssinatura(new ctlAssinatura());

        // -------------------------------------------------------------------------
        // ETAPA 1: Solicitação de Assinatura e Autenticação SMS (Msgs 1 a 12 + FA1)
        // -------------------------------------------------------------------------
        System.out.println("\n>>> ETAPA 1: Autenticação do Assinante via SMS (Msgs 1 a 12 | Inclui FA1)");
        view.solicitarAssinatura("11987654321");

        // FA1: 1ª tentativa com código incorreto -> aciona registrarTentativaInvalida() (msg 12)
        view.autenticarAssinante("000000");

        // Fluxo Principal: 2ª tentativa com código correto -> aciona confirmarAutenticacao() (msg 11)
        String codigoCorreto = view.getCtl().getAssinante().getCodigoConfirmacao().getCodigoSMS();
        view.autenticarAssinante(codigoCorreto);

        // -------------------------------------------------------------------------
        // ETAPA 2: Consulta e Contratação de Plano (Msgs 13 a 23)
        // -------------------------------------------------------------------------
        System.out.println("\n>>> ETAPA 2: Consulta e Seleção de Plano de Assinatura (Msgs 13 a 23)");
        view.consultarPlanos();
        view.contratarPlano(1); // Plano Semanal (Limite: 5 refeições, 7 dias, R$ 89,00)

        // -------------------------------------------------------------------------
        // ETAPA 3: Preferências Alimentares e Filtragem de Cardápio (Msgs 24 a 30)
        // -------------------------------------------------------------------------
        System.out.println("\n>>> ETAPA 3: Definição de Preferências Alimentares e Filtro de Cardápio (Msgs 24 a 30)");
        view.definirPreferenciasAlimentares("Vegetariana");

        // -------------------------------------------------------------------------
        // ETAPA 4: Montagem do Pedido com Validação de Limite (Msgs 31 a 41 + FA2)
        // -------------------------------------------------------------------------
        System.out.println("\n>>> ETAPA 4: Inclusão de Refeições no Pedido (Msgs 31 a 41 | Inclui FA2)");
        // FA2: Tentativa de incluir 6 refeições em um plano com limite de 5 -> bloqueado por validarLimite()
        view.incluirRefeicao(3, 6);

        // Inclusão válida dentro do limite de 5 refeições (Prato Principal, Acompanhamento e Sobremesa)
        view.incluirRefeicao(3, 2); // 2x Lasanha de Berinjela (Prato Principal)
        view.incluirRefeicao(5, 2); // 2x Arroz Integral com Brocolis (Acompanhamento)
        view.incluirRefeicao(9, 1); // 1x Salada de Frutas Frescas (Sobremesa)

        // -------------------------------------------------------------------------
        // ETAPA 5: Revisão do Pedido e Agendamento de Entrega (Msgs 42 a 53)
        // -------------------------------------------------------------------------
        System.out.println("\n>>> ETAPA 5: Revisão do Pedido e Configuração de Entrega (Msgs 42 a 53)");
        view.revisarPedido();
        view.agendarEntrega("Rua das Flores, 123 - CEP 01000-000");

        // -------------------------------------------------------------------------
        // ETAPA 6: Pagamento, Ativação da Assinatura e Persistência CSV (Msgs 54 a 65 + FA3)
        // -------------------------------------------------------------------------
        System.out.println("\n>>> ETAPA 6: Processamento de Pagamento e Ativação (Msgs 54 a 65 | Inclui FA3)");
        // FA3: 1ª tentativa com cartão recusado -> aciona manterAguardandoPagamento() (msg 65)
        view.efetuarPagamento("4111-2222-3333-0000");

        // Fluxo Principal: 2ª tentativa com cartão válido -> aprova, ativa assinatura e grava CSV (msgs 60 a 64)
        view.efetuarPagamento("5555-4444-3333-1234");

        System.out.println("\n================================================================================");
        System.out.println("  SIMULAÇÃO CONCLUÍDA COM SUCESSO!");
        System.out.println("================================================================================");
    }

    public static void executarModoInterativo() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        viewAssinatura view = new viewAssinatura(new ctlAssinatura());

        System.out.println("=== MODO INTERATIVO: MINHA REFEIÇÃO ===");
        System.out.print("Informe seu celular: ");
        view.solicitarAssinatura(scanner.nextLine().trim());

        while (!view.getCtl().getAssinante().isAutenticado()
                && view.getCtl().getAssinante().getCodigoConfirmacao().getTentativas() < 3) {
            System.out.print("Digite o código SMS recebido: ");
            view.autenticarAssinante(scanner.nextLine().trim());
        }
        if (!view.getCtl().getAssinante().isAutenticado()) {
            System.out.println("Número máximo de tentativas excedido. Encerrando.");
            return;
        }

        view.consultarPlanos();
        System.out.print("Escolha o ID do plano (1, 2 ou 3): ");
        view.contratarPlano(Integer.parseInt(scanner.nextLine().trim()));

        System.out.print("Informe sua preferência alimentar (Tradicional, Vegetariana, Sem lactose): ");
        view.definirPreferenciasAlimentares(scanner.nextLine().trim());

        while (view.getCtl().getAssinatura().getPedido().getTotalRefeicoes()
                < view.getCtl().getAssinatura().getPlano().getLimiteRefeicoes()) {
            System.out.print("ID do produto para incluir (ou 0 para encerrar seleção): ");
            int idProd = Integer.parseInt(scanner.nextLine().trim());
            if (idProd == 0) {
                break;
            }
            System.out.print("Quantidade: ");
            int qtd = Integer.parseInt(scanner.nextLine().trim());
            view.incluirRefeicao(idProd, qtd);
        }

        view.revisarPedido();
        System.out.print("Informe os dados do endereço de entrega (ex.: Rua das Flores, 123 - CEP 01000-000): ");
        view.agendarEntrega(scanner.nextLine().trim());

        while (!"Ativa".equals(view.getCtl().getAssinatura().getStatus())) {
            System.out.print("Informe os dados do cartão de crédito (termine com 0000 para testar recusa): ");
            view.efetuarPagamento(scanner.nextLine().trim());
        }
    }
}
