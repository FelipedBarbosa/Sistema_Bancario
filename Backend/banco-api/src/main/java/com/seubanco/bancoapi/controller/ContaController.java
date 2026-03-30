package com.seubanco.bancoapi.controller;
import com.seubanco.bancoapi.dto.ContaDTO;
import com.seubanco.bancoapi.dto.OperacaoDTO;
import com.seubanco.bancoapi.dto.TransferenciaDTO;
import com.seubanco.bancoapi.model.Conta;
import com.seubanco.bancoapi.model.ContaCorrente;
import com.seubanco.bancoapi.model.ContaPoupanca;
import com.seubanco.bancoapi.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/contas")
@CrossOrigin(origins = "*")
public class ContaController {

    @Autowired
    private ContaService service;

    @GetMapping
    public List<Conta> listar() {
        return service.listarTodas();
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Conta> buscar(@PathVariable int numero) {
        Conta conta = service.buscarPorNumero(numero);
        return conta != null ? ResponseEntity.ok(conta) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Conta> criar(@RequestBody ContaDTO dto) {
        Conta novaConta;
        if (dto.tipo.equalsIgnoreCase("CORRENTE")) {
            novaConta = new ContaCorrente(dto.numero, dto.titular);
        } else {
            novaConta = new ContaPoupanca(dto.numero, dto.titular);
        }
        return ResponseEntity.ok(service.salvar(novaConta));
    }

    @PostMapping("/depositar")
    public ResponseEntity<String> depositar(@RequestBody OperacaoDTO dto) {
        service.depositar(dto.numero, dto.valor);
        return ResponseEntity.ok("Depósito de R$ " + dto.valor + " realizado com sucesso!");
    }

    @PostMapping("/sacar")
    public ResponseEntity<String> sacar(@RequestBody OperacaoDTO dto) {
        boolean sucesso = service.sacar(dto.numero, dto.valor);
        return sucesso ? ResponseEntity.ok("Saque realizado!") : ResponseEntity.badRequest().body("Saldo insuficiente ou erro no saque.");
    }

    @PostMapping("/transferir")
    public ResponseEntity<String> transferir(@RequestBody TransferenciaDTO dto) {
        boolean sucesso = service.transferir(dto.origem, dto.destino, dto.valor);
        return sucesso ? ResponseEntity.ok("Transferência de R$ " + dto.valor + " realizada!") : ResponseEntity.badRequest().body("Erro na transferência.");
    }

    @GetMapping("/tributos")
    public ResponseEntity<Double> calcularTributos() {
        return ResponseEntity.ok(service.calcularTotalTributos());
    }

    @GetMapping("/destaque")
    public List<Conta> listarContasDestaque() {
        return service.listarTodas().stream()
                .filter(c -> c.getSaldo() > 1000)
                .toList();
    }
}