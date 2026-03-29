package com.seubanco.bancoapi.controller;

import com.seubanco.bancoapi.dto.TransferenciaDTO;
import com.seubanco.bancoapi.model.Conta;
import com.seubanco.bancoapi.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas") // Todas as URLs vão começar com /contas
public class ContaController {

    @Autowired
    private ContaService service;

    // GET /contas - Listar todas
    @GetMapping
    public List<Conta> listar() {
        return service.listarTodas();
    }

    // POST /contas/transferir - Realizar transferência usando DTO
    @PostMapping("/transferir")
    public String transferir(@RequestBody TransferenciaDTO dto) {
        boolean sucesso = service.transferir(dto.origem, dto.destino, dto.valor);
        return sucesso ? "Transferência realizada!" : "Erro na transferência.";
    }

    // GET /contas/{numero} - Buscar uma conta específica
    @GetMapping("/{numero}")
    public Conta buscar(@PathVariable int numero) {
        return service.buscarPorNumero(numero);
    }
}