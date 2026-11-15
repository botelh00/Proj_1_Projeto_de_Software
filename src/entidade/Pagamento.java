package entidade;

import java.util.Date;

/**
 * <<Entidade>> Pagamento.
 */
public class Pagamento {
    private Date dataTransacao;
    private double valorCobrado;
    private String dadosCartao;
    private String statusPagamento;

    private Pagamento(double valor, String dadosCartao) {
        this.dataTransacao = new Date();
        this.valorCobrado = valor;
        this.dadosCartao = dadosCartao;
        this.statusPagamento = "Aprovado";
    }

    /** Mensagem 61: <<create>> criar(valor, dadosCartao): Pagamento */
    public static Pagamento criar(double valor, String dadosCartao) {
        return new Pagamento(valor, dadosCartao);
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public double getValorCobrado() {
        return valorCobrado;
    }

    public String getDadosCartao() {
        return dadosCartao;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }
}
