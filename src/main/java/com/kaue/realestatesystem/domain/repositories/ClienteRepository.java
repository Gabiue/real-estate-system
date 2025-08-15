package com.kaue.realestatesystem.domain.repositories;
import com.kaue.realestatesystem.domain.entities.Cliente;

import java.util.List;
import java.util.Optional;


public interface ClienteRepository {

    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarId(Long id);

    Optional<Cliente> buscarPorCpf(String cpf);

    List<Cliente> ListarTodos();

    boolean existePorCpf(String cpf);

    void deletar(Long id);

}
