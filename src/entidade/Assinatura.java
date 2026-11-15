package entidade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Assinatura {
    private final String idAssinatura = UUID.randomUUID().toString().substring(0, 8); // chave p/ persistência
    private String numeroProtocolo;
    private LocalDate dataInicio;
    private LocalDate previsaoPrimeiraEntrega;
    private String status;
    private final List<Pagamento> historicoPagamentos = new ArrayList<>();
    private final Assinante assinante;
    private final PlanoAssinatura plano;
    private final List<Pedido> pedidos = new ArrayList<>();

    private Assinatura(Assinante assinante, PlanoAssinatura plano) {
        this.assinante = assinante;
        this.plano = plano;
        this.status = "Em Contratacao";
        pedidos.add(Pedido.criar());                 // <<create>> criar(): Pedido
        assinante.vincularAssinatura(this);          // vincularAssinatura(assinatura)
    }

    /** <<create>> criar(assinante, plano) */
    public static Assinatura criar(Assinante assinante, PlanoAssinatura plano) {
        return new Assinatura(assinante, plano);
    }

    private Pedido pedidoAtual() { return pedidos.get(pedidos.size() - 1); }

    public boolean incluirItemNoPedido(ItemCardapio item, int qtd) {
        int limite = plano.getLimiteRefeicoes();
        boolean valido = pedidoAtual().validarLimiteItens(qtd, limite);
        if (valido) pedidoAtual().addItem(item, qtd);
        return valido;
    }

    public String obterComposicaoPedido() { return pedidoAtual().calcularComposicao(); }

    public double prepararCheckout(EnderecoEntrega endEntrega) {
        pedidoAtual().vincularEnderecoEntrega(endEntrega);
        alterarStatus("Aguardando Pagamento");
        return plano.getValorTotal();
    }

    public void alterarStatus(String novoStatus) { this.status = novoStatus; }

    public Pagamento criarTentativaPagamento(String dadosCartao) {
        Pagamento pag = Pagamento.criar(plano.getValorTotal(), dadosCartao);
        historicoPagamentos.add(pag);
        return pag;
    }

    public boolean registrarResultadoPagamento(Pagamento pag, String codAut) {
        return pag.validarAutorizacao(codAut);
    }

    public String ativarAssinatura() {
        alterarStatus("Ativa");
        pedidoAtual().alterarStatus("Aprovado");
        gerarNumeroProtocolo();
        calcularPrevisaoPrimeiraEntrega();
        dataInicio = LocalDate.now();
        return "Assinatura ativada! Plano: " + plano.getNomePlano()
                + " | Protocolo: " + numeroProtocolo
                + " | Previsão da 1ª entrega: " + previsaoPrimeiraEntrega.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String gerarNumeroProtocolo() {
        numeroProtocolo = "MR" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
                + String.format("%04d", new Random().nextInt(10000));
        return numeroProtocolo;
    }

    public LocalDate calcularPrevisaoPrimeiraEntrega() {
        previsaoPrimeiraEntrega = LocalDate.now().plusDays(3);
        return previsaoPrimeiraEntrega;
    }

    public void manterAguardandoPagamento() { alterarStatus("Aguardando Pagamento"); }

    public String getIdAssinatura() { return idAssinatura; }
    public String getNumeroProtocolo() { return numeroProtocolo; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getPrevisaoPrimeiraEntrega() { return previsaoPrimeiraEntrega; }
    public String getStatus() { return status; }
    public List<Pagamento> getHistoricoPagamentos() { return historicoPagamentos; }
    public Assinante getAssinante() { return assinante; }
    public PlanoAssinatura getPlano() { return plano; }
    public List<Pedido> getPedidos() { return pedidos; }
}
