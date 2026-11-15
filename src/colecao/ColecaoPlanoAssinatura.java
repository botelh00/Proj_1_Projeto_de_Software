package colecao;

import entidade.PlanoAssinatura;

import java.util.ArrayList;
import java.util.List;

/**
 * <<Collection>> Multiobjeto ColecaoPlanoAssinatura.
 */
public class ColecaoPlanoAssinatura {
    private List<PlanoAssinatura> planos;

    public ColecaoPlanoAssinatura() {
        this.planos = new ArrayList<>();
        this.planos.add(new PlanoAssinatura(1, "Plano Semanal", 5, 7, 89.0));
        this.planos.add(new PlanoAssinatura(2, "Pacote 15 Dias", 15, 15, 240.0));
        this.planos.add(new PlanoAssinatura(3, "Plano Mensal", 20, 30, 320.0));
    }

    /** Mensagem 15: obterPlanosDisponiveis(): List<PlanoAssinatura> */
    public List<PlanoAssinatura> obterPlanosDisponiveis() {
        List<PlanoAssinatura> disponiveis = new ArrayList<>();
        for (PlanoAssinatura p : planos) {
            // Mensagem 16: getDadosPlano(): String
            p.getDadosPlano();
            disponiveis.add(p);
        }
        return disponiveis;
    }

    /** Mensagem 19: busca(idPlano): PlanoAssinatura */
    public PlanoAssinatura busca(int idPlano) {
        for (PlanoAssinatura p : planos) {
            // Mensagem 20: getPlano(idPlano): PlanoAssinatura
            PlanoAssinatura encontrado = p.getPlano(idPlano);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }
}
