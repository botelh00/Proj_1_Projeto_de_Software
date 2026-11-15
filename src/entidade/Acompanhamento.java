package entidade;

/**
 * <<Entidade>> Subclasse Acompanhamento.
 */
public class Acompanhamento extends Produto {
    private String porcao;

    public Acompanhamento(int idProduto, String nome, String ingredientes, String dietaCompativel, String porcao) {
        super(idProduto, nome, ingredientes, dietaCompativel);
        this.porcao = porcao;
    }

    public String getPorcao() {
        return porcao;
    }

    @Override
    public String getCategoria() {
        return "Acompanhamento (" + porcao + ")";
    }
}
