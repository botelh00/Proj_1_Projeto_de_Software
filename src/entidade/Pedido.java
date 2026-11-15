package entidade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int sequencia = 1;
    private final int idPedido;
    private final LocalDate dataSelecao;
    private String statusPedido;
    private final List<ItemPedido> itensSelecionados = new ArrayList<>();
    private EnderecoEntrega enderecoEntrega;

    private Pedido() {
        idPedido = sequencia++;
        dataSelecao = LocalDate.now();
        statusPedido = "Em Selecao";
    }

    /** <<create>> criar() */
    public static Pedido criar() { return new Pedido(); }

    public int totalItens() {
        int t = 0;
        for (ItemPedido i : itensSelecionados) t += i.getQuantidade();
        return t;
    }

    public boolean validarLimiteItens(int qtd, int limite) {
        return qtd > 0 && totalItens() + qtd <= limite;
    }

    public void addItem(ItemCardapio item, int qtd) {
        itensSelecionados.add(ItemPedido.criar(item, qtd));
    }

    public String calcularComposicao() {
        StringBuilder sb = new StringBuilder();
        for (ItemPedido i : itensSelecionados)
            sb.append(" - ").append(i.getQuantidade()).append("x ").append(i.getItem().getNome())
              .append(" (").append(i.getItem().getCategoria()).append(")\n");
        sb.append("Total de refeições: ").append(totalItens());
        return sb.toString();
    }

    public void vincularEnderecoEntrega(EnderecoEntrega endEntrega) { this.enderecoEntrega = endEntrega; }
    public void alterarStatus(String novoStatus) { this.statusPedido = novoStatus; }

    public int getIdPedido() { return idPedido; }
    public LocalDate getDataSelecao() { return dataSelecao; }
    public String getStatusPedido() { return statusPedido; }
    public List<ItemPedido> getItensSelecionados() { return itensSelecionados; }
    public EnderecoEntrega getEnderecoEntrega() { return enderecoEntrega; }
}
