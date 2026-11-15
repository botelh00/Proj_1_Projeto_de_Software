import controle.ctlAssinatura;
import fronteira.viewAssinatura;

public class Main {
    public static void main(String[] args) {
        new viewAssinatura(new ctlAssinatura()).iniciar();
    }
}
