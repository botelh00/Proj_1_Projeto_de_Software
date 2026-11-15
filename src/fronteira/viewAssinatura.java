package fronteira;

import controle.ctlAssinatura;
import entidade.ItemCardapio;
import entidade.PlanoAssinatura;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Fronteira (interface de console) do caso de uso "Assinar Plano de Refeições". */
public class viewAssinatura {
    private final ctlAssinatura ctl;
    private final Scanner in = new Scanner(System.in, "UTF-8");

    // estado de tela (controle dos laços FA1 e FA3)
    private boolean codigoValidado;
    private int tentativas;
    private boolean pagamentoAprovado;

    public viewAssinatura(ctlAssinatura ctl) { this.ctl = ctl; }

    /** Conduz o ator pelo cenário completo (fluxo principal + FA1, FA2 e FA3). */
    public void iniciar() {
        System.out.println("=== Minha Refeição - Assinar Plano de Refeições ===");

        // --- Autenticação (FA1) ---
        String celular = ler("Informe seu celular (somente números): ");
        informarCelular(celular);
        tentativas = 0;
        codigoValidado = false;
        while (tentativas < 3 && !codigoValidado) {
            informarCodigo(celular, ler("Informe o código recebido por SMS: "));
        }
        if (!codigoValidado) return;

        // --- Planos ---
        visualizarPlanos();
        while (true) {
            try {
                selecionarPlano(Integer.parseInt(ler("Escolha o plano (id): ")));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Plano inválido, tente novamente.");
            }
        }

        // --- Preferências ---
        String dietas = ler("Preferências (separe por vírgula; Enter = nenhuma). Ex.: Vegetariana, Vegana, Sem Gluten, Sem Lactose: ");
        List<String> tipos = new ArrayList<>();
        for (String d : dietas.split(",")) if (!d.isBlank()) tipos.add(d.trim());
        informarPreferencias(tipos);

        // --- Seleção de itens (FA2) ---
        System.out.println("\nSelecione Pratos Principais, Acompanhamentos e Sobremesas.");
        while (true) {
            int id = Integer.parseInt(ler("ID do item (0 para finalizar): "));
            if (id == 0) break;
            int qtd = Integer.parseInt(ler("Quantidade: "));
            selecionarItem(id, qtd);
        }
        visualizarResumo();

        // --- Endereços ---
        String entrega = lerEndereco("ENTREGA");
        String cobranca = ler("Cobrança igual à entrega? (s/n): ").equalsIgnoreCase("s") ? entrega : lerEndereco("COBRANÇA");
        confirmarEnderecos(entrega, cobranca);

        // --- Pagamento (FA3) ---
        pagamentoAprovado = false;
        while (!pagamentoAprovado) {
            informarPagamento(ler("Número do cartão (16 dígitos): "));
            if (!pagamentoAprovado) {
                if (!ler("Tentar novamente com outro cartão? (s/n): ").equalsIgnoreCase("s")) {
                    cancelarTentativaPagamento();
                    System.out.println("Contratação encerrada sem ativação.");
                    break;
                }
            }
        }
    }

    // ===== Estímulos do ator (mensagens "Ator -> View" do diagrama) =====
    public void informarCelular(String numeroCelular) {
        String codGerado = ctl.solicitarAutenticacao(numeroCelular);
        enviarSMSEExibirTelaCodigo(codGerado);
    }

    public void informarCodigo(String numeroCelular, String codigoInformado) {
        tentativas++;
        boolean valido = ctl.validarCodigo(numeroCelular, codigoInformado);
        if (valido) {
            codigoValidado = true;
            exibirConfirmacaoValidacao();
        } else {
            informarCodigoInvalidoOuSolicitarNovo(tentativas);
        }
    }

    public void visualizarPlanos() {
        apresentarPlanos(ctl.obterPlanosDisponiveis());
    }

    public void selecionarPlano(int idPlano) {
        ctl.contratarPlano(idPlano);
        exibirTelaPreferencias();
    }

    public void informarPreferencias(List<String> tiposDieta) {
        apresentarCardapioFiltrado(ctl.registrarPreferencias(tiposDieta));
    }

    public void selecionarItem(int idItem, int qtd) {
        if (ctl.adicionarItemPedido(idItem, qtd)) atualizarStatusItemOK();
        else informarLimiteExcedidoESolicitarAjuste();
    }

    public void visualizarResumo() {
        apresentarResumoESolicitarEndereco(ctl.obterResumoPedido());
    }

    public void confirmarEnderecos(String dadosEntrega, String dadosCobranca) {
        apresentarValorESolicitarPagamento(ctl.registrarEnderecos(dadosEntrega, dadosCobranca));
    }

    public void informarPagamento(String dadosCartao) {
        String confirmacao = ctl.processarPagamento(dadosCartao);
        if (confirmacao.equals("Pagamento Nao Autorizado")) {
            informarPagamentoRecusadoSolicitarNovoCartao();
        } else {
            pagamentoAprovado = true;
            apresentarConfirmacaoEProtocolo(confirmacao);
        }
    }

    public void cancelarTentativaPagamento() { ctl.encerrarSemAtivacao(); }

    // ===== Respostas ao ator (mensagens "View -> Ator" do diagrama) =====
    public void enviarSMSEExibirTelaCodigo(String codGerado) {
        System.out.println("[SMS simulado] Seu código de confirmação é: " + codGerado);
    }

    public void exibirConfirmacaoValidacao() { System.out.println("Celular validado com sucesso!\n"); }

    public void informarCodigoInvalidoOuSolicitarNovo(int numTentativas) {
        if (numTentativas >= 3) System.out.println("Código inválido. Limite de 3 tentativas excedido: validação encerrada. Solicite um novo código.");
        else System.out.println("Código inválido. Tentativas restantes: " + (3 - numTentativas));
    }

    public void apresentarPlanos(List<PlanoAssinatura> listaPlanos) {
        System.out.println("\nPlanos disponíveis:");
        for (PlanoAssinatura p : listaPlanos) System.out.println("  " + p.getDadosPlano());
    }

    public void exibirTelaPreferencias() { System.out.println("Plano contratado. Informe suas preferências alimentares."); }

    public void apresentarCardapioFiltrado(List<ItemCardapio> itensFiltrados) {
        System.out.println("\nCardápio compatível com suas preferências:");
        for (ItemCardapio i : itensFiltrados)
            System.out.printf("  [%d] %-22s %-16s (%s)%n", i.getIdItem(), i.getNome(), i.getCategoria(), i.getIngredientes());
    }

    public void atualizarStatusItemOK() { System.out.println("Item adicionado ao pedido."); }

    public void informarLimiteExcedidoESolicitarAjuste() {
        System.out.println("Item inválido ou quantidade incompatível com o limite do plano. Ajuste a quantidade.");
    }

    public void apresentarResumoESolicitarEndereco(String resumo) {
        System.out.println("\nResumo do pedido:\n" + resumo + "\nInforme os endereços.");
    }

    public void apresentarValorESolicitarPagamento(double valorTotal) {
        System.out.printf(java.util.Locale.forLanguageTag("pt-BR"), "%nValor total da assinatura: R$ %.2f%n", valorTotal);
    }

    public void apresentarConfirmacaoEProtocolo(String confirmacao) { System.out.println("\n" + confirmacao); }

    public void informarPagamentoRecusadoSolicitarNovoCartao() { System.out.println("Pagamento não autorizado pela operadora."); }

    // ===== utilitários de console =====
    private String ler(String msg) {
        System.out.print(msg);
        return in.hasNextLine() ? in.nextLine().trim() : "";
    }

    private String lerEndereco(String titulo) {
        System.out.println("Endereço de " + titulo + ":");
        String log = ler("  Logradouro: "), num = ler("  Número: "), comp = ler("  Complemento: "), cep = ler("  CEP: ");
        return log + "|" + num + "|" + comp + "|" + cep;
    }
}
