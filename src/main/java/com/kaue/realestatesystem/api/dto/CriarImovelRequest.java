package com.kaue.realestatesystem.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CriarImovelRequest {

    @NotBlank(message = "Tipo do imóvel é obrigatório")
    @Pattern(regexp = "CASA|APARTAMENTO|TERRENO|COMERCIAL", message = "Tipo deve ser: CASA, APARTAMENTO, TERRENO ou COMERCIAL")
    private String tipo;

    @NotBlank(message = "Endereço é obrigatório")
    @Size(min = 10, max = 300, message = "Endereço deve ter entre 10 e 300 caracteres")
    private String endereco;

    @NotNull(message = "Área é obrigatória")
    @DecimalMin(value = "1.0", message = "Área deve ser maior que 1 m²")
    @DecimalMax(value = "100000.0", message = "Área deve ser menor que 100.000 m²")
    @Digits(integer = 6, fraction = 2, message = "Área deve ter no máximo 6 dígitos e 2 casas decimais")
    private BigDecimal area;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "1000.0", message = "Preço deve ser maior que R$ 1.000")
    @Digits(integer = 12, fraction = 2, message = "Preço deve ter no máximo 12 dígitos e 2 casas decimais")
    private BigDecimal preco;

    @NotNull(message = "Número de quartos é obrigatório")
    @Min(value = 0, message = "Número de quartos não pode ser negativo")
    @Max(value = 50, message = "Número de quartos deve ser menor que 50")
    private Integer quartos;

    @Size(max = 1000, message = "Descrição não pode ter mais de 1000 caracteres")
    private String descricao;
}
