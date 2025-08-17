package com.kaue.realestatesystem.api.controllers;

import com.kaue.realestatesystem.api.dto.CriarPropostarRequest;
import com.kaue.realestatesystem.api.dto.PropostaDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/propostas")
@CrossOrigin(origins = "*")
public class PropostaController {
    @PostMapping
    public ResponseEntity<PropostaDTO> criarProposta(@Valid @RequestBody CriarPropostarRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping
    public ResponseEntity<List<PropostaDTO>> listarPropostas(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long imovelId,
            @RequestParam(required = false) BigDecimal valorMin,
            @RequestParam(required = false) BigDecimal valorMax,
            @RequestParam(required = false, defaultValue = "false") boolean incluirExpiradas) {

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaDTO> buscarPropostaPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<PropostaDTO> buscarPropostaPorNumero(@PathVariable String numero) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<PropostaDTO> aprovarProposta(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<PropostaDTO> rejeitarProposta(
            @PathVariable Long id,
            @RequestParam String motivo) {

        if (motivo == null || motivo.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PropostaDTO> cancelarProposta(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{id}/observacoes")
    public ResponseEntity<PropostaDTO> atualizarObservacoes(
            @PathVariable Long id,
            @RequestBody String observacoes) {

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{id}/estender")
    public ResponseEntity<PropostaDTO> estenderValidade(
            @PathVariable Long id,
            @RequestParam int dias) {

        if (dias <= 0 || dias > 90) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PropostaDTO>> listarPropostasDoCliente(@PathVariable Long clienteId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/imovel/{imovelId}")
    public ResponseEntity<List<PropostaDTO>> listarPropostasDoImovel(@PathVariable Long imovelId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping("/simular")
    public ResponseEntity<SimulacaoDTO> simularFinanciamento(
            @RequestParam BigDecimal valorImovel,
            @RequestParam BigDecimal valorEntrada,
            @RequestParam int quantidadeParcelas,
            @RequestParam(required = false, defaultValue = "0") double taxaJuros) {

        if (valorImovel == null || valorEntrada == null ||
                valorImovel.compareTo(BigDecimal.ZERO) <= 0 ||
                valorEntrada.compareTo(BigDecimal.ZERO) < 0 ||
                valorEntrada.compareTo(valorImovel) > 0 ||
                quantidadeParcelas <= 0 || quantidadeParcelas > 480) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    public static class SimulacaoDTO {
        public BigDecimal valorImovel;
        public BigDecimal valorEntrada;
        public BigDecimal valorFinanciado;
        public BigDecimal percentualEntrada;
        public int quantidadeParcelas;
        public BigDecimal valorParcela;
        public double taxaJuros;
        public BigDecimal valorTotalComJuros;
    }
}
