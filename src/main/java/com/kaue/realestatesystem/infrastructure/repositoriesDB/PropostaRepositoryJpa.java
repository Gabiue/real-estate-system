package com.kaue.realestatesystem.infrastructure.repositoriesDB;

import com.kaue.realestatesystem.infrastructure.entitiesDB.PropostaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PropostaRepositoryJpa extends JpaRepository<PropostaEntity, Long> {

    Optional<PropostaEntity> findByNumero(String numero);

    List<PropostaEntity> findByStatus(PropostaEntity.StatusPropostaEnum status);

    List<PropostaEntity> findByClienteId(Long clienteId);

    List<PropostaEntity> findByImovelId(Long imovelId);

    @Query("SELECT p FROM PropostaEntity p WHERE p.status = 'PENDENTE' AND p.validaAte < :dataAtual")
    List<PropostaEntity> findPropostasExpiradas(LocalDateTime dataAtual);

    boolean existsByNumero(String numero);
}