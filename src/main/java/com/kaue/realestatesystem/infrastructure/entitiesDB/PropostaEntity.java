package com.kaue.realestatesystem.infrastructure.entitiesDB;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "propostas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropostaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "imovel_id", nullable = false)
    private Long imovelId;

    @Column(name = "valor_entrada", precision = 12, scale = 2)
    private BigDecimal valorEntrada;

    @Column(name = "valor_proposta", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorProposta;

    @Column(name = "quantidade_parcelas")
    private Integer quantidadeParcelas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPropostaEnum status;

    @Column(length = 1000)
    private String observacoes;

    @Column(name = "motivo_rejeicao", length = 500)
    private String motivoRejeicao;

    @Column(name = "criada_em", nullable = false)
    private LocalDateTime criadaEm;

    @Column(name = "atualizada_em", nullable = false)
    private LocalDateTime atualizadaEm;

    @Column(name = "valida_ate", nullable = false)
    private LocalDateTime validaAte;

    @PrePersist
    protected void onCreate() {
        criadaEm = LocalDateTime.now();
        atualizadaEm = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadaEm = LocalDateTime.now();
    }

    public enum StatusPropostaEnum {
        PENDENTE, APROVADA, REJEITADA, CANCELADA, EXPIRADA
    }
}