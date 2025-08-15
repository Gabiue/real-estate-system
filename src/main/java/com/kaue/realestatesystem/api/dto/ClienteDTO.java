package com.kaue.realestatesystem.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClienteDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDate dataNascimento;
    private String telefone;
    private String observacoes;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
