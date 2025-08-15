package com.kaue.realestatesystem.api.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;


@Data
public class CriarPropostarRequest{

    @NotNull(message = "ID do cliente é obrigatório")
    @Positive(message = "ID do cliente deve ser positivo")
    private Long clienteId;

    @NotNull(message = "ID do imóvel é obrigatório")
    @Positive(message = "ID do imóvel deve ser positivo")
    private Long imovelId;

    @NotNull(message = "Valor da proposta é obrigatório")
    @DecimalMin(value = "1000.0", message = "Valor da proposta deve ser maior que R$ 1.000")
    @Digits(integer = 12, fraction = 2, message = "Valor deve ter no máximo 12 dígitos e 2 casas decimais")
    private BigDecimal valorProposta;

    @DecimalMin(value = "0.0", message = "Valor da entrada não pode ser negativo")
    @Digits(integer = 12, fraction = 2, message = "Entrada deve ter no máximo 12 dígitos e 2 casas decimais")
    private BigDecimal valorEntrada;

    @Min(value = 1, message = "Quantidade de parcelas deve ser no mínimo 1")
    @Max(value = 480, message = "Quantidade de parcelas deve ser no máximo 480 (40 anos)")
    private Integer quantidadeParcelas;

    @Size(max = 1000, message = "Observações não podem ter mais de 1000 caracteres")
    private String observacoes;

}
