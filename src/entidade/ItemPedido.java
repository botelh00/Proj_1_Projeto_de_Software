package entidade;

/**
 * <<Entidade>> ItemPedido (associação entre Pedido e Produto).
 */
public class ItemPedido {
    private int quantidade;
    private Produto produto;

    private ItemPedido(Produto prod, int qtd) {
        this.produto = prod;
        this.quantidade = qtd;
    }

    /** Mensagem 40: <<create>> criar(prod, qtd): ItemPedido */
    public static ItemPedido criar(Produto prod, int qtd) {
        return new ItemPedido(prod, qtd);
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }
}
