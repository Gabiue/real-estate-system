package com.kaue.realestatesystem.domain.entities;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "cpf")
@EqualsAndHashCode(exclude = {"criadoEm", "atualizadoEm"})

public class Client {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private String endereco;

    @Builder.Default
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime atualizadoEm = LocalDateTime.now();

    public void validar(){
        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if(!isCpfValido()){
            throw new IllegalArgumentException("Cpf inválido");
        }
        if(!isEmailValido()){
            throw new IllegalArgumentException("Email inválido");
        }
    }

    public boolean isCpfValido(){
        return cpf != null
        && cpf.matches("\\d{11}")
        && !isCpfSequencial() && isDigitoVerificadorValido();
    }
    public boolean isMaiorIdade(){
        return dataNascimento != null && dataNascimento.isBefore(LocalDate.now().minusYears(18));
    }
    public void atualizarDados(String nome, String email, String telefone, String endereco){
        if(nome !=null) this.nome = nome;
        if(email !=null) this.email = email;
        if(telefone !=null) this.telefone = telefone;
        if(endereco !=null) this.endereco = endereco;

        this.atualizadoEm = LocalDateTime.now();
        this.validar();
    }

    public String getNomeFormatado(){
        return nome != null ? nome.trim().toUpperCase() : "";
    }
    public String getCpfFormatado(){
        if(cpf == null || cpf.length()!= 11) return cpf;
        return cpf.substring(0,3) + "." + cpf.substring(3,6)+ "." + cpf.substring(6,9)+ "-" + cpf.substring(9);
    }

    //MÉTODOS PRIVADOS

    private boolean isCpfSequencial(){
        return cpf != null && cpf.chars().distinct().count() == 1;
    }
    private boolean isEmailValido(){
        return email != null
                && email.trim().length() > 0
                && email.contains("@")
                && email.contains(".")
                && !email.startsWith("@")
                && !email.endsWith("@");
    }

    private boolean isDigitoVerificadorValido(){
        return cpf.length() == 11;
    }
}
