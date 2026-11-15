package entidade;

public class ItemPedido {
    private final ItemCardapio item;
    private final int quantidade;

    private ItemPedido(ItemCardapio item, int qtd) { this.item = item; this.quantidade = qtd; }

    /** <<create>> criar(item, qtd) */
    public static ItemPedido criar(ItemCardapio item, int qtd) { return new ItemPedido(item, qtd); }

    public int getQuantidade() { return quantidade; }
    public ItemCardapio getItem() { return item; }
}
