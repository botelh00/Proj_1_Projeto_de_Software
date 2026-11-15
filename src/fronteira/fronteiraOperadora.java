package fronteira;

import entidade.OperadoraCartaoCredito;

/**
 * <<Fronteira>> Fronteira de comunicação com o ator secundário Operadora de Cartão de Crédito.
 */
public class fronteiraOperadora {
    private OperadoraCartaoCredito operadora;

    public fronteiraOperadora() {
        this.operadora = new OperadoraCartaoCredito();
    }

    /** Mensagem 58: solicitarAutorizacao(dadosCartao, valor): boolean */
    public boolean solicitarAutorizacao(String dadosCartao, double valor) {
        // Mensagem 59: autorizarTransacao(dadosCartao, valor): boolean
        return operadora.autorizarTransacao(dadosCartao, valor);
    }

    public OperadoraCartaoCredito getOperadora() {
        return operadora;
    }
}
