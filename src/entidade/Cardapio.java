package entidade;

import persistencia.ArquivoCSV;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Cardápio da semana, carregado de dados/cardapio.csv (criado com itens de exemplo na 1ª execução). */
public class Cardapio {
    private final int idCardapio = 1;
    private final LocalDate semanaReferencia = LocalDate.now();
    private final boolean atualizado = true;
    private final List<ItemCardapio> itensDisponiveis = new ArrayList<>();

    public Cardapio() {
        ArquivoCSV arq = new ArquivoCSV("cardapio.csv");
        if (arq.ler().isEmpty()) {
            List<String[]> seed = new ArrayList<>();
            seed.add(new String[]{"1", "Frango Grelhado", "Prato Principal", "frango, temperos", "Sem Gluten,Sem Lactose"});
            seed.add(new String[]{"2", "Strogonoff de Carne", "Prato Principal", "carne, creme de leite", "Sem Gluten"});
            seed.add(new String[]{"3", "Lasanha de Berinjela", "Prato Principal", "berinjela, queijo", "Vegetariana"});
            seed.add(new String[]{"4", "Moqueca de Palmito", "Prato Principal", "palmito, leite de coco", "Vegetariana,Vegana,Sem Gluten,Sem Lactose"});
            seed.add(new String[]{"5", "Arroz Integral", "Acompanhamento", "arroz integral", "Vegetariana,Vegana,Sem Gluten,Sem Lactose"});
            seed.add(new String[]{"6", "Pure de Batata", "Acompanhamento", "batata, leite", "Vegetariana,Sem Gluten"});
            seed.add(new String[]{"7", "Legumes Assados", "Acompanhamento", "legumes variados", "Vegetariana,Vegana,Sem Gluten,Sem Lactose"});
            seed.add(new String[]{"8", "Mousse de Maracuja", "Sobremesa", "maracuja, creme de leite", "Vegetariana,Sem Gluten"});
            seed.add(new String[]{"9", "Salada de Frutas", "Sobremesa", "frutas da estacao", "Vegetariana,Vegana,Sem Gluten,Sem Lactose"});
            seed.add(new String[]{"10", "Pudim", "Sobremesa", "leite condensado, ovos", "Vegetariana,Sem Gluten"});
            arq.gravar(seed);
        }
        for (String[] l : arq.ler())
            itensDisponiveis.add(new ItemCardapio(Integer.parseInt(l[0]), l[1], l[2], l[3], l[4]));
    }

    public List<ItemCardapio> filtrarCardapio(List<PreferenciaAlimentar> prefs) {
        List<ItemCardapio> filtrados = new ArrayList<>();
        for (ItemCardapio i : itensDisponiveis)
            if (i.verificarCompatibilidade(prefs)) filtrados.add(i);
        return filtrados;
    }

    public ItemCardapio buscarItem(int idItem) {
        for (ItemCardapio i : itensDisponiveis) if (i.getIdItem() == idItem) return i;
        return null;
    }

    public List<ItemCardapio> getItensDisponiveis() { return itensDisponiveis; }
}
