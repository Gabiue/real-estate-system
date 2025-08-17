package com.kaue.realestatesystem.infrastructure.repositoriesDB;

import com.kaue.realestatesystem.domain.entities.Proposta;
import com.kaue.realestatesystem.domain.enums.StatusProposta;
import com.kaue.realestatesystem.domain.repositories.PropostaRepository;
import com.kaue.realestatesystem.infrastructure.entitiesDB.PropostaEntity;
import com.kaue.realestatesystem.infrastructure.mappersDB.PropostaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PropostaRepositoryImpl implements PropostaRepository {

    private final PropostaRepositoryJpa jpaRepository;
    private final PropostaEntityMapper mapper;

    @Override
    public Proposta salvar(Proposta proposta) {
        PropostaEntity entity = mapper.toEntity(proposta);
        PropostaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Proposta> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Proposta> buscarPorNumero(String numero) {
        return jpaRepository.findByNumero(numero)
                .map(mapper::toDomain);
    }

    @Override
    public List<Proposta> listarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Proposta> listarPorStatus(StatusProposta status) {
        PropostaEntity.StatusPropostaEnum entityStatus = PropostaEntity.StatusPropostaEnum.valueOf(status.name());
        return jpaRepository.findByStatus(entityStatus)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Proposta> listarPorCliente(Long clienteId) {
        return jpaRepository.findByClienteId(clienteId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Proposta> listarPorImovel(Long imovelId) {
        return jpaRepository.findByImovelId(imovelId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Proposta> buscarPropostarExpiradas() {
        return jpaRepository.findPropostasExpiradas(LocalDateTime.now())
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorNumero(String numero) {
        return jpaRepository.existsByNumero(numero);
    }

    @Override
    public void deletar(Long id) {
        jpaRepository.deleteById(id);
    }
}