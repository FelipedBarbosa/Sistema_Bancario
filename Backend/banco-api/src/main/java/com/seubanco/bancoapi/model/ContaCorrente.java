package com.seubanco.bancoapi.model;

import jakarta.persistence.Entity;

@Entity
public class ContaCorrente extends Conta implements Tributavel {

    private double taxaSaque = 2.50;

    // OBRIGATÓRIO: Construtor vazio para o JPA
    public ContaCorrente() {
        super();
    }

    public ContaCorrente(int numero, String titular) {
        super(numero, titular);
    }

    @Override
    public boolean sacar(double valor) {
        double valorTotal = valor + taxaSaque;
        // Usamos o 'saldo' que é protected na classe pai (Conta)
        if (valor > 0 && saldo >= valorTotal) {
            saldo -= valorTotal;
            registrarOperacao("Saque CC (Taxa R$" + taxaSaque + "): -R$ " + valorTotal);
            return true;
        }
        return false;
    }

    @Override
    public double calcularTributo() {
        return this.getSaldo() * 0.01;
    }

    @Override
    public String toString() {
        return "[Corrente] Número: " + getNumero() + " | Titular: " + getTitular() + " | Saldo: R$ " + getSaldo();
    }
}