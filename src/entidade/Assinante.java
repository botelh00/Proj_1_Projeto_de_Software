package entidade;

/**
 * <<Entidade>> Assinante.
 */
public class Assinante {
    private String numeroCelular;
    private boolean autenticado;
    private CodigoConfirmacao codigoConfirmacao;
    private PreferenciaAlimentar preferenciaAlimentar;
    private Endereco endereco;

    private Assinante(String celular) {
        this.numeroCelular = celular;
        this.autenticado = false;
    }

    /** Mensagem 3: <<create>> criar(celular): Assinante */
    public static Assinante criar(String celular) {
        return new Assinante(celular);
    }

    /** Mensagem 4: gerarCodigoConfirmacao(): void */
    public void gerarCodigoConfirmacao() {
        // Mensagem 5: <<create>> criar(celular): CodigoConfirmacao
        this.codigoConfirmacao = CodigoConfirmacao.criar(this.numeroCelular);
        // Mensagem 6: enviarCodigoSMS(): void
        this.codigoConfirmacao.enviarCodigoSMS();
    }

    /** Mensagem 9: validarCodigo(codigo): boolean */
    public boolean validarCodigo(String codigo) {
        if (this.codigoConfirmacao == null) {
            return false;
        }
        // Mensagem 10: conferirCodigo(codigo): boolean
        boolean ok = this.codigoConfirmacao.conferirCodigo(codigo);
        if (ok) {
            // Mensagem 11: confirmarAutenticacao(): void
            this.confirmarAutenticacao();
        } else {
            // Mensagem 12: registrarTentativaInvalida(): void
            this.codigoConfirmacao.registrarTentativaInvalida();
        }
        return ok;
    }

    /** Mensagem 11: confirmarAutenticacao(): void */
    public void confirmarAutenticacao() {
        this.autenticado = true;
    }

    /** Mensagem 26: adicionarPreferencia(tipoDieta): PreferenciaAlimentar */
    public PreferenciaAlimentar adicionarPreferencia(String tipoDieta) {
        // Mensagem 27: <<create>> criar(tipoDieta): PreferenciaAlimentar
        this.preferenciaAlimentar = PreferenciaAlimentar.criar(tipoDieta);
        return this.preferenciaAlimentar;
    }

    /** Mensagem 48: cadastrarEndereco(dadosEndereco): Endereco */
    public Endereco cadastrarEndereco(String dadosEndereco) {
        // Mensagem 49: <<create>> criar(dadosEndereco): Endereco
        this.endereco = Endereco.criar(dadosEndereco);
        return this.endereco;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public CodigoConfirmacao getCodigoConfirmacao() {
        return codigoConfirmacao;
    }

    public PreferenciaAlimentar getPreferenciaAlimentar() {
        return preferenciaAlimentar;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
