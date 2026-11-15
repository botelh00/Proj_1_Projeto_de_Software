package fronteira;

import entidade.OperadoraCartaoCredito;

/** Fronteira com o sistema externo da operadora de cartão (gateway simulado). */
public class fronteiraOperadora {
    private final String endpointGateway = "https://gateway.operadora.simulado/autorizar";
    private final OperadoraCartaoCredito operadora = new OperadoraCartaoCredito();

    public String solicitarAutorizacao(String dadosCartao, double valorTotal) {
        return enviarTransacao(dadosCartao, valorTotal);
    }

    public String enviarTransacao(String dadosCartao, double valorTotal) {
        return operadora.enviarTransacao(dadosCartao, valorTotal);
    }

    public String getEndpointGateway() { return endpointGateway; }
}
