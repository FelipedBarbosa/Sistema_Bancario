package com.seubanco.bancoapi.controller;
import com.seubanco.bancoapi.dto.ContaDTO;
import com.seubanco.bancoapi.dto.TransferenciaDTO;
import com.seubanco.bancoapi.model.Conta;
import com.seubanco.bancoapi.model.ContaCorrente;
import com.seubanco.bancoapi.model.ContaPoupanca;
import com.seubanco.bancoapi.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService service;

    @GetMapping
    public List<Conta> listar() {
        return service.listarTodas();
    }

    @PostMapping("/transferir")
    public String transferir(@RequestBody TransferenciaDTO dto) {
        boolean sucesso = service.transferir(dto.origem, dto.destino, dto.valor);
        return sucesso ? "Transferência realizada!" : "Erro na transferência.";
    }

    @GetMapping("/{numero}")
    public Conta buscar(@PathVariable int numero) {
        return service.buscarPorNumero(numero);
    }

    @PostMapping
    public Conta criar(@RequestBody ContaDTO dto) {
        if (dto.tipo.equalsIgnoreCase("CORRENTE")) {
            return service.salvar(new ContaCorrente(dto.numero, dto.titular));
        } else {
            return service.salvar(new ContaPoupanca(dto.numero, dto.titular));
        }
    }
}