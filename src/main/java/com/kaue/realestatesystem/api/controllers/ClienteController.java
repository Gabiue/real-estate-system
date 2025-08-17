package com.kaue.realestatesystem.api.controllers;

import com.kaue.realestatesystem.api.dto.AtualizarClienteRequest;
import com.kaue.realestatesystem.api.dto.ClienteDTO;
import com.kaue.realestatesystem.api.dto.CriarClienteRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    //CRIAR CLIENTE

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody CriarClienteRequest request){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    //LISTAR CLIENTES
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes(){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    //BUSCAR POR ID

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    //ATUALIZAR CLIENTE EXISTENTE

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarClienteRequest request){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    //DELETAR CLIENTE

    @DeleteMapping
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    //BUSCAR CLIENTE POR CPF

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteDTO> buscarClientePorCpf(@PathVariable String cpf){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    //VERIFICAR CPF EXISTENTE

    @GetMapping
    public ResponseEntity<Void> verificarCpfExiste(@PathVariable String cpf){

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

}
