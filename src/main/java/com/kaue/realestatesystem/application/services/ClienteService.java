package com.kaue.realestatesystem.application.services;

import com.kaue.realestatesystem.api.controllers.ClienteController;
import com.kaue.realestatesystem.api.dto.AtualizarClienteRequest;
import com.kaue.realestatesystem.api.dto.ClienteDTO;
import com.kaue.realestatesystem.api.dto.CriarClienteRequest;
import com.kaue.realestatesystem.api.mappers.ClienteMapper;
import com.kaue.realestatesystem.domain.entities.Cliente;
import com.kaue.realestatesystem.domain.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteDTO criarCliente(CriarClienteRequest request){
        Cliente cliente = clienteMapper.toEntity(request);

        Cliente clienteSalvo = clienteRepository.salvar(cliente);

        return clienteMapper.toDTO(clienteSalvo);
    }

    public List<ClienteDTO> listarTodos() {
        List<Cliente> clientes = clienteRepository.ListarTodos();

        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }


    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.buscarId(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return clienteMapper.toDTO(cliente);
    }

    public ClienteDTO buscarPorCpf(String cpf) {
        Cliente cliente = clienteRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return clienteMapper.toDTO(cliente);
    }

    public ClienteDTO atualizarCliente(Long id, AtualizarClienteRequest request){
        Cliente cliente = clienteRepository.buscarId(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        clienteMapper.updateEntity(cliente, request);

        Cliente clienteAtualizado = clienteRepository.salvar(cliente);

        return clienteMapper.toDTO(clienteAtualizado);

    }

    public void deletarCliente(Long id) {
        clienteRepository.deletar(id);
    }

    public boolean verificarCpfExiste(String cpf) {
        return clienteRepository.existePorCpf(cpf);
    }
}
