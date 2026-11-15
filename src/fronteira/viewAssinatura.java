package fronteira;

import controle.ctlAssinatura;
import entidade.Assinatura;
import entidade.PlanoAssinatura;
import entidade.Produto;

import java.util.List;
import java.util.Locale;

/**
 * <<Fronteira>> Interface principal com o ator Assinante.
 */
public class viewAssinatura {
    private ctlAssinatura ctl;

    public viewAssinatura() {
        this.ctl = new ctlAssinatura();
    }

    public viewAssinatura(ctlAssinatura ctl) {
        this.ctl = ctl;
    }

    /** Mensagem 1: solicitarAssinatura(celular): void */
    public void solicitarAssinatura(String celular) {
        System.out.println("\n[1: solicitarAssinatura] Celular informado: " + celular);
        // Mensagem 2: iniciarAssinatura(celular): void
        ctl.iniciarAssinatura(celular);
    }

    /** Mensagem 7: autenticarAssinante(codigo): void */
    public void autenticarAssinante(String codigo) {
        System.out.println("\n[7: autenticarAssinante] Código informado pelo usuário: " + codigo);
        // Mensagem 8: validarAssinante(codigo): boolean
        boolean autenticado = ctl.validarAssinante(codigo);
        if (autenticado) {
            System.out.println("  -> [OK] Assinante autenticado com sucesso!");
        } else {
            int tentativas = ctl.getAssinante().getCodigoConfirmacao().getTentativas();
            System.out.println("  -> [ERRO - FA1] Código SMS inválido! Tentativa inválida registrada (" + tentativas + "/3).");
        }
    }

    /** Mensagem 13: consultarPlanos(): void */
    public void consultarPlanos() {
        System.out.println("\n[13: consultarPlanos] Consultando planos de assinatura disponíveis:");
        // Mensagem 14: listarPlanos(): List<PlanoAssinatura>
        List<PlanoAssinatura> planos = ctl.listarPlanos();
        for (PlanoAssinatura p : planos) {
            System.out.println("  " + p.getDadosPlano());
        }
    }

    /** Mensagem 17: contratarPlano(idPlano): void */
    public void contratarPlano(int idPlano) {
        System.out.println("\n[17: contratarPlano] Selecionando plano ID: " + idPlano);
        // Mensagem 18: selecionarPlano(idPlano): void
        ctl.selecionarPlano(idPlano);
        PlanoAssinatura plano = ctl.getAssinatura().getPlano();
        System.out.println("  -> [OK] Assinatura criada com o plano: " + plano.getNomePlano()
                + " (Limite: " + plano.getLimiteRefeicoes() + " refeições).");
    }

    /** Mensagem 24: definirPreferenciasAlimentares(tipoDieta): void */
    public void definirPreferenciasAlimentares(String tipoDieta) {
        System.out.println("\n[24: definirPreferenciasAlimentares] Preferência informada: " + tipoDieta);
        // Mensagem 25: registrarPreferencias(tipoDieta): List<Produto>
        List<Produto> produtosCompativeis = ctl.registrarPreferencias(tipoDieta);
        System.out.println("  -> Cardápio filtrado (" + produtosCompativeis.size() + " produtos compatíveis):");
        for (Produto prod : produtosCompativeis) {
            System.out.println("     " + prod);
        }
    }

    /** Mensagem 31: incluirRefeicao(idProduto, qtd): void */
    public void incluirRefeicao(int idProduto, int qtd) {
        System.out.println("\n[31: incluirRefeicao] Solicitando inclusão do produto ID=" + idProduto + ", Qtd=" + qtd);
        // Mensagem 32: adicionarProdutoPedido(idProduto, qtd): boolean
        boolean adicionado = ctl.adicionarProdutoPedido(idProduto, qtd);
        if (adicionado) {
            System.out.println("  -> [OK] Produto adicionado ao pedido! Total parcial: "
                    + ctl.getAssinatura().getPedido().getTotalRefeicoes() + "/"
                    + ctl.getAssinatura().getPlano().getLimiteRefeicoes() + " refeições.");
        } else {
            System.out.println("  -> [BLOQUEADO - FA2] Quantidade excede o limite de "
                    + ctl.getAssinatura().getPlano().getLimiteRefeicoes()
                    + " refeições do plano ou produto inválido. Ajuste a quantidade!");
        }
    }

    /** Mensagem 42: revisarPedido(): void */
    public void revisarPedido() {
        System.out.println("\n[42: revisarPedido] Exibindo composição atual do pedido:");
        // Mensagem 43: obterResumoPedido(): String
        String resumo = ctl.obterResumoPedido();
        System.out.println(resumo);
    }

    /** Mensagem 46: agendarEntrega(dadosEndereco): void */
    public void agendarEntrega(String dadosEndereco) {
        System.out.println("\n[46: agendarEntrega] Informando endereço de entrega: " + dadosEndereco);
        // Mensagem 47: registrarEntrega(dadosEndereco): void
        ctl.registrarEntrega(dadosEndereco);
        Assinatura ass = ctl.getAssinatura();
        System.out.printf(Locale.forLanguageTag("pt-BR"),
                "  -> [OK] Entrega configurada para: %s%n  -> Status da Assinatura: %s | Valor para pagamento: R$ %.2f%n",
                ass.getEntrega().getEndereco(), ass.getStatus(), ass.obterValorAssinatura());
    }

    /** Mensagem 54: efetuarPagamento(dadosCartao): void */
    public void efetuarPagamento(String dadosCartao) {
        System.out.println("\n[54: efetuarPagamento] Processando pagamento com cartão: " + dadosCartao);
        // Mensagem 55: processarPagamento(dadosCartao): boolean
        boolean aprovado = ctl.processarPagamento(dadosCartao);
        Assinatura ass = ctl.getAssinatura();
        if (aprovado) {
            System.out.println("  -> [APROVADO] Transação autorizada pela operadora!");
            System.out.println("  -> Status da Assinatura : " + ass.getStatus());
            System.out.println("  -> Status do Pedido     : " + ass.getPedido().getStatusPedido());
            System.out.println("  -> Status da Entrega    : " + ass.getEntrega().getStatusEntrega()
                    + " (Previsão 1ª Entrega: " + ass.getEntrega().getDataPrevisaoFormatada() + ")");
            System.out.println("  -> Número de Protocolo  : " + ass.getNumeroProtocolo());
            System.out.println("  -> Registro salvo em CSV: " + ctl.getArquivoCSV().getCaminho().toAbsolutePath());
        } else {
            System.out.println("  -> [RECUSADO - FA3] Transação negada pela operadora de cartão!");
            System.out.println("  -> Status da Assinatura mantido como: " + ass.getStatus());
            System.out.println("  -> Por favor, informe um novo cartão de crédito válido.");
        }
    }

    public ctlAssinatura getCtl() {
        return ctl;
    }
}
