package com.seubanco.bancoapi.service;
import com.seubanco.bancoapi.model.Conta;
import com.seubanco.bancoapi.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    public List<Conta> listarTodas() {
        return repository.findAll();
    }

    public Conta salvar(Conta conta) {
        return repository.save(conta);
    }

    public Conta buscarPorNumero(int numero) {
        return repository.findByNumero(numero);
    }

    public void depositar(int numero, double valor) {
        Conta conta = repository.findByNumero(numero);
        if (conta != null) {
            conta.depositar(valor);
            repository.save(conta);
        }
    }

    public boolean sacar(int numero, double valor) {
        Conta conta = repository.findByNumero(numero);
        if (conta != null && conta.sacar(valor)) {
            repository.save(conta);
            return true;
        }
        return false;
    }

    public boolean transferir(int numOrigem, int numDestino, double valor) {
        Conta origem = repository.findByNumero(numOrigem);
        Conta destino = repository.findByNumero(numDestino);

        if (origem != null && destino != null && origem.sacar(valor)) {
            destino.depositar(valor);
            repository.save(origem);
            repository.save(destino);
            return true;
        }
        return false;
    }
}