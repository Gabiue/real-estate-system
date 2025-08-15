package com.kaue.realestatesystem.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ImovelDTO {

        private Long id;

        private String codigo;

        private String tipo;

        private String endereco;

        private BigDecimal area;

        private BigDecimal preco;

        private Integer quartos;

        private String descricao;

        private String status;

        private LocalDateTime criadoEm;

        private LocalDateTime atualizadoEm;
    }

