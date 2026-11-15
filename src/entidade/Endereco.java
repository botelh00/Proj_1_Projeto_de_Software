package entidade;

/**
 * <<Entidade>> Endereco do Assinante/Entrega.
 */
public class Endereco {
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;

    public Endereco(String logradouro, String numero, String complemento, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
    }

    /** Mensagem 49: <<create>> criar(dadosEndereco): Endereco */
    public static Endereco criar(String dadosEndereco) {
        if (dadosEndereco == null || dadosEndereco.isBlank()) {
            return new Endereco("Nao informado", "S/N", "", "00000-000");
        }
        if (dadosEndereco.contains("|")) {
            String[] p = dadosEndereco.split("\\|", -1);
            return new Endereco(
                    p.length > 0 ? p[0].trim() : "",
                    p.length > 1 ? p[1].trim() : "S/N",
                    p.length > 2 ? p[2].trim() : "",
                    p.length > 3 ? p[3].trim() : "00000-000"
            );
        }
        String logradouro = dadosEndereco;
        String numero = "S/N";
        String complemento = "Apto/Casa";
        String cep = "00000-000";

        int idxCep = dadosEndereco.toUpperCase().indexOf("CEP");
        String parteEnd = idxCep >= 0 ? dadosEndereco.substring(0, idxCep).replace("-", "").trim() : dadosEndereco;
        if (idxCep >= 0) {
            cep = dadosEndereco.substring(idxCep + 3).replace(":", "").trim();
        }
        if (parteEnd.contains(",")) {
            String[] partes = parteEnd.split(",", 2);
            logradouro = partes[0].trim();
            numero = partes[1].trim();
        } else {
            logradouro = parteEnd.trim();
        }
        return new Endereco(logradouro, numero, complemento, cep);
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCep() {
        return cep;
    }

    @Override
    public String toString() {
        return logradouro + ", " + numero
                + (complemento != null && !complemento.isBlank() ? " (" + complemento + ")" : "")
                + " - CEP " + cep;
    }
}
