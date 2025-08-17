package com.kaue.realestatesystem.api.controllers;

import com.kaue.realestatesystem.api.dto.AtualizarImovelRequest;
import com.kaue.realestatesystem.api.dto.CriarImovelRequest;
import com.kaue.realestatesystem.api.dto.ImovelDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/imoveis")
@CrossOrigin(origins = "*")
public class ImovelController {

    @PostMapping
    public ResponseEntity<ImovelDTO> cadastrarImovel(@Valid @RequestBody CriarImovelRequest request){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();

    }

    @GetMapping
    public ResponseEntity<List<ImovelDTO>> listarImoveis(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) Integer quartos,
            @RequestParam(required = false) String status){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImovelDTO> buscarImovelPorId(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImovelDTO> atualizarImovel(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarImovelRequest request){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();

    }

    //DELETAR IMOVEL
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarImovel(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    //BUSCAR IMOVEL POR CÓDIGO UNICO
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ImovelDTO> buscarImovelPorCodigo(@PathVariable String codigo) {

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


    //LISTAR DISPONIVEIS
    @GetMapping("/disponiveis")
    public ResponseEntity<List<ImovelDTO>> listarImoveisDisponiveis() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


    //ALTERAR STATUS
    @PutMapping("/{id}/status")
    public ResponseEntity<ImovelDTO> alterarStatusImovel(
            @PathVariable Long id,
            @RequestParam String acao) {

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    //BUSCAR POR FAIXA DE PREÇO

    @GetMapping("/preco")
    public ResponseEntity<List<ImovelDTO>> buscarPorFaixaPreco(
            @RequestParam BigDecimal precoMin,
            @RequestParam BigDecimal precoMax) {

        if (precoMin == null || precoMax == null || precoMin.compareTo(precoMax) > 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }



}
