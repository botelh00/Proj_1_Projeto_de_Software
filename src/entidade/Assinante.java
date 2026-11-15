package entidade;

import java.util.ArrayList;
import java.util.List;

public class Assinante {
    private final String numeroCelular;
    private String nome = "";
    private boolean validado;
    private CodigoConfirmacao codigo;
    private final List<PreferenciaAlimentar> preferencias = new ArrayList<>();
    private final List<EnderecoEntrega> enderecosEntrega = new ArrayList<>();
    private final List<EnderecoCobranca> enderecosCobranca = new ArrayList<>();
    private final List<Assinatura> assinaturas = new ArrayList<>();

    private Assinante(String numeroCelular) { this.numeroCelular = numeroCelular; }

    /** <<create>> criar(numeroCelular) */
    public static Assinante criar(String numeroCelular) { return new Assinante(numeroCelular); }

    public static Assinante restaurar(String celular, String nome, boolean validado, CodigoConfirmacao cod) {
        Assinante a = new Assinante(celular);
        a.nome = nome; a.validado = validado; a.codigo = cod;
        return a;
    }

    public String gerarCodigoConfirmacao() {
        codigo = CodigoConfirmacao.criar();
        validado = false;
        return codigo.gerarCodigoSMS();
    }

    public boolean validarCodigoSMS(String codigoInformado) {
        boolean valido = codigo != null && codigo.conferirCodigo(codigoInformado);
        if (valido) {
            confirmarValidacao();
        } else if (codigo != null) {
            int numTentativas = codigo.registrarTentativaInvalida();
            if (numTentativas >= 3) codigo.encerrarValidacao();
        }
        return valido;
    }

    public void confirmarValidacao() { this.validado = true; }

    public void vincularAssinatura(Assinatura assinatura) { assinaturas.add(assinatura); }

    public void adicionarPreferencia(String tipoDieta) {
        for (PreferenciaAlimentar p : preferencias)
            if (p.getTipoDieta().equalsIgnoreCase(tipoDieta)) return;
        preferencias.add(PreferenciaAlimentar.criar(tipoDieta));
    }

    public List<PreferenciaAlimentar> getPreferencias() { return preferencias; }

    public EnderecoEntrega cadastrarEnderecos(String dadosEntrega, String dadosCobranca) {
        EnderecoEntrega ee = EnderecoEntrega.criar(dadosEntrega);
        EnderecoCobranca ec = EnderecoCobranca.criar(dadosCobranca);
        enderecosEntrega.add(ee);
        enderecosCobranca.add(ec);
        return ee;
    }

    public String getNumeroCelular() { return numeroCelular; }
    public String getNome() { return nome; }
    public boolean isValidado() { return validado; }
    public CodigoConfirmacao getCodigo() { return codigo; }
    public List<EnderecoEntrega> getEnderecosEntrega() { return enderecosEntrega; }
    public List<EnderecoCobranca> getEnderecosCobranca() { return enderecosCobranca; }
    public List<Assinatura> getAssinaturas() { return assinaturas; }
}
