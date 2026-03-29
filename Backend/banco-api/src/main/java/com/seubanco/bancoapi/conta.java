import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    private int numero;
    protected double saldo;
    private List<String> historico;

    public Conta(int numero) {
        this.numero = numero;
        this.saldo = 0.0;
        this.historico = new ArrayList<>();
        registrarOperacao("Conta criada.");
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    protected void registrarOperacao(String operacao) {
        historico.add(operacao);
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            registrarOperacao("Depósito: +R$ " + valor);
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            registrarOperacao("Saque: -R$ " + valor);
            return true;
        }
        return false;
    }

    public boolean transferir(Conta destino, double valor) {
        if (valor > 0 && saldo >= valor) {
            this.saldo -= valor;
            destino.depositarTransferencia(valor);

            this.registrarOperacao("Transferência enviada: -R$ " + valor + " para Conta " + destino.getNumero());
            destino.registrarOperacao("Transferência recebida: +R$ " + valor + " da Conta " + this.getNumero());

            return true;
        }
        return false;
    }

    protected void depositarTransferencia(double valor) {
        saldo += valor;
    }

    public void exibirHistorico() {
        System.out.println("\n--- Histórico da Conta " + numero + " ---");
        for (String op : historico) {
            System.out.println("- " + op);
        }
        System.out.println("------------------------------");
    }

    @Override
    public String toString() {
        return "Conta: " + numero + " | Saldo: R$ " + saldo;
    }
}