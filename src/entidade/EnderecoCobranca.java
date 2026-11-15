package entidade;

/** Formato de entrada/saída: logradouro|numero|complemento|cep */
public class EnderecoCobranca {
    private final String logradouro;
    private final String numero;
    private final String complemento;
    private final String cep;

    private EnderecoCobranca(String[] p) {
        logradouro = p[0]; numero = p[1]; complemento = p[2]; cep = p[3];
    }

    /** <<create>> criar(dadosCobranca) */
    public static EnderecoCobranca criar(String dadosCobranca) {
        String[] p = dadosCobranca.split("\\|", -1);
        String[] f = new String[4];
        for (int i = 0; i < 4; i++) f[i] = i < p.length ? p[i].trim() : "";
        return new EnderecoCobranca(f);
    }

    public String toCsv() { return logradouro + "|" + numero + "|" + complemento + "|" + cep; }

    @Override public String toString() {
        return logradouro + ", " + numero + (complemento.isEmpty() ? "" : " - " + complemento) + " - CEP " + cep;
    }
}
