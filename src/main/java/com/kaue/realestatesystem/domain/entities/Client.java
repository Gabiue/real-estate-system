package com.kaue.realestatesystem.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Client {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private String endereco;
    private LocalDateTime criadoEm;
    private  LocalDateTime atualizadoEm;

    //CONSTRUTOR

    public Client(){
        public Client(String nome, String cpf, String email, String telefone){
            this.nome = nome;
            this.cpf = cpf;
            this.email = email;
            this.telefone = telefone;
            this.criadoEm = LocalDateTime.now();
            this.atualizadoEm = LocalDateTime.now();

            this.validar();
        }
        public void validar(){
            if(nome == null || nome.trim().isEmpty()){
                throw new IllegalArgumentException("Nome é obrigatório");
            }
            if(!IsCpfValido){
                throw new IllegalArgumentException("O CPF é inválido");
            }
            if(!IsEmailValido){
                throw new IllegalArgumentException("O Email é inválido");
            }

        }

    }



}
