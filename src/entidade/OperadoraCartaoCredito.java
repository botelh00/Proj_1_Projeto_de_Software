package entidade;

import java.util.Random;

/** Ator externo (simulado): cartões com 16 dígitos terminados em 0 são recusados. */
public class OperadoraCartaoCredito {
    private final String nomeOperadora = "OperadoraSimulada";

    public String enviarTransacao(String dadosCartao, double valorTotal) {
        String d = dadosCartao.replaceAll("\\D", "");
        if (d.length() == 16 && !d.endsWith("0"))
            return "AUT" + (100000 + new Random().nextInt(900000));
        return "NEGADO";
    }

    public String getNomeOperadora() { return nomeOperadora; }
}
