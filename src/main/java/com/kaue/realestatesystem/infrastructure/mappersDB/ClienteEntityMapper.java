package com.kaue.realestatesystem.infrastructure.mappersDB;

import com.kaue.realestatesystem.domain.entities.Cliente;
import com.kaue.realestatesystem.infrastructure.entitiesDB.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteEntityMapper {

    public ClienteEntity toEntity(Cliente domain) {
        if (domain == null) {
            return null;
        }

        return ClienteEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .cpf(domain.getCpf())
                .email(domain.getEmail())
                .telefone(domain.getTelefone())
                .dataNascimento(domain.getDataNascimento())
                .endereco(domain.getEndereco())
                .criadoEm(domain.getCriadoEm())
                .atualizadoEm(domain.getAtualizadoEm())
                .build();
    }

    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }

        return Cliente.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .cpf(entity.getCpf())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .dataNascimento(entity.getDataNascimento())
                .endereco(entity.getEndereco())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}