package com.kaue.realestatesystem.api.controllers;

import com.kaue.realestatesystem.api.dto.CriarPropostarRequest;
import com.kaue.realestatesystem.api.dto.PropostaDTO;
import com.kaue.realestatesystem.application.services.PropostaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/propostas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PropostaController {

    private final PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaDTO> criarProposta(@Valid @RequestBody CriarPropostarRequest request) {
        return ResponseEntity.ok(propostaService.criarProposta(request));
    }

    @GetMapping
    public ResponseEntity<List<PropostaDTO>> listarPropostas(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long imovelId,
            @RequestParam(required = false) BigDecimal valorMin,
            @RequestParam(required = false) BigDecimal valorMax,
            @RequestParam(required = false, defaultValue = "false") boolean incluirExpiradas) {
        return ResponseEntity.ok(propostaService.listarPropostas(status, clienteId, imovelId, valorMin, valorMax, incluirExpiradas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaDTO> buscarPropostaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(propostaService.buscarPropostaPorId(id));
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<PropostaDTO> buscarPropostaPorNumero(@PathVariable String numero) {
        return ResponseEntity.ok(propostaService.buscarPropostaPorNumero(numero));
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<PropostaDTO> aprovarProposta(@PathVariable Long id) {
        return ResponseEntity.ok(propostaService.aprovarProposta(id));
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<PropostaDTO> rejeitarProposta(
            @PathVariable Long id,
            @RequestParam String motivo) {

        if (motivo == null || motivo.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(propostaService.rejeitarProposta(id, motivo));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PropostaDTO> cancelarProposta(@PathVariable Long id) {
        return ResponseEntity.ok(propostaService.cancelarProposta(id));
    }

    @PutMapping("/{id}/observacoes")
    public ResponseEntity<PropostaDTO> atualizarObservacoes(
            @PathVariable Long id,
            @RequestBody String observacoes) {
        return ResponseEntity.ok(propostaService.atualizarObservacoes(id, observacoes));
    }

    @PutMapping("/{id}/estender")
    public ResponseEntity<PropostaDTO> estenderValidade(
            @PathVariable Long id,
            @RequestParam int dias) {

        if (dias <= 0 || dias > 90) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(propostaService.estenderValidade(id, dias));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PropostaDTO>> listarPropostasDoCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(propostaService.listarPropostasDoCliente(clienteId));
    }

    @GetMapping("/imovel/{imovelId}")
    public ResponseEntity<List<PropostaDTO>> listarPropostasDoImovel(@PathVariable Long imovelId) {
        return ResponseEntity.ok(propostaService.listarPropostasDoImovel(imovelId));
    }

    @PostMapping("/simular")
    public ResponseEntity<PropostaService.SimulacaoDTO> simularFinanciamento(
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

        return ResponseEntity.ok(propostaService.simularFinanciamento(valorImovel, valorEntrada, quantidadeParcelas, taxaJuros));
    }
}