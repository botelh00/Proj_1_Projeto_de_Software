package controle;

import colecao.ColecaoPlanoAssinatura;
import entidade.Assinante;
import entidade.Assinatura;
import entidade.Cardapio;
import entidade.Endereco;
import entidade.PlanoAssinatura;
import entidade.PreferenciaAlimentar;
import entidade.Produto;
import fronteira.fronteiraOperadora;
import persistencia.ArquivoCSV;

import java.util.List;

/**
 * <<Controller>> Controlador do caso de uso "Assinar Plano de Refeições".
 */
public class ctlAssinatura {
    private Assinante assinante;
    private Assinatura assinatura;
    private ColecaoPlanoAssinatura colecaoPlanos;
    private Cardapio cardapio;
    private fronteiraOperadora fronteiraOp;
    private ArquivoCSV arquivoCSV;

    public ctlAssinatura() {
        this.colecaoPlanos = new ColecaoPlanoAssinatura();
        this.cardapio = new Cardapio();
        this.fronteiraOp = new fronteiraOperadora();
        this.arquivoCSV = new ArquivoCSV("assinaturas.csv");
    }

    /** Mensagem 2: iniciarAssinatura(celular): void */
    public void iniciarAssinatura(String celular) {
        // Mensagem 3: <<create>> criar(celular): Assinante
        this.assinante = Assinante.criar(celular);
        // Mensagem 4: gerarCodigoConfirmacao(): void
        this.assinante.gerarCodigoConfirmacao();
    }

    /** Mensagem 8: validarAssinante(codigo): boolean */
    public boolean validarAssinante(String codigo) {
        // Mensagem 9: validarCodigo(codigo): boolean
        return this.assinante.validarCodigo(codigo);
    }

    /** Mensagem 14: listarPlanos(): List<PlanoAssinatura> */
    public List<PlanoAssinatura> listarPlanos() {
        // Mensagem 15: obterPlanosDisponiveis(): List<PlanoAssinatura>
        return this.colecaoPlanos.obterPlanosDisponiveis();
    }

    /** Mensagem 18: selecionarPlano(idPlano): void */
    public void selecionarPlano(int idPlano) {
        // Mensagem 19: busca(idPlano): PlanoAssinatura
        PlanoAssinatura plano = this.colecaoPlanos.busca(idPlano);
        if (plano == null) {
            throw new IllegalArgumentException("Plano inexistente para o ID: " + idPlano);
        }
        // Mensagem 21: <<create>> criar(assinante, plano): Assinatura
        this.assinatura = Assinatura.criar(this.assinante, plano);
        this.assinatura.setCardapio(this.cardapio);
    }

    /** Mensagem 25: registrarPreferencias(tipoDieta): List<Produto> */
    public List<Produto> registrarPreferencias(String tipoDieta) {
        // Mensagem 26: adicionarPreferencia(tipoDieta): PreferenciaAlimentar
        PreferenciaAlimentar pref = this.assinante.adicionarPreferencia(tipoDieta);
        // Mensagem 28: filtrarPorPreferencia(pref): List<Produto>
        return this.cardapio.filtrarPorPreferencia(pref);
    }

    /** Mensagem 32: adicionarProdutoPedido(idProduto, qtd): boolean */
    public boolean adicionarProdutoPedido(int idProduto, int qtd) {
        // Mensagem 33: adicionarProduto(idProduto, qtd): boolean
        return this.assinatura.adicionarProduto(idProduto, qtd);
    }

    /** Mensagem 43: obterResumoPedido(): String */
    public String obterResumoPedido() {
        // Mensagem 44: obterComposicaoPedido(): String
        return this.assinatura.obterComposicaoPedido();
    }

    /** Mensagem 47: registrarEntrega(dadosEndereco): void */
    public void registrarEntrega(String dadosEndereco) {
        // Mensagem 48: cadastrarEndereco(dadosEndereco): Endereco
        Endereco endEntrega = this.assinante.cadastrarEndereco(dadosEndereco);
        // Mensagem 50: configurarEntrega(endEntrega): void
        this.assinatura.configurarEntrega(endEntrega);
    }

    /** Mensagem 55: processarPagamento(dadosCartao): boolean */
    public boolean processarPagamento(String dadosCartao) {
        // Mensagem 56: obterValorAssinatura(): double
        double valor = this.assinatura.obterValorAssinatura();
        // Mensagem 58: solicitarAutorizacao(dadosCartao, valor): boolean
        boolean aut = this.fronteiraOp.solicitarAutorizacao(dadosCartao, valor);
        if (aut) {
            // Mensagem 60: confirmarAssinatura(dadosCartao, valor): void
            this.assinatura.confirmarAssinatura(dadosCartao, valor);
            this.arquivoCSV.salvarAssinatura(this.assinatura);
        } else {
            // Mensagem 65: manterAguardandoPagamento(): void
            this.assinatura.manterAguardandoPagamento();
        }
        return aut;
    }

    public Assinante getAssinante() {
        return assinante;
    }

    public Assinatura getAssinatura() {
        return assinatura;
    }

    public ArquivoCSV getArquivoCSV() {
        return arquivoCSV;
    }
}
