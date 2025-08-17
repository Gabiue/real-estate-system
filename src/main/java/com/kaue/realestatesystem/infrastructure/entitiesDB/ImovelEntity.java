package com.kaue.realestatesystem.infrastructure.entitiesDB;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "imoveis")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImovelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false, length = 300)
    private String endereco;

    @Column(length = 8)
    private String cep;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @Column
    private Integer quartos;

    @Column
    private Integer banheiros;

    @Column
    private Integer vagas;

    @Column(name = "area_total")
    private Double areaTotal;

    @Column(name = "area_construida")
    private Double areaConstruida;

    @Column(length = 1000)
    private String descricao;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "proprietario_id")
    private Long proprietarioId;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}