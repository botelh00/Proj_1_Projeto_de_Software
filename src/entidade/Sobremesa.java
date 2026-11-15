package entidade;

/**
 * <<Entidade>> Subclasse Sobremesa.
 */
public class Sobremesa extends Produto {
    private boolean contemAcucar;

    public Sobremesa(int idProduto, String nome, String ingredientes, String dietaCompativel, boolean contemAcucar) {
        super(idProduto, nome, ingredientes, dietaCompativel);
        this.contemAcucar = contemAcucar;
    }

    public boolean isContemAcucar() {
        return contemAcucar;
    }

    @Override
    public String getCategoria() {
        return "Sobremesa (" + (contemAcucar ? "Com açúcar" : "Zero açúcar") + ")";
    }
}
