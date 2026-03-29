package com.seubanco.bancoapi.model;

import jakarta.persistence.Entity;

@Entity
public class ContaPoupanca extends Conta {

    // OBRIGATÓRIO: Construtor vazio para o JPA
    public ContaPoupanca() {
        super();
    }

    public ContaPoupanca(int numero, String titular) {
        super(numero, titular);
    }

    public void renderJuros(double taxa) {
        double rendimento = getSaldo() * taxa;
        this.saldo += rendimento; // Acessando o saldo protected da classe Conta
        registrarOperacao("Rendimento Poupança: +R$ " + rendimento);
    }

    @Override
    public String toString() {
        return "[Poupança] Número: " + getNumero() + " | Titular: " + getTitular() + " | Saldo: R$ " + getSaldo();
    }
}