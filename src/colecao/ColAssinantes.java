package colecao;

import entidade.*;
import persistencia.ArquivoCSV;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Collection de assinantes. Persiste em dados/assinantes.csv, dados/preferencias.csv
 * e dados/enderecos.csv. Carrega tudo na inicialização e regrava em persistir().
 */
public class ColAssinantes {
    private final ArquivoCSV arqAssinantes = new ArquivoCSV("assinantes.csv");
    private final ArquivoCSV arqPrefs = new ArquivoCSV("preferencias.csv");
    private final ArquivoCSV arqEnderecos = new ArquivoCSV("enderecos.csv");
    private final List<Assinante> listaAssinantes = new ArrayList<>();

    public ColAssinantes() { carregar(); }

    public Assinante buscarPorCelular(String numeroCelular) {
        for (Assinante a : listaAssinantes)
            if (a.getNumeroCelular().equals(numeroCelular)) return a;
        return null;
    }

    public void adicionar(Assinante a) {
        listaAssinantes.add(a);
        persistir();
    }

    private void carregar() {
        for (String[] l : arqAssinantes.ler()) {
            boolean validado = Boolean.parseBoolean(l[2]);
            CodigoConfirmacao cod = l[3].isEmpty() ? null : CodigoConfirmacao.restaurar(
                    l[3], LocalDateTime.parse(l[4]), Integer.parseInt(l[5]), validado, Boolean.parseBoolean(l[6]));
            listaAssinantes.add(Assinante.restaurar(l[0], l[1], validado, cod));
        }
        for (String[] l : arqPrefs.ler()) {
            Assinante a = buscarPorCelular(l[0]);
            if (a != null) a.adicionarPreferencia(l[1]);
        }
        for (String[] l : arqEnderecos.ler()) {
            Assinante a = buscarPorCelular(l[0]);
            if (a == null) continue;
            if (l[1].equals("E")) a.getEnderecosEntrega().add(EnderecoEntrega.criar(l[2]));
            else a.getEnderecosCobranca().add(EnderecoCobranca.criar(l[2]));
        }
    }

    public void persistir() {
        List<String[]> as = new ArrayList<>(), ps = new ArrayList<>(), es = new ArrayList<>();
        for (Assinante a : listaAssinantes) {
            CodigoConfirmacao c = a.getCodigo();
            as.add(new String[]{ArquivoCSV.campo(a.getNumeroCelular()), ArquivoCSV.campo(a.getNome()),
                    String.valueOf(a.isValidado()),
                    c == null ? "" : c.getCodigoGerado(),
                    c == null || c.getDataHoraEnvio() == null ? "" : c.getDataHoraEnvio().toString(),
                    c == null ? "0" : String.valueOf(c.getTentativas()),
                    c == null ? "false" : String.valueOf(c.isEncerrado())});
            for (PreferenciaAlimentar p : a.getPreferencias())
                ps.add(new String[]{a.getNumeroCelular(), ArquivoCSV.campo(p.getTipoDieta())});
            for (EnderecoEntrega e : a.getEnderecosEntrega())
                es.add(new String[]{a.getNumeroCelular(), "E", ArquivoCSV.campo(e.toCsv())});
            for (EnderecoCobranca e : a.getEnderecosCobranca())
                es.add(new String[]{a.getNumeroCelular(), "C", ArquivoCSV.campo(e.toCsv())});
        }
        arqAssinantes.gravar(as);
        arqPrefs.gravar(ps);
        arqEnderecos.gravar(es);
    }
}
