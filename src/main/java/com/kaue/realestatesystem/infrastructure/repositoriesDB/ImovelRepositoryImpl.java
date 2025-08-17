package com.kaue.realestatesystem.infrastructure.repositoriesDB;

import com.kaue.realestatesystem.domain.entities.Imovel;
import com.kaue.realestatesystem.domain.repositories.ImovelRepository;
import com.kaue.realestatesystem.infrastructure.entitiesDB.ImovelEntity;
import com.kaue.realestatesystem.infrastructure.mappersDB.ImovelEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ImovelRepositoryImpl implements ImovelRepository {

    private final ImovelRepositoryJpa jpaRepository;
    private final ImovelEntityMapper mapper;

    @Override
    public Imovel salvar(Imovel imovel) {
        ImovelEntity entity = mapper.toEntity(imovel);
        ImovelEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Imovel> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Imovel> listarDisponiveis() {
        return jpaRepository.findByStatus("DISPONIVEL")
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Imovel> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return jpaRepository.findByPrecoBetween(precoMin, precoMax)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return jpaRepository.existsByCodigo(codigo);
    }

    @Override
    public void deletar(Long id) {
        jpaRepository.deleteById(id);
    }
}