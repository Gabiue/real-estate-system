package com.kaue.realestatesystem.infrastructure.mappersDB;

import com.kaue.realestatesystem.domain.entities.Imovel;
import com.kaue.realestatesystem.infrastructure.entitiesDB.ImovelEntity;
import org.springframework.stereotype.Component;

@Component
public class ImovelEntityMapper {

    public ImovelEntity toEntity(Imovel domain) {
        if (domain == null) {
            return null;
        }

        return ImovelEntity.builder()
                .id(domain.getId())
                .codigo(domain.getCodigo())
                .tipo(domain.getTipo())
                .endereco(domain.getEndereco())
                .cep(domain.getCep())
                .preco(domain.getPreco())
                .quartos(domain.getQuartos())
                .banheiros(domain.getBanheiros())
                .vagas(domain.getVagas())
                .areaTotal(domain.getAreaTotal())
                .areaConstruida(domain.getAreaConstruida())
                .descricao(domain.getDescricao())
                .status(domain.getStatus())
                .proprietarioId(domain.getProprietarioId())
                .criadoEm(domain.getCriadoEm())
                .atualizadoEm(domain.getAtualizadoEm())
                .build();
    }

    public Imovel toDomain(ImovelEntity entity) {
        if (entity == null) {
            return null;
        }

        return Imovel.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .tipo(entity.getTipo())
                .endereco(entity.getEndereco())
                .cep(entity.getCep())
                .preco(entity.getPreco())
                .quartos(entity.getQuartos())
                .banheiros(entity.getBanheiros())
                .vagas(entity.getVagas())
                .areaTotal(entity.getAreaTotal())
                .areaConstruida(entity.getAreaConstruida())
                .descricao(entity.getDescricao())
                .status(entity.getStatus())
                .proprietarioId(entity.getProprietarioId())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}