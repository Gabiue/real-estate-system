package com.kaue.realestatesystem.domain.enums;

public enum StatusProposta {
    PENDENTE("Aguardando análise"),
    APROVADA("Proposta aprovada"),
    REJEITADA("Proposta rejeitada"),
    CANCELADA("Proposta cancelada"),
    EXPIRADA("Proposta expirada");

    private final String descricao;

    StatusProposta(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean permitirAlteracao(){
        return this == PENDENTE;
    }

    public boolean isFinal(){
        return this != PENDENTE;
    }
    public boolean isAtiva(){
        return this == PENDENTE || this == APROVADA;
    }
}
