package persistencia;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/** Utilitário de persistência: lê/grava arquivos CSV (separador ';') na pasta ./dados. */
public class ArquivoCSV {
    private static final String SEP = ";";
    private final Path caminho;

    public ArquivoCSV(String nome) {
        caminho = Paths.get("dados", nome);
        try {
            Files.createDirectories(caminho.getParent());
            if (!Files.exists(caminho)) Files.createFile(caminho);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<String[]> ler() {
        try {
            List<String[]> linhas = new ArrayList<>();
            for (String l : Files.readAllLines(caminho, StandardCharsets.UTF_8))
                if (!l.isBlank()) linhas.add(l.split(SEP, -1));
            return linhas;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void gravar(List<String[]> linhas) {
        List<String> saida = new ArrayList<>();
        for (String[] l : linhas) saida.add(String.join(SEP, l));
        try {
            Files.write(caminho, saida, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /** Remove as linhas cuja coluna 'col' vale 'chave' e acrescenta as novas linhas. */
    public void substituir(int col, String chave, List<String[]> novas) {
        List<String[]> atuais = new ArrayList<>();
        for (String[] l : ler()) if (!(l.length > col && l[col].equals(chave))) atuais.add(l);
        atuais.addAll(novas);
        gravar(atuais);
    }

    public static String campo(Object o) {
        return o == null ? "" : o.toString().replace(";", ",").replace("\n", " ");
    }
}
