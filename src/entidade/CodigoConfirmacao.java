package entidade;

import java.time.LocalDateTime;
import java.util.Random;

public class CodigoConfirmacao {
    private String codigoGerado;
    private LocalDateTime dataHoraEnvio;
    private int tentativas;
    private boolean validado;
    private boolean encerrado;

    private CodigoConfirmacao() { }

    /** <<create>> criar() */
    public static CodigoConfirmacao criar() {
        return new CodigoConfirmacao();
    }

    public static CodigoConfirmacao restaurar(String cod, LocalDateTime dh, int tentativas,
                                              boolean validado, boolean encerrado) {
        CodigoConfirmacao c = new CodigoConfirmacao();
        c.codigoGerado = cod; c.dataHoraEnvio = dh; c.tentativas = tentativas;
        c.validado = validado; c.encerrado = encerrado;
        return c;
    }

    public String gerarCodigoSMS() {
        String fixo = System.getProperty("sms.fixo"); // apenas para demonstração/testes
        codigoGerado = fixo != null ? fixo : String.format("%06d", new Random().nextInt(1_000_000));
        dataHoraEnvio = LocalDateTime.now();
        tentativas = 0; validado = false; encerrado = false;
        return codigoGerado;
    }

    public boolean conferirCodigo(String codigoInformado) {
        if (encerrado || codigoGerado == null) return false;
        if (codigoGerado.equals(codigoInformado)) { validado = true; return true; }
        return false;
    }

    public int registrarTentativaInvalida() { return ++tentativas; }

    public void encerrarValidacao() { encerrado = true; }

    public String getCodigoGerado() { return codigoGerado; }
    public LocalDateTime getDataHoraEnvio() { return dataHoraEnvio; }
    public int getTentativas() { return tentativas; }
    public boolean isValidado() { return validado; }
    public boolean isEncerrado() { return encerrado; }
}
