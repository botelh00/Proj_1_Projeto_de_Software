package controle;

import colecao.ColAssinaturas;
import colecao.ColAssinantes;
import colecao.ColPlanos;
import entidade.*;
import fronteira.fronteiraOperadora;

import java.util.ArrayList;
import java.util.List;

/** Controller do caso de uso "Assinar Plano de Refeições". */
public class ctlAssinatura {
    private Assinante assinanteAtual;
    private Assinatura assinaturaAtual;
    private double valorTotalAtual;

    private final ColAssinantes colAssinantes = new ColAssinantes();
    private final ColPlanos colPlanos = new ColPlanos();
    private final ColAssinaturas colAssinaturas = new ColAssinaturas();
    private final Cardapio cardapio = new Cardapio();
    private final fronteiraOperadora operadora = new fronteiraOperadora();

    // ---- Cenário 1: autenticação por SMS ----
    public String solicitarAutenticacao(String numeroCelular) {
        Assinante a = colAssinantes.buscarPorCelular(numeroCelular);
        if (a == null) {
            a = Assinante.criar(numeroCelular);
            colAssinantes.adicionar(a);
        }
        assinanteAtual = a;
        String codGerado = a.gerarCodigoConfirmacao();
        colAssinantes.persistir();
        return codGerado;
    }

    public boolean validarCodigo(String numeroCelular, String codigoInformado) {
        Assinante a = colAssinantes.buscarPorCelular(numeroCelular);
        if (a == null) return false;
        boolean valido = a.validarCodigoSMS(codigoInformado);
        if (valido) assinanteAtual = a;
        colAssinantes.persistir();
        return valido;
    }

    // ---- Cenário 2: planos e preferências ----
    public List<PlanoAssinatura> obterPlanosDisponiveis() {
        return colPlanos.listarDisponiveis();
    }

    public void contratarPlano(int idPlano) {
        PlanoAssinatura plano = colPlanos.buscar(idPlano);
        if (plano == null) throw new IllegalArgumentException("Plano inexistente: " + idPlano);
        assinaturaAtual = Assinatura.criar(assinanteAtual, plano);
        colAssinaturas.salvar(assinaturaAtual);
    }

    public List<ItemCardapio> registrarPreferencias(List<String> tiposDieta) {
        for (String tipo : tiposDieta) assinanteAtual.adicionarPreferencia(tipo);
        colAssinantes.persistir();
        List<PreferenciaAlimentar> prefs = assinanteAtual.getPreferencias();
        return cardapio.filtrarCardapio(prefs);
    }

    // ---- Cenário 3: montagem do pedido e endereços ----
    public boolean adicionarItemPedido(int idItem, int qtd) {
        ItemCardapio item = cardapio.buscarItem(idItem);
        if (item == null) return false;
        boolean ok = assinaturaAtual.incluirItemNoPedido(item, qtd);
        if (ok) colAssinaturas.salvar(assinaturaAtual);
        return ok;
    }

    public String obterResumoPedido() {
        return assinaturaAtual.obterComposicaoPedido();
    }

    public double registrarEnderecos(String dadosEntrega, String dadosCobranca) {
        EnderecoEntrega endEntrega = assinanteAtual.cadastrarEnderecos(dadosEntrega, dadosCobranca);
        valorTotalAtual = assinaturaAtual.prepararCheckout(endEntrega);
        colAssinantes.persistir();
        colAssinaturas.salvar(assinaturaAtual);
        return valorTotalAtual;
    }

    // ---- Cenário 4: pagamento ----
    public String processarPagamento(String dadosCartao) {
        Pagamento pag = assinaturaAtual.criarTentativaPagamento(dadosCartao);
        String codAut = operadora.solicitarAutorizacao(dadosCartao, valorTotalAtual);
        boolean aprovado = assinaturaAtual.registrarResultadoPagamento(pag, codAut);
        String retorno;
        if (aprovado) {
            retorno = assinaturaAtual.ativarAssinatura();
        } else {
            assinaturaAtual.manterAguardandoPagamento();
            retorno = "Pagamento Nao Autorizado";
        }
        colAssinaturas.salvar(assinaturaAtual);
        return retorno;
    }

    public void encerrarSemAtivacao() {
        assinaturaAtual.alterarStatus("Encerrada Sem Ativacao");
        colAssinaturas.salvar(assinaturaAtual);
    }
}
