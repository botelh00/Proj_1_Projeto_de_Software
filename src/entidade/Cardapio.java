package entidade;

import colecao.ColecaoProduto;

import java.util.Date;
import java.util.List;

/**
 * <<Entidade>> Cardápio semanal.
 */
public class Cardapio {
    private Date semanaReferencia;
    private ColecaoProduto colecaoProduto;

    public Cardapio() {
        this.semanaReferencia = new Date();
        this.colecaoProduto = new ColecaoProduto();
    }

    /** Mensagem 28: filtrarPorPreferencia(pref): List<Produto> */
    public List<Produto> filtrarPorPreferencia(PreferenciaAlimentar pref) {
        // Mensagem 29: buscarCompativeis(pref): List<Produto>
        return colecaoProduto.buscarCompativeis(pref);
    }

    /** Mensagem 36: busca(idProduto): Produto */
    public Produto busca(int idProduto) {
        // Mensagem 37: busca(idProduto): Produto
        return colecaoProduto.busca(idProduto);
    }

    public Date getSemanaReferencia() {
        return semanaReferencia;
    }

    public ColecaoProduto getColecaoProduto() {
        return colecaoProduto;
    }
}
