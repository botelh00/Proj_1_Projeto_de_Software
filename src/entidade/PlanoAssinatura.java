package entidade;

import java.util.Locale;

public class PlanoAssinatura {
    private final int idPlano;
    private final String nomePlano;
    private final int limiteRefeicoes;
    private final int periodicidadeDias;
    private final double valorTotal;

    public PlanoAssinatura(int idPlano, String nomePlano, int limiteRefeicoes, int periodicidadeDias, double valorTotal) {
        this.idPlano = idPlano; this.nomePlano = nomePlano; this.limiteRefeicoes = limiteRefeicoes;
        this.periodicidadeDias = periodicidadeDias; this.valorTotal = valorTotal;
    }

    public String getDadosPlano() {
        return String.format(Locale.forLanguageTag("pt-BR"), "[%d] %s - %d refeições a cada %d dias - R$ %.2f",
                idPlano, nomePlano, limiteRefeicoes, periodicidadeDias, valorTotal);
    }

    public int getIdPlano() { return idPlano; }
    public String getNomePlano() { return nomePlano; }
    public int getLimiteRefeicoes() { return limiteRefeicoes; }
    public int getPeriodicidadeDias() { return periodicidadeDias; }
    public double getValorTotal() { return valorTotal; }
}
