package colecao;

import entidade.PlanoAssinatura;
import persistencia.ArquivoCSV;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Collection de planos, persistida em dados/planos.csv (criado com planos de exemplo na 1ª execução). */
public class ColPlanos {
    private final List<PlanoAssinatura> planosDisponiveis = new ArrayList<>();

    public ColPlanos() {
        ArquivoCSV arq = new ArquivoCSV("planos.csv");
        if (arq.ler().isEmpty()) {
            List<String[]> seed = new ArrayList<>();
            seed.add(new String[]{"1", "Plano Semanal 5", "5", "7", "99.90"});
            seed.add(new String[]{"2", "Plano Semanal 10", "10", "7", "179.90"});
            seed.add(new String[]{"3", "Plano Quinzenal 20", "20", "15", "329.90"});
            arq.gravar(seed);
        }
        for (String[] l : arq.ler())
            planosDisponiveis.add(new PlanoAssinatura(Integer.parseInt(l[0]), l[1],
                    Integer.parseInt(l[2]), Integer.parseInt(l[3]), Double.parseDouble(l[4].replace(',', '.'))));
    }

    public List<PlanoAssinatura> listarDisponiveis() { return new ArrayList<>(planosDisponiveis); }

    public PlanoAssinatura buscar(int idPlano) {
        for (PlanoAssinatura p : planosDisponiveis) if (p.getIdPlano() == idPlano) return p;
        return null;
    }
}
