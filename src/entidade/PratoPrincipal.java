package entidade;

/**
 * <<Entidade>> Subclasse PratoPrincipal.
 */
public class PratoPrincipal extends Produto {
    private int pesoGramas;

    public PratoPrincipal(int idProduto, String nome, String ingredientes, String dietaCompativel, int pesoGramas) {
        super(idProduto, nome, ingredientes, dietaCompativel);
        this.pesoGramas = pesoGramas;
    }

    public int getPesoGramas() {
        return pesoGramas;
    }

    @Override
    public String getCategoria() {
        return "Prato Principal (" + pesoGramas + "g)";
    }
}
