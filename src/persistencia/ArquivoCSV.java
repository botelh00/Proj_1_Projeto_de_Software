package persistencia;

import entidade.Assinatura;
import entidade.ItemPedido;
import entidade.Pagamento;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Utilitário de persistência em arquivo CSV para registrar assinaturas confirmadas.
 */
public class ArquivoCSV {
    private static final String SEP = ";";
    private final Path caminho;

    public ArquivoCSV() {
        this("assinaturas.csv");
    }

    public ArquivoCSV(String nomeArquivo) {
        this.caminho = Paths.get("dados", nomeArquivo);
        try {
            Files.createDirectories(caminho.getParent());
            if (!Files.exists(caminho) || Files.size(caminho) == 0) {
                String cabecalho = String.join(SEP,
                        "Protocolo",
                        "DataInicio",
                        "StatusAssinatura",
                        "CelularAssinante",
                        "PreferenciaAlimentar",
                        "Plano",
                        "ValorTotal",
                        "ItensPedido",
                        "StatusPedido",
                        "EnderecoEntrega",
                        "PrevisaoPrimeiraEntrega",
                        "StatusEntrega",
                        "StatusPagamento"
                ) + System.lineSeparator();
                Files.writeString(caminho, cabecalho, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void salvarAssinatura(Assinatura assinatura) {
        if (assinatura == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataInicio = assinatura.getDataInicio() != null ? sdf.format(assinatura.getDataInicio()) : "";
        String celular = assinatura.getAssinante() != null ? assinatura.getAssinante().getNumeroCelular() : "";
        String dieta = (assinatura.getAssinante() != null && assinatura.getAssinante().getPreferenciaAlimentar() != null)
                ? assinatura.getAssinante().getPreferenciaAlimentar().getTipoDieta()
                : "Tradicional";
        String nomePlano = assinatura.getPlano() != null ? assinatura.getPlano().getNomePlano() : "";
        String valor = assinatura.getPlano() != null
                ? String.format(Locale.US, "%.2f", assinatura.getPlano().getValorTotal())
                : "0.00";

        List<String> descItens = new ArrayList<>();
        if (assinatura.getPedido() != null && assinatura.getPedido().getColecaoItens() != null) {
            for (ItemPedido item : assinatura.getPedido().getColecaoItens().getItens()) {
                descItens.add(item.getQuantidade() + "x " + item.getProduto().getNome());
            }
        }
        String itensStr = String.join(" | ", descItens);
        String statusPedido = assinatura.getPedido() != null ? assinatura.getPedido().getStatusPedido() : "";
        String enderecoStr = (assinatura.getEntrega() != null && assinatura.getEntrega().getEndereco() != null)
                ? assinatura.getEntrega().getEndereco().toString()
                : "";
        String previsaoEntrega = assinatura.getEntrega() != null
                ? assinatura.getEntrega().getDataPrevisaoFormatada()
                : "";
        String statusEntrega = assinatura.getEntrega() != null
                ? assinatura.getEntrega().getStatusEntrega()
                : "";
        String statusPagamento = "Pendente";
        if (assinatura.getPagamentos() != null && !assinatura.getPagamentos().isEmpty()) {
            Pagamento ult = assinatura.getPagamentos().get(assinatura.getPagamentos().size() - 1);
            statusPagamento = ult.getStatusPagamento();
        }

        String linha = String.join(SEP,
                limpar(assinatura.getNumeroProtocolo()),
                limpar(dataInicio),
                limpar(assinatura.getStatus()),
                limpar(celular),
                limpar(dieta),
                limpar(nomePlano),
                limpar(valor),
                limpar(itensStr),
                limpar(statusPedido),
                limpar(enderecoStr),
                limpar(previsaoEntrega),
                limpar(statusEntrega),
                limpar(statusPagamento)
        ) + System.lineSeparator();

        try {
            Files.writeString(caminho, linha, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Path getCaminho() {
        return caminho;
    }

    private String limpar(String valor) {
        return valor == null ? "" : valor.replace(SEP, ",").replace("\r", "").replace("\n", " ").trim();
    }
}
