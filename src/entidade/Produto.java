package entidade;

/**
 * <<Entidade>> Superclasse Produto do Cardápio.
 */
public class Produto {
    private int idProduto;
    private String nome;
    private String ingredientes;
    private String dietaCompativel;

    public Produto(int idProduto, String nome, String ingredientes, String dietaCompativel) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.dietaCompativel = dietaCompativel;
    }

    /** Mensagem 30: verificarDieta(pref): boolean */
    public boolean verificarDieta(PreferenciaAlimentar pref) {
        if (pref == null || pref.getTipoDieta() == null || pref.getTipoDieta().isBlank()) {
            return true;
        }
        String dietaSolicitada = pref.getTipoDieta().trim().toLowerCase();
        if (dietaSolicitada.equals("tradicional")) {
            return true;
        }
        if (dietaCompativel == null) {
            return false;
        }
        for (String d : dietaCompativel.split(",")) {
            if (d.trim().equalsIgnoreCase(dietaSolicitada)) {
                return true;
            }
        }
        return false;
    }

    /** Mensagem 38: getProduto(idProduto): Produto */
    public Produto getProduto(int idProduto) {
        return this.idProduto == idProduto ? this : null;
    }

    public String getCategoria() {
        return "Produto";
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getNome() {
        return nome;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getDietaCompativel() {
        return dietaCompativel;
    }

    @Override
    public String toString() {
        return String.format("[%d] %-24s | %-16s | Ingredientes: %-28s | Dietas: %s",
                idProduto, nome, getCategoria(), ingredientes, dietaCompativel);
    }
}
