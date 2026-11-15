package entidade;

import java.util.List;

public class ItemCardapio {
    private final int idItem;
    private final String nome;
    private final String categoria;
    private final String ingredientes;
    private final String tipoDietaCompativel; // lista separada por vírgula

    public ItemCardapio(int idItem, String nome, String categoria, String ingredientes, String tipoDietaCompativel) {
        this.idItem = idItem; this.nome = nome; this.categoria = categoria;
        this.ingredientes = ingredientes; this.tipoDietaCompativel = tipoDietaCompativel;
    }

    /** Compatível quando atende a TODAS as preferências informadas (lista vazia = compatível). */
    public boolean verificarCompatibilidade(List<PreferenciaAlimentar> prefs) {
        String compat = "," + tipoDietaCompativel.toLowerCase().replace(", ", ",") + ",";
        for (PreferenciaAlimentar p : prefs)
            if (!compat.contains("," + p.getTipoDieta().trim().toLowerCase() + ",")) return false;
        return true;
    }

    public int getIdItem() { return idItem; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public String getIngredientes() { return ingredientes; }
    public String getTipoDietaCompativel() { return tipoDietaCompativel; }
}
