package entidade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <<Entidade>> Entrega vinculada ao Pedido e à Assinatura.
 */
public class Entrega {
    private Date dataPrevisaoPrimeiraEntrega;
    private String statusEntrega;
    private Endereco endereco;

    private Entrega(Endereco endEntrega) {
        this.endereco = endEntrega;
        this.statusEntrega = "Pendente";
    }

    /** Mensagem 51: <<create>> criar(endEntrega): Entrega */
    public static Entrega criar(Endereco endEntrega) {
        return new Entrega(endEntrega);
    }

    /** Mensagem 64: programarPrimeiraEntrega(): void */
    public void programarPrimeiraEntrega() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3);
        this.dataPrevisaoPrimeiraEntrega = cal.getTime();
        this.statusEntrega = "Programada";
    }

    public Date getDataPrevisaoPrimeiraEntrega() {
        return dataPrevisaoPrimeiraEntrega;
    }

    public String getDataPrevisaoFormatada() {
        if (dataPrevisaoPrimeiraEntrega == null) {
            return "Nao programada";
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(dataPrevisaoPrimeiraEntrega);
    }

    public String getStatusEntrega() {
        return statusEntrega;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
