package com.kaue.realestatesystem.infrastructure.mappersDB;

import com.kaue.realestatesystem.domain.entities.Proposta;
import com.kaue.realestatesystem.domain.enums.StatusProposta;
import com.kaue.realestatesystem.infrastructure.entitiesDB.PropostaEntity;
import org.springframework.stereotype.Component;

@Component
public class PropostaEntityMapper {

    public PropostaEntity toEntity(Proposta domain) {
        if (domain == null) {
            return null;
        }

        return PropostaEntity.builder()
                .id(domain.getId())
                .numero(domain.getNumero())
                .clienteId(domain.getClienteId())
                .imovelId(domain.getImovelId())
                .valorEntrada(domain.getValorEntrada())
                .valorProposta(domain.getValorProposta())
                .quantidadeParcelas(domain.getQuantidadeParcelas())
                .status(toEntityEnum(domain.getStatus()))
                .observacoes(domain.getObservacoes())
                .motivoRejeicao(domain.getMotivoRejeicao())
                .criadaEm(domain.getCriadaEm())
                .atualizadaEm(domain.getAtualizadaEm())
                .validaAte(domain.getValidaAte())
                .build();
    }

    public Proposta toDomain(PropostaEntity entity) {
        if (entity == null) {
            return null;
        }

        return Proposta.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .clienteId(entity.getClienteId())
                .imovelId(entity.getImovelId())
                .valorEntrada(entity.getValorEntrada())
                .valorProposta(entity.getValorProposta())
                .quantidadeParcelas(entity.getQuantidadeParcelas())
                .status(toDomainEnum(entity.getStatus()))
                .observacoes(entity.getObservacoes())
                .motivoRejeicao(entity.getMotivoRejeicao())
                .criadaEm(entity.getCriadaEm())
                .atualizadaEm(entity.getAtualizadaEm())
                .validaAte(entity.getValidaAte())
                .build();
    }

    private PropostaEntity.StatusPropostaEnum toEntityEnum(StatusProposta domainStatus) {
        if (domainStatus == null) return null;
        return PropostaEntity.StatusPropostaEnum.valueOf(domainStatus.name());
    }

    private StatusProposta toDomainEnum(PropostaEntity.StatusPropostaEnum entityStatus) {
        if (entityStatus == null) return null;
        return StatusProposta.valueOf(entityStatus.name());
    }
}