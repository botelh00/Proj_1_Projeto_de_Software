package entidade;

public class PreferenciaAlimentar {
    private static int sequencia = 1;
    private final int idPreferencia;
    private final String tipoDieta;

    private PreferenciaAlimentar(String tipoDieta) {
        this.idPreferencia = sequencia++;
        this.tipoDieta = tipoDieta;
    }

    /** <<create>> criar(tipoDieta) */
    public static PreferenciaAlimentar criar(String tipoDieta) {
        return new PreferenciaAlimentar(tipoDieta);
    }

    public int getIdPreferencia() { return idPreferencia; }
    public String getTipoDieta() { return tipoDieta; }
}
