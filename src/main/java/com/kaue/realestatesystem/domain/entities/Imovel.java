package com.kaue.realestatesystem.domain.entities;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "proprietarioId")
@EqualsAndHashCode(exclude = {"criadoEm", "atualizadoEm"})

public class Imovel {
    private Long id;

    @Builder.Default
    private String codigo = gerarCodigo();

    private String tipo;
    private String endereco;
    private String cep;
    private BigDecimal preco;
    private Integer quartos;
    private Integer banheiros;
    private Integer vagas;
    private Double areaTotal;
    private Double areaConstruida;
    private String descricao;

    @Builder.Default
    private String status = "DISPONIVEL";

    private Long proprietarioId;

    @Builder.Default
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime atualizadoEm = LocalDateTime.now();

    public void validarConsistenciaInterna() {
        // Só validações que NUNCA deveriam passar
        if (preco != null && preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Bug detectado: preço negativo não deveria chegar aqui");
        }
        if (areaConstruida != null && areaTotal != null && areaConstruida > areaTotal) {
            throw new IllegalStateException("Bug detectado: área construída maior que total");
        }

        if (quartos != null && quartos < 0) {
            throw new IllegalStateException("Bug detectado: quartos negativos");
        }

        if (banheiros != null && banheiros < 0) {
            throw new IllegalStateException("Bug detectado: banheiros negativos");
        }
    }

    //STATUS

    public boolean isDisponivel(){
        return "DISPONIVEL".equals(status);
    }
    public boolean isVendido(){
        return "VENDIDO".equals(status);
    }
    public boolean isReservado(){
        return "RESERVADO".equals(status);
    }
    public boolean isInativo(){
        return "INATIVO".equals(status);
    }

    // NEGÓCIOS

    public void reservar(){
        if(!isDisponivel()){
            throw new IllegalStateException("Imovel não está disponivel");
        }
        this.status = "RESERVADO";
        this.atualizadoEm = LocalDateTime.now();
    }

    public void vender(){
        if(!isDisponivel() && !isReservado()){
            throw new IllegalStateException("Imovel não pode ser vendido");
        }
        this.status = "VENDIDO";
        this.atualizadoEm = LocalDateTime.now();
    }
    public void cancelarReserva(){
        if (!isReservado()){
            throw new IllegalStateException("Imovel não reservado");
        }
        this.status = "DISPONIVEL";
        this.atualizadoEm = LocalDateTime.now();
    }
    public void inativar(){
        if(!isVendido()){
            throw new IllegalStateException("O imovel já foi vendido");
        }
        this.status = "INATIVO";
        this.atualizadoEm = LocalDateTime.now();
    }
    public void reativar() {
        if (!isInativo()) {
            throw new IllegalStateException("Apenas imóveis inativos podem ser reativados");
        }
        this.status = "DISPONIVEL";
        this.atualizadoEm = LocalDateTime.now();
    }

    //CALCULOS
    public BigDecimal calcularValorFinanciado(double percentualEntrada) {
        if (percentualEntrada < 0 || percentualEntrada > 1) {
            throw new IllegalArgumentException("Percentual de entrada deve estar entre 0 e 1 (0% a 100%)");
        }
        return preco.multiply(BigDecimal.valueOf(1 - percentualEntrada));
    }

    public BigDecimal calcularValorEntrada(double percentualEntrada) {
        if (percentualEntrada < 0 || percentualEntrada > 1) {
            throw new IllegalArgumentException("Percentual de entrada deve estar entre 0 e 1 (0% a 100%)");
        }
        return preco.multiply(BigDecimal.valueOf(percentualEntrada));
    }

    public BigDecimal calcularValorM2() {
        if (areaConstruida == null || areaConstruida <= 0) {
            throw new IllegalStateException("Área construída deve estar preenchida para calcular valor por m²");
        }
        return preco.divide(BigDecimal.valueOf(areaConstruida), 2, BigDecimal.ROUND_HALF_UP);
    }

    public double calcularPercentualConstruido() {
        if (areaTotal == null || areaTotal <= 0 || areaConstruida == null || areaConstruida <= 0) {
            return 0.0;
        }
        return (areaConstruida / areaTotal) * 100;
    }

    // ATUALIZAR (PREÇO E DESCRIÇÃO)

    public void atualizarPreco(BigDecimal novoPreco) {
        if (novoPreco == null || novoPreco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Novo preço deve ser maior que zero");
        }
        if (isVendido()) {
            throw new IllegalStateException("Não é possível alterar preço de imóvel vendido");
        }
        this.preco = novoPreco;
        this.atualizadoEm = LocalDateTime.now();
    }

    public void atualizarDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
        this.atualizadoEm = LocalDateTime.now();
    }

    //Formatação

    public String getCepFormatado() {
        if (cep == null || cep.length() != 8) return cep;
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }

    public String getCodigoFormatado() {
        return codigo != null ? codigo.toUpperCase() : "";
    }

    public String getResumoImovel() {
        StringBuilder sb = new StringBuilder();
        sb.append(tipo != null ? tipo : "Imóvel");
        if (quartos != null && quartos > 0) sb.append(" - ").append(quartos).append(" quartos");
        if (banheiros != null && banheiros > 0) sb.append(" - ").append(banheiros).append(" banheiros");
        if (areaConstruida != null) sb.append(" - ").append(areaConstruida).append("m²");
        return sb.toString();
    }


    private static String gerarCodigo() {
        return "IMV-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

}
