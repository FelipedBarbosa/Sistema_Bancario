package com.seubanco.bancoapi.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Requisito da Parte 2
@DiscriminatorColumn(name = "tipo_conta")
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // O banco precisa de um ID único (Primary Key)

    private int numero;
    protected double saldo;
    private String titular; // Adicionado conforme o exemplo do professor

    @ElementCollection // Forma simples de salvar uma lista de Strings no JPA/SQLite
    private List<String> historico = new ArrayList<>();

    // OBRIGATÓRIO PARA JPA: Construtor vazio
    public Conta() {
    }

    public Conta(int numero, String titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0.0;
        registrarOperacao("Conta criada para: " + titular);
    }

    // Getters e Setters
    public Long getId() { return id; }
    public int getNumero() { return numero; }
    public double getSaldo() { return saldo; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }

    // Suas regras de negócio (sacar, depositar, transferir) continuam iguais!
    protected void registrarOperacao(String operacao) {
        this.historico.add(operacao);
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

    // Na Parte 5 (Service), vamos mover a lógica de transferência para lá,
    // mas por enquanto pode manter aqui para não quebrar seu código.
    public void depositarTransferencia(double valor) {
        this.saldo += valor;
    }

    @Override
    public String toString() {
        return "Conta: " + numero + " | Titular: " + titular + " | Saldo: R$ " + saldo;
    }
}