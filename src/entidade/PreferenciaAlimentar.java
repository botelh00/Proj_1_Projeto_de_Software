package entidade;

/**
 * <<Entidade>> PreferenciaAlimentar.
 */
public class PreferenciaAlimentar {
    private String tipoDieta;

    private PreferenciaAlimentar(String tipoDieta) {
        this.tipoDieta = tipoDieta;
    }

    /** Mensagem 27: <<create>> criar(tipoDieta): PreferenciaAlimentar */
    public static PreferenciaAlimentar criar(String tipoDieta) {
        return new PreferenciaAlimentar(tipoDieta);
    }

    public String getTipoDieta() {
        return tipoDieta;
    }

    @Override
    public String toString() {
        return tipoDieta;
    }
}
