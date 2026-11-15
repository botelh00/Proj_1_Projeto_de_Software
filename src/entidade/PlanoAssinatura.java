package entidade;

import java.util.Locale;

/**
 * <<Entidade>> Plano de Assinatura.
 */
public class PlanoAssinatura {
    private int idPlano;
    private String nomePlano;
    private int limiteRefeicoes;
    private int periodicidadeDias;
    private double valorTotal;

    public PlanoAssinatura(int idPlano, String nomePlano, int limiteRefeicoes, int periodicidadeDias, double valorTotal) {
        this.idPlano = idPlano;
        this.nomePlano = nomePlano;
        this.limiteRefeicoes = limiteRefeicoes;
        this.periodicidadeDias = periodicidadeDias;
        this.valorTotal = valorTotal;
    }

    /** Mensagem 16: getDadosPlano(): String */
    public String getDadosPlano() {
        return String.format(Locale.forLanguageTag("pt-BR"),
                "[%d] %-24s | Limite: %2d refeições | Ciclo: %2d dias | Valor: R$ %.2f",
                idPlano, nomePlano, limiteRefeicoes, periodicidadeDias, valorTotal);
    }

    /** Mensagem 20: getPlano(idPlano): PlanoAssinatura */
    public PlanoAssinatura getPlano(int idPlano) {
        return this.idPlano == idPlano ? this : null;
    }

    /** Mensagem 34: getLimiteRefeicoes(): int */
    public int getLimiteRefeicoes() {
        return limiteRefeicoes;
    }

    /** Mensagem 57: getValorTotal(): double */
    public double getValorTotal() {
        return valorTotal;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public int getPeriodicidadeDias() {
        return periodicidadeDias;
    }
}
