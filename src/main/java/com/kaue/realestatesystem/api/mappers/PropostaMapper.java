package com.kaue.realestatesystem.api.mappers;

import com.kaue.realestatesystem.api.dto.PropostaDTO;
import com.kaue.realestatesystem.api.dto.CriarPropostarRequest;
import com.kaue.realestatesystem.domain.entities.Proposta;
import com.kaue.realestatesystem.domain.enums.StatusProposta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PropostaMapper {


    public Proposta toEntity(CriarPropostarRequest request){
        if (request == null) {
            return null;
        }
        return Proposta.builder()
                .clienteId(request.getClienteId())
                .imovelId(request.getImovelId())
                .valorProposta(request.getValorProposta())
                .valorEntrada(request.getValorEntrada() != null ?
                        request.getValorEntrada() : BigDecimal.ZERO)
                .quantidadeParcelas(request.getQuantidadeParcelas())
                .observacoes(request.getObservacoes())
                .build();

    }
    public PropostaDTO toDTO(Proposta proposta){
        if (proposta == null) {
            return null;
        }

        PropostaDTO dto = new PropostaDTO();

        dto.setId(proposta.getId());
        dto.setNumero(proposta.getNumero());
        dto.setClienteId(proposta.getClienteId());  // clientId → clienteId
        dto.setImovelId(proposta.getImovelId());
        dto.setValorProposta(proposta.getValorProposta());
        dto.setValorEntrada(proposta.getValorEntrada());
        dto.setQuantidadeParcelas(proposta.getQuantidadeParcelas());
        dto.setObservacoes(proposta.getObservacoes());
        dto.setMotivoRejeicao(proposta.getMotivoRejeicao());
        dto.setValidaAte(proposta.getValidaAte());
        dto.setCriadaEm(proposta.getCriadaEm());
        dto.setAtualizadaEm(proposta.getAtualizadaEm());

        dto.setStatus(proposta.getStatus() != null ?
                proposta.getStatus().name() : null);

        dto.setValorFinanciado(proposta.calcularValorFinanciado());
        dto.setPercentualEntrada(proposta.calcularPercentualEntrada());
        dto.setValorParcela(proposta.calcularValorParcela());
        dto.setDiasRestantesValidade(proposta.getDiasRestantesValidade());

        return dto;
    }
    public void updateObservacoes(Proposta proposta, String novasObservacoes) {
        if (proposta == null) {
            return;
        }
        proposta.atualizarObservacoes(novasObservacoes);
    }

    public StatusProposta stringToStatusEnum(String statusString) {
        if (statusString == null || statusString.trim().isEmpty()) {
            return null;
        }

        try {
            return StatusProposta.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Status inválido
        }
    }

    public String statusEnumToString(StatusProposta status) {
        return status != null ? status.name() : null;
    }

    }
