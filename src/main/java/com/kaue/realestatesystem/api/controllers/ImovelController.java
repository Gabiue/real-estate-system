package com.kaue.realestatesystem.api.controllers;

import com.kaue.realestatesystem.api.dto.AtualizarImovelRequest;
import com.kaue.realestatesystem.api.dto.CriarImovelRequest;
import com.kaue.realestatesystem.api.dto.ImovelDTO;
import com.kaue.realestatesystem.application.services.ImovelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/imoveis")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ImovelController {

    private final ImovelService imovelService;

    @PostMapping
    public ResponseEntity<ImovelDTO> cadastrarImovel(@Valid @RequestBody CriarImovelRequest request){
        return ResponseEntity.ok(imovelService.cadastrarImovel(request));
    }

    @GetMapping
    public ResponseEntity<List<ImovelDTO>> listarImoveis(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) Integer quartos,
            @RequestParam(required = false) String status){
        return ResponseEntity.ok(imovelService.listarImoveis(tipo, precoMin, precoMax, quartos, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImovelDTO> buscarImovelPorId(@PathVariable Long id){
        return ResponseEntity.ok(imovelService.buscarImovelPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImovelDTO> atualizarImovel(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarImovelRequest request){
        return ResponseEntity.ok(imovelService.atualizarImovel(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarImovel(@PathVariable Long id){
        imovelService.deletarImovel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ImovelDTO> buscarImovelPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(imovelService.buscarImovelPorCodigo(codigo));
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<ImovelDTO>> listarImoveisDisponiveis() {
        return ResponseEntity.ok(imovelService.listarImoveisDisponiveis());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ImovelDTO> alterarStatusImovel(
            @PathVariable Long id,
            @RequestParam String acao) {
        return ResponseEntity.ok(imovelService.alterarStatusImovel(id, acao));
    }

    @GetMapping("/preco")
    public ResponseEntity<List<ImovelDTO>> buscarPorFaixaPreco(
            @RequestParam BigDecimal precoMin,
            @RequestParam BigDecimal precoMax) {

        if (precoMin == null || precoMax == null || precoMin.compareTo(precoMax) > 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(imovelService.buscarPorFaixaPreco(precoMin, precoMax));
    }
}