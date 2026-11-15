package entidade;

/**
 * <<Entidade>> CodigoConfirmacao para autenticação por SMS.
 */
public class CodigoConfirmacao {
    private String codigoSMS;
    private int tentativas;
    private boolean validado;

    private CodigoConfirmacao(String celular) {
        String fixo = System.getProperty("sms.codigo", "123456");
        this.codigoSMS = fixo;
        this.tentativas = 0;
        this.validado = false;
    }

    /** Mensagem 5: <<create>> criar(celular): CodigoConfirmacao */
    public static CodigoConfirmacao criar(String celular) {
        return new CodigoConfirmacao(celular);
    }

    /** Mensagem 6: enviarCodigoSMS(): void */
    public void enviarCodigoSMS() {
        System.out.println("  [SMS Enviado] Código de confirmação enviado para o celular: " + codigoSMS);
    }

    /** Mensagem 10: conferirCodigo(codigo): boolean */
    public boolean conferirCodigo(String codigo) {
        if (tentativas >= 3) {
            return false;
        }
        if (codigoSMS != null && codigoSMS.equals(codigo)) {
            this.validado = true;
            return true;
        }
        return false;
    }

    /** Mensagem 12: registrarTentativaInvalida(): void */
    public void registrarTentativaInvalida() {
        this.tentativas++;
    }

    public String getCodigoSMS() {
        return codigoSMS;
    }

    public int getTentativas() {
        return tentativas;
    }

    public boolean isValidado() {
        return validado;
    }
}
