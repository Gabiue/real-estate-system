package com.kaue.realestatesystem.infrastructure.repositoriesDB;

import com.kaue.realestatesystem.infrastructure.entitiesDB.ImovelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ImovelRepositoryJpa extends JpaRepository<ImovelEntity, Long> {

    List<ImovelEntity> findByStatus(String status);

    List<ImovelEntity> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

    Optional<ImovelEntity> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}