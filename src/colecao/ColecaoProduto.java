package colecao;

import entidade.Acompanhamento;
import entidade.PratoPrincipal;
import entidade.PreferenciaAlimentar;
import entidade.Produto;
import entidade.Sobremesa;

import java.util.ArrayList;
import java.util.List;

/**
 * <<Collection>> Multiobjeto ColecaoProduto.
 */
public class ColecaoProduto {
    private List<Produto> produtos;

    public ColecaoProduto() {
        this.produtos = new ArrayList<>();
        // Pratos Principais
        produtos.add(new PratoPrincipal(1, "Frango Grelhado Ervas", "Peito de frango, azeite, ervas", "Tradicional, Sem lactose", 250));
        produtos.add(new PratoPrincipal(2, "Strogonoff de Carne", "Iscas bovinas, creme de leite, champignon", "Tradicional", 300));
        produtos.add(new PratoPrincipal(3, "Lasanha de Berinjela", "Berinjela, molho de tomate, queijo minas", "Tradicional, Vegetariana", 320));
        produtos.add(new PratoPrincipal(4, "Moqueca de Palmito", "Palmito pupunha, leite de coco, pimentoes", "Tradicional, Vegetariana, Sem lactose", 280));
        // Acompanhamentos
        produtos.add(new Acompanhamento(5, "Arroz Integral com Brocolis", "Arroz integral, brocolis no vapor, alho", "Tradicional, Vegetariana, Sem lactose", "150g"));
        produtos.add(new Acompanhamento(6, "Pure de Batata Cremoso", "Batata inglesa, manteiga, leite integral", "Tradicional, Vegetariana", "150g"));
        produtos.add(new Acompanhamento(7, "Mix de Legumes Assados", "Abobrinha, cenoura, batata-doce, alecrim", "Tradicional, Vegetariana, Sem lactose", "180g"));
        // Sobremesas
        produtos.add(new Sobremesa(8, "Mousse de Maracuja", "Polpa de maracuja, creme de leite", "Tradicional, Vegetariana", true));
        produtos.add(new Sobremesa(9, "Salada de Frutas Frescas", "Manga, morango, kiwi, laranja", "Tradicional, Vegetariana, Sem lactose", false));
        produtos.add(new Sobremesa(10, "Chia Pudding com Frutas Vermelhas", "Semente de chia, leite de amendoas, morango", "Tradicional, Vegetariana, Sem lactose", false));
    }

    /** Mensagem 29: buscarCompativeis(pref): List<Produto> */
    public List<Produto> buscarCompativeis(PreferenciaAlimentar pref) {
        List<Produto> compativeis = new ArrayList<>();
        for (Produto p : produtos) {
            // Mensagem 30: verificarDieta(pref): boolean
            if (p.verificarDieta(pref)) {
                compativeis.add(p);
            }
        }
        return compativeis;
    }

    /** Mensagem 37: busca(idProduto): Produto */
    public Produto busca(int idProduto) {
        for (Produto p : produtos) {
            // Mensagem 38: getProduto(idProduto): Produto
            Produto encontrado = p.getProduto(idProduto);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }
}
