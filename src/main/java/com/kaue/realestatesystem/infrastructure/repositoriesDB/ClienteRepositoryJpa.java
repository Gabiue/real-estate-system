package com.kaue.realestatesystem.infrastructure.repositoriesDB;

import com.kaue.realestatesystem.infrastructure.entitiesDB.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepositoryJpa extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}