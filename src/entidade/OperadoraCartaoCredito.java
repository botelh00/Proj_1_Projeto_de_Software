package entidade;

/**
 * <<Entidade>> OperadoraCartaoCredito (sistema autorizador simulado).
 */
public class OperadoraCartaoCredito {
    private String nomeOperadora;

    public OperadoraCartaoCredito() {
        this.nomeOperadora = "Operadora CredCard S.A.";
    }

    /**
     * Mensagem 59: autorizarTransacao(dadosCartao, valor): boolean
     * Retorna false para cartões inválidos, terminados em "0000" ou contendo "RECUSADO"/"INVALIDO" (teste FA3),
     * e true para cartões válidos.
     */
    public boolean autorizarTransacao(String dadosCartao, double valor) {
        if (dadosCartao == null || dadosCartao.isBlank() || valor <= 0) {
            return false;
        }
        String limpo = dadosCartao.trim().toUpperCase();
        if (limpo.contains("RECUSADO") || limpo.contains("INVALIDO") || limpo.endsWith("0000") || limpo.endsWith("0")) {
            return false;
        }
        String digitos = limpo.replaceAll("\\D", "");
        return digitos.length() >= 12;
    }

    public String getNomeOperadora() {
        return nomeOperadora;
    }
}
