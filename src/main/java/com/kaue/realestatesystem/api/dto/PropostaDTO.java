package com.kaue.realestatesystem.api.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class PropostaDTO {

    private Long id;

    private String numero;

    private Long clienteId;

    private Long imovelId;

    private BigDecimal valorProposta;

    private BigDecimal valorEntrada;

    private BigDecimal valorFinanciado;

    private BigDecimal percentualEntrada;

    private Integer quantidadeParcelas;

    private BigDecimal valorParcela;

    private String status;

    private String observacoes;

    private String motivoRejeicao;

    private LocalDateTime validaAte;

    private Long diasRestantesValidade;

    private LocalDateTime criadaEm;

    private LocalDateTime atualizadaEm;
}
