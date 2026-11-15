package entidade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <<Entidade>> Assinatura coordenadora do pedido, entrega e ativação.
 */
public class Assinatura {
    private String numeroProtocolo;
    private Date dataInicio;
    private String status;
    private Assinante assinante;
    private PlanoAssinatura plano;
    private Cardapio cardapio;
    private Pedido pedido;
    private Entrega entrega;
    private List<Pagamento> pagamentos;

    private Assinatura(Assinante assinante, PlanoAssinatura plano) {
        this.assinante = assinante;
        this.plano = plano;
        this.status = "Em Contratacao";
        this.cardapio = new Cardapio();
        this.pagamentos = new ArrayList<>();
        // Mensagem 22: <<create>> criar(): Pedido
        this.pedido = Pedido.criar();
    }

    /** Mensagem 21: <<create>> criar(assinante, plano): Assinatura */
    public static Assinatura criar(Assinante assinante, PlanoAssinatura plano) {
        return new Assinatura(assinante, plano);
    }

    public void setCardapio(Cardapio cardapio) {
        if (cardapio != null) {
            this.cardapio = cardapio;
        }
    }

    /** Mensagem 33: adicionarProduto(idProduto, qtd): boolean */
    public boolean adicionarProduto(int idProduto, int qtd) {
        // Mensagem 34: getLimiteRefeicoes(): int
        int limite = plano.getLimiteRefeicoes();
        // Mensagem 35: validarLimite(qtd, limite): boolean
        boolean dentroDoLimite = pedido.validarLimite(qtd, limite);
        if (!dentroDoLimite) {
            return false;
        }
        // Mensagem 36: busca(idProduto): Produto
        Produto prod = cardapio.busca(idProduto);
        if (prod == null) {
            return false;
        }
        // Mensagem 39: addProduto(prod, qtd): void
        pedido.addProduto(prod, qtd);
        return true;
    }

    /** Mensagem 44: obterComposicaoPedido(): String */
    public String obterComposicaoPedido() {
        // Mensagem 45: calcularComposicao(): String
        return pedido.calcularComposicao();
    }

    /** Mensagem 50: configurarEntrega(endEntrega): void */
    public void configurarEntrega(Endereco endEntrega) {
        // Mensagem 51: <<create>> criar(endEntrega): Entrega
        this.entrega = Entrega.criar(endEntrega);
        // Mensagem 52: vincularEntrega(entrega): void
        this.pedido.vincularEntrega(this.entrega);
        // Mensagem 53: alterarStatus("Aguardando Pagamento"): void
        this.alterarStatus("Aguardando Pagamento");
    }

    /** Mensagem 53: alterarStatus(novoStatus): void */
    public void alterarStatus(String novoStatus) {
        this.status = novoStatus;
    }

    /** Mensagem 56: obterValorAssinatura(): double */
    public double obterValorAssinatura() {
        // Mensagem 57: getValorTotal(): double
        return plano.getValorTotal();
    }

    /** Mensagem 60: confirmarAssinatura(dadosCartao, valor): void */
    public void confirmarAssinatura(String dadosCartao, double valor) {
        // Mensagem 61: <<create>> criar(valor, dadosCartao): Pagamento
        Pagamento pag = Pagamento.criar(valor, dadosCartao);
        this.pagamentos.add(pag);
        // Mensagem 62: ativarAssinatura(): void
        this.ativarAssinatura();
        // Mensagem 63: aprovarPedido(): void
        this.pedido.aprovarPedido();
        // Mensagem 64: programarPrimeiraEntrega(): void
        if (this.entrega != null) {
            this.entrega.programarPrimeiraEntrega();
        }
    }

    /** Mensagem 62: ativarAssinatura(): void */
    public void ativarAssinatura() {
        this.status = "Ativa";
        this.dataInicio = new Date();
        String dataStr = new SimpleDateFormat("yyyyMMdd").format(this.dataInicio);
        this.numeroProtocolo = "MR-" + dataStr + "-" + String.format("%04d", new Random().nextInt(10000));
    }

    /** Mensagem 65: manterAguardandoPagamento(): void */
    public void manterAguardandoPagamento() {
        this.status = "Aguardando Pagamento";
    }

    public String getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public String getStatus() {
        return status;
    }

    public Assinante getAssinante() {
        return assinante;
    }

    public PlanoAssinatura getPlano() {
        return plano;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }
}
