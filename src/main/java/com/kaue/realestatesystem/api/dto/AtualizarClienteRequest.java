package com.kaue.realestatesystem.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AtualizarClienteRequest {
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 a 100 caracteres")
    private String nome;

    @Size(max = 150, message = "maximo de 100 caracteres")
    @Email(message = "Digite um email valido")
    private String email;

    @NotNull(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\(\\{2}\\) \\d{4,5}-\\d{4}", message = "O formato deve serguir o padrão: (12) 00000-0000")
    private String telefone;

    @Size(max = 500, message = "O máximo é 500 caracteres")
    private String observacoes;
}
