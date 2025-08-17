package com.kaue.realestatesystem.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CriarClienteRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 a 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF é obrigatorio")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "cpf deve estar no padrão: 000.000.000-00")
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    @Size(max = 150, message = "Email não pode ter mais que 150 caracteres")
    private String email;

    @NotNull(message = "Data de nascimento é obrigatório")
    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @NotNull(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "O formato deve seguir o padrão: (11) 99999-9999")
    private String telefone;

    @Size(max = 500, message = "O máximo é 500 caracteres")
    private String observacoes;
}