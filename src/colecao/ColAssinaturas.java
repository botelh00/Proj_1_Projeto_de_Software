package colecao;

import entidade.*;
import persistencia.ArquivoCSV;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Persistência das assinaturas (extensão do modelo, necessária para guardar o resultado do cenário):
 * dados/assinaturas.csv, dados/itens_pedido.csv e dados/pagamentos.csv.
 */
public class ColAssinaturas {
    private final ArquivoCSV arqAssinaturas = new ArquivoCSV("assinaturas.csv");
    private final ArquivoCSV arqItens = new ArquivoCSV("itens_pedido.csv");
    private final ArquivoCSV arqPagamentos = new ArquivoCSV("pagamentos.csv");

    public void salvar(Assinatura a) {
        Pedido ped = a.getPedidos().get(a.getPedidos().size() - 1);
        String id = a.getIdAssinatura();

        List<String[]> linhaAss = new ArrayList<>();
        linhaAss.add(new String[]{id, a.getAssinante().getNumeroCelular(),
                String.valueOf(a.getPlano().getIdPlano()), ArquivoCSV.campo(a.getStatus()),
                ArquivoCSV.campo(a.getNumeroProtocolo()), ArquivoCSV.campo(a.getDataInicio()),
                ArquivoCSV.campo(a.getPrevisaoPrimeiraEntrega()), String.valueOf(ped.getIdPedido()),
                ArquivoCSV.campo(ped.getStatusPedido()),
                ped.getEnderecoEntrega() == null ? "" : ArquivoCSV.campo(ped.getEnderecoEntrega().toCsv())});
        arqAssinaturas.substituir(0, id, linhaAss);

        List<String[]> itens = new ArrayList<>();
        for (ItemPedido i : ped.getItensSelecionados())
            itens.add(new String[]{id, String.valueOf(i.getItem().getIdItem()),
                    ArquivoCSV.campo(i.getItem().getNome()), String.valueOf(i.getQuantidade())});
        arqItens.substituir(0, id, itens);

        List<String[]> pags = new ArrayList<>();
        for (Pagamento p : a.getHistoricoPagamentos())
            pags.add(new String[]{id, String.valueOf(p.getIdPagamento()), p.getDataTransacao().toString(),
                    String.format(Locale.US, "%.2f", p.getValorCobrado()), p.getUltimosDigitosCartao(),
                    ArquivoCSV.campo(p.getCodigoAutorizacao()), p.getStatusPagamento()});
        arqPagamentos.substituir(0, id, pags);
    }
}
