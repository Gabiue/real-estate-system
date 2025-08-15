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

    private Long clienteId;
    private Long imovelId;

    private BigDecimal valorEntrada;
    private BigDecimal valorProposta;
    private Integer quantidadeParcelas;

    @Builder.Default

    private StatusProposta status = StatusProposta.PENDENTE;

    private String observacoes;
    private String motivoRejeicao;

    @Builder.Default
    private LocalDateTime criadaEm = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime atualizadaEm = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime validaAte = LocalDateTime.now().plusDays(15);

    //VALIDACAO

    public void validarConsistenciaInterna() {
        // Só para detectar bugs/corrupção de dados, não regras de negócio
        if (status == null) {
            throw new IllegalStateException("Bug detectado: status não pode ser null");
        }
        if (criadaEm == null) {
            throw new IllegalStateException("Bug detectado: criadaEm não pode ser null");
        }
        if (atualizadaEm == null) {
            throw new IllegalStateException("Bug detectado: atualizadaEm não pode ser null");
        }
        if (validaAte == null) {
            throw new IllegalStateException("Bug detectado: validaAte não pode ser null");
        }
    }

    public boolean isPendente(){
        return status == StatusProposta.PENDENTE;
    }

    public boolean isAprovada(){
        return status == StatusProposta.APROVADA;
    }

    public boolean isRejeitada(){
        return status == StatusProposta.REJEITADA;
    }
    public boolean isCancelada(){
        return status == StatusProposta.CANCELADA;
    }
    public boolean isExpirada(){
        return status == StatusProposta.EXPIRADA || (isPendente() && validaAte.isBefore(LocalDateTime.now()));
    }

    public boolean isValida(){
        return isPendente() && validaAte.isAfter(LocalDateTime.now());
    }

    //NEGOCIO

    public void aprovar(){
        if(!isValida()){
            throw new IllegalStateException("Proposta não aprovada. Status: " +getStatusDetalhado());
        }
        this.status = StatusProposta.APROVADA;
        this.atualizadaEm = LocalDateTime.now();
    }

    public void rejeitar(String motivo){
        if(!isPendente()){
            throw new IllegalStateException("Apenas propostas pendentes podem ser rejeitadas");
        }
        this.motivoRejeicao = motivo;
        this.status = StatusProposta.REJEITADA;
        this.atualizadaEm = LocalDateTime.now();
    }

    public void cancelar() {
        if (!isPendente()) {
            throw new IllegalStateException("Apenas propostas pendentes podem ser canceladas");
        }
        this.status = StatusProposta.CANCELADA;
        this.atualizadaEm = LocalDateTime.now();
    }

    public void marcarComoExpirada(){
        if(isPendente() && validaAte.isBefore(LocalDateTime.now())){
            this.status = StatusProposta.EXPIRADA;
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

    //ATUALIZAÇÃO

    public void atualizarObservacoes(String novasObservacoes) {
        if (!isPendente()) {
            throw new IllegalStateException("Apenas propostas pendentes podem ter observações alteradas");
        }
        this.observacoes = novasObservacoes;
        this.atualizadaEm = LocalDateTime.now();
    }


    //FORMATAÇÃO

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
        return status.name() + " - " + status.getDescricao();
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


    //gerarUUIDPROP
    private static String gerarNumero(){
        return "PROP-"+UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }


}