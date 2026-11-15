package entidade;

import colecao.ColecaoItemPedido;

import java.util.Date;

/**
 * <<Entidade>> Pedido vinculado à Assinatura.
 */
public class Pedido {
    private Date dataSelecao;
    private String statusPedido;
    private ColecaoItemPedido colecaoItens;
    private Entrega entrega;

    private Pedido() {
        this.dataSelecao = new Date();
        this.statusPedido = "Em Selecao";
        // Mensagem 23: <<create>> criar(): ColecaoItemPedido
        this.colecaoItens = ColecaoItemPedido.criar();
    }

    /** Mensagem 22: <<create>> criar(): Pedido */
    public static Pedido criar() {
        return new Pedido();
    }

    public int getTotalRefeicoes() {
        int soma = 0;
        for (ItemPedido item : colecaoItens.getItens()) {
            soma += item.getQuantidade();
        }
        return soma;
    }

    /** Mensagem 35: validarLimite(qtd, limite): boolean */
    public boolean validarLimite(int qtd, int limite) {
        return qtd > 0 && (getTotalRefeicoes() + qtd) <= limite;
    }

    /** Mensagem 39: addProduto(prod, qtd): void */
    public void addProduto(Produto prod, int qtd) {
        // Mensagem 40: <<create>> criar(prod, qtd): ItemPedido
        ItemPedido item = ItemPedido.criar(prod, qtd);
        // Mensagem 41: addItem(item): void
        colecaoItens.addItem(item);
    }

    /** Mensagem 45: calcularComposicao(): String */
    public String calcularComposicao() {
        StringBuilder sb = new StringBuilder();
        for (ItemPedido item : colecaoItens.getItens()) {
            sb.append(String.format("  - %dx %s [%s]%n",
                    item.getQuantidade(),
                    item.getProduto().getNome(),
                    item.getProduto().getCategoria()));
        }
        sb.append("  Total de refeições selecionadas: ").append(getTotalRefeicoes());
        return sb.toString();
    }

    /** Mensagem 52: vincularEntrega(entrega): void */
    public void vincularEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    /** Mensagem 63: aprovarPedido(): void */
    public void aprovarPedido() {
        this.statusPedido = "Aprovado";
    }

    public Date getDataSelecao() {
        return dataSelecao;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public ColecaoItemPedido getColecaoItens() {
        return colecaoItens;
    }

    public Entrega getEntrega() {
        return entrega;
    }
}
