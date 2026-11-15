package entidade;

import java.time.LocalDateTime;

public class Pagamento {
    private static int sequencia = 1;
    private final int idPagamento;
    private final LocalDateTime dataTransacao;
    private final double valorCobrado;
    private final String ultimosDigitosCartao;
    private String codigoAutorizacao;
    private String statusPagamento;

    private Pagamento(double valorTotal, String dadosCartao) {
        idPagamento = sequencia++;
        dataTransacao = LocalDateTime.now();
        valorCobrado = valorTotal;
        String d = dadosCartao.replaceAll("\\D", "");
        ultimosDigitosCartao = d.length() >= 4 ? d.substring(d.length() - 4) : d; // nunca guarda o número completo
        statusPagamento = "Pendente";
    }

    /** <<create>> criar(valorTotal, dadosCartao) */
    public static Pagamento criar(double valorTotal, String dadosCartao) {
        return new Pagamento(valorTotal, dadosCartao);
    }

    public boolean validarAutorizacao(String codAut) {
        if (codAut != null && !codAut.isBlank() && !codAut.startsWith("NEGADO")) {
            codigoAutorizacao = codAut;
            statusPagamento = "Aprovado";
            return true;
        }
        statusPagamento = "Recusado";
        return false;
    }

    public int getIdPagamento() { return idPagamento; }
    public LocalDateTime getDataTransacao() { return dataTransacao; }
    public double getValorCobrado() { return valorCobrado; }
    public String getUltimosDigitosCartao() { return ultimosDigitosCartao; }
    public String getCodigoAutorizacao() { return codigoAutorizacao; }
    public String getStatusPagamento() { return statusPagamento; }
}
