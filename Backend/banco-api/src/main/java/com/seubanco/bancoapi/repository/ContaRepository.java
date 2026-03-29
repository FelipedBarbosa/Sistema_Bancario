package com.seubanco.bancoapi.repository;
import com.seubanco.bancoapi.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    // O professor pediu esse método específico na Parte 4:
    Conta findByNumero(int numero);
}