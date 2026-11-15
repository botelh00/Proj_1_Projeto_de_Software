package colecao;

import entidade.ItemPedido;

import java.util.ArrayList;
import java.util.List;

/**
 * <<Collection>> Multiobjeto ColecaoItemPedido.
 */
public class ColecaoItemPedido {
    private List<ItemPedido> itens;

    private ColecaoItemPedido() {
        this.itens = new ArrayList<>();
    }

    /** Mensagem 23: <<create>> criar(): ColecaoItemPedido */
    public static ColecaoItemPedido criar() {
        return new ColecaoItemPedido();
    }

    /** Mensagem 41: addItem(item): void */
    public void addItem(ItemPedido item) {
        if (item != null) {
            this.itens.add(item);
        }
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
}
