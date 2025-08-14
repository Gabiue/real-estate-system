package com.kaue.realestatesystem.domain.entities;

import com.kaue.realestatesystem.domain.enums.StatusProposta;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"clienteId", "imovelId"})
@EqualsAndHashCode(exclude = {"criadaEm", "atualizadaEm"})
public class Proposta {

    private Long id;

    @Builder.Default
    private String numero = gerarNumero();

    private Long clienteId;    // FK para Cliente
    private Long imovelId;     // FK para Imovel

    private BigDecimal valorProposta;    // Valor oferecido pelo cliente
    private BigDecimal valorEntrada;     // Valor da entrada
    private Integer quantidadeParcelas;  // Número de parcelas para financiamento

    @Builder.Default
    private String status = "PENDENTE";  // PENDENTE, APROVADA, REJEITADA, CANCELADA, EXPIRADA

    private String observacoes;          // Observações do cliente
    private String motivoRejeicao;       // Motivo se rejeitada

    @Builder.Default
    private LocalDateTime criadaEm = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime atualizadaEm = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime validaAte = LocalDateTime.now().plusDays(15); // Padrão: 15 dias

    // validação

    public void validarConsistenciaInterna() {
        if (valorProposta != null && valorProposta.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Bug detectado: valor da proposta deve ser positivo");
        }

        if (valorEntrada != null && valorEntrada.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Bug detectado: entrada não pode ser negativa");
        }

        if (valorProposta != null && valorEntrada != null && valorEntrada.compareTo(valorProposta) > 0) {
            throw new IllegalStateException("Bug detectado: entrada maior que valor da proposta");
        }

        if (quantidadeParcelas != null && quantidadeParcelas <= 0) {
            throw new IllegalStateException("Bug detectado: parcelas deve ser positivo");
        }
    }

    // ===== MÉTODOS DE STATUS =====

    public boolean isPendente() {
        return "PENDENTE".equals(status);
    }

    public boolean isAprovada() {
        return "APROVADA".equals(status);
    }

    public boolean isRejeitada() {
        return "REJEITADA".equals(status);
    }

    public boolean isCancelada() {
        return "CANCELADA".equals(status);
    }

    public boolean isExpirada() {
        return "EXPIRADA".equals(status) || (isPendente() && validaAte.isBefore(LocalDateTime.now()));
    }

    public boolean isValida() {
        return isPendente() && validaAte.isAfter(LocalDateTime.now());
    }

    // NEGÓCIO

    public void aprovar() {
        if (!isValida()) {
            throw new IllegalStateException("Proposta não pode ser aprovada. Status: " + getStatusDetalhado());
        }
        this.status = "APROVADA";
        this.atualizadaEm = LocalDateTime.now();
    }

    public void rejeitar(String motivo) {
        if (!isPendente()) {
            throw new IllegalStateException("Apenas propostas pendentes podem ser rejeitadas");
        }
        this.status = "REJEITADA";
        this.motivoRejeicao = motivo;
        this.atualizadaEm = LocalDateTime.now();
    }

    public void cancelar() {
        if (!isPendente()) {
            throw new IllegalStateException("Apenas propostas pendentes podem ser canceladas");
        }
        this.status = "CANCELADA";
        this.atualizadaEm = LocalDateTime.now();
    }

    public void marcarComoExpirada() {
        if (isPendente() && validaAte.isBefore(LocalDateTime.now())) {
            this.status = "EXPIRADA";
            this.atualizadaEm = LocalDateTime.now();
        }
    }

    public void estenderValidade(int dias) {
        if (!isPendente()) {
            throw new IllegalStateException("Apenas propostas pendentes podem ter validade estendida");
        }
        if (dias <= 0) {
            throw new IllegalArgumentException("Dias deve ser positivo");
        }
        this.validaAte = this.validaAte.plusDays(dias);
        this.atualizadaEm = LocalDateTime.now();
    }

    // ===== CÁLCULOS FINANCEIROS =====

    public BigDecimal calcularValorFinanciado() {
        if (valorProposta == null || valorEntrada == null) {
            return BigDecimal.ZERO;
        }
        return valorProposta.subtract(valorEntrada);
    }

    public BigDecimal calcularPercentualEntrada() {
        if (valorProposta == null || valorEntrada == null || valorProposta.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return valorEntrada.divide(valorProposta, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public BigDecimal calcularValorParcela() {
        if (quantidadeParcelas == null || quantidadeParcelas <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal valorFinanciado = calcularValorFinanciado();
        if (valorFinanciado.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return valorFinanciado.divide(BigDecimal.valueOf(quantidadeParcelas), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularValorParcelaComJuros(double taxaJurosAnual) {
        if (quantidadeParcelas == null || quantidadeParcelas <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal valorFinanciado = calcularValorFinanciado();
        if (valorFinanciado.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        if (taxaJurosAnual <= 0) {
            return calcularValorParcela(); // Sem juros
        }

        // Fórmula da Tabela Price: PMT = PV * [(1+i)^n * i] / [(1+i)^n - 1]
        double taxaJurosMensal = taxaJurosAnual / 12 / 100;
        double fator = Math.pow(1 + taxaJurosMensal, quantidadeParcelas);
        double valorParcela = valorFinanciado.doubleValue() * (fator * taxaJurosMensal) / (fator - 1);

        return BigDecimal.valueOf(valorParcela).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularTotalComJuros(double taxaJurosAnual) {
        BigDecimal parcelaComJuros = calcularValorParcelaComJuros(taxaJurosAnual);
        if (quantidadeParcelas == null || parcelaComJuros.compareTo(BigDecimal.ZERO) <= 0) {
            return valorProposta != null ? valorProposta : BigDecimal.ZERO;
        }

        BigDecimal totalFinanciado = parcelaComJuros.multiply(BigDecimal.valueOf(quantidadeParcelas));
        return totalFinanciado.add(valorEntrada != null ? valorEntrada : BigDecimal.ZERO);
    }

    // ===== MÉTODOS DE ATUALIZAÇÃO =====

    public void atualizarObservacoes(String novasObservacoes) {
        if (!isPendente()) {
            throw new IllegalStateException("Apenas propostas pendentes podem ter observações alteradas");
        }
        this.observacoes = novasObservacoes;
        this.atualizadaEm = LocalDateTime.now();
    }

    // ===== MÉTODOS DE FORMATAÇÃO/UTILIDADE =====

    public long getDiasRestantesValidade() {
        if (isExpirada()) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDateTime.now(), validaAte);
    }

    public String getStatusDetalhado() {
        if (isExpirada() && isPendente()) {
            return "EXPIRADA (vencida em " + validaAte.toLocalDate() + ")";
        }
        return status;
    }

    public String getNumeroFormatado() {
        return numero != null ? numero.toUpperCase() : "";
    }

    public String getResumoFinanceiro() {
        if (valorProposta == null) return "Valores não definidos";

        StringBuilder sb = new StringBuilder();
        sb.append("Proposta: ").append(valorProposta);

        if (valorEntrada != null) {
            sb.append(" | Entrada: ").append(valorEntrada)
                    .append(" (").append(calcularPercentualEntrada().setScale(1, RoundingMode.HALF_UP)).append("%)");
        }

        if (quantidadeParcelas != null && quantidadeParcelas > 0) {
            sb.append(" | ").append(quantidadeParcelas).append("x de ").append(calcularValorParcela());
        }

        return sb.toString();
    }

    // ===== MÉTODOS PRIVADOS =====

    private static String gerarNumero() {
        return "PROP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}