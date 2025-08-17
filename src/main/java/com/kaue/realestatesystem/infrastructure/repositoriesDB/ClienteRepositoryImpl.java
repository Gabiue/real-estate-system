package com.kaue.realestatesystem.infrastructure.repositoriesDB;

import com.kaue.realestatesystem.domain.entities.Cliente;
import com.kaue.realestatesystem.domain.repositories.ClienteRepository;
import com.kaue.realestatesystem.infrastructure.entitiesDB.ClienteEntity;
import com.kaue.realestatesystem.infrastructure.mappersDB.ClienteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteRepositoryJpa jpaRepository;
    private final ClienteEntityMapper mapper;

    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        ClienteEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Cliente> buscarId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return jpaRepository.findByCpf(cpf)
                .map(mapper::toDomain);
    }

    @Override
    public List<Cliente> ListarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }

    @Override
    public void deletar(Long id) {
        jpaRepository.deleteById(id);
    }
}