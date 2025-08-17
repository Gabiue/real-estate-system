package com.kaue.realestatesystem.api.controllers;

import com.kaue.realestatesystem.api.dto.AtualizarClienteRequest;
import com.kaue.realestatesystem.api.dto.ClienteDTO;
import com.kaue.realestatesystem.api.dto.CriarClienteRequest;
import com.kaue.realestatesystem.application.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody CriarClienteRequest request){
        return ResponseEntity.ok(clienteService.criarCliente(request));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes(){
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarClienteRequest request){
        return ResponseEntity.ok(clienteService.atualizarCliente(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteDTO> buscarClientePorCpf(@PathVariable String cpf){
        return ResponseEntity.ok(clienteService.buscarPorCpf(cpf));
    }

    @GetMapping("/cpf/{cpf}/existe")
    public ResponseEntity<Boolean> verificarCpfExiste(@PathVariable String cpf){
        return ResponseEntity.ok(clienteService.verificarCpfExiste(cpf));
    }
}