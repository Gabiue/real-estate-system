package com.kaue.realestatesystem.application.services;

import com.kaue.realestatesystem.api.dto.CriarPropostarRequest;
import com.kaue.realestatesystem.api.dto.PropostaDTO;
import com.kaue.realestatesystem.api.mappers.PropostaMapper;
import com.kaue.realestatesystem.domain.entities.Proposta;
import com.kaue.realestatesystem.domain.enums.StatusProposta;
import com.kaue.realestatesystem.domain.repositories.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final PropostaMapper propostaMapper;

    public PropostaDTO criarProposta(CriarPropostarRequest request) {
        Proposta proposta = propostaMapper.toEntity(request);
        Proposta propostaSalva = propostaRepository.salvar(proposta);
        return propostaMapper.toDTO(propostaSalva);
    }

    public List<PropostaDTO> listarPropostas(String status, Long clienteId, Long imovelId, BigDecimal valorMin, BigDecimal valorMax, boolean incluirExpiradas) {
        List<Proposta> propostas;

        if (status != null) {
            StatusProposta statusEnum = propostaMapper.stringToStatusEnum(status);
            propostas = propostaRepository.listarPorStatus(statusEnum);
        } else if (clienteId != null) {
            propostas = propostaRepository.listarPorCliente(clienteId);
        } else if (imovelId != null) {
            propostas = propostaRepository.listarPorImovel(imovelId);
        } else {
            propostas = propostaRepository.listarTodos();
        }

        return propostas.stream()
                .filter(proposta -> valorMin == null || proposta.getValorProposta().compareTo(valorMin) >= 0)
                .filter(proposta -> valorMax == null || proposta.getValorProposta().compareTo(valorMax) <= 0)
                .filter(proposta -> incluirExpiradas || !proposta.isExpirada())
                .map(propostaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PropostaDTO buscarPropostaPorId(Long id) {
        Proposta proposta = propostaRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Proposta não encontrada"));
        return propostaMapper.toDTO(proposta);
    }

    public PropostaDTO buscarPropostaPorNumero(String numero) {
        Proposta proposta = propostaRepository.buscarPorNumero(numero)
                .orElseThrow(() -> new RuntimeException("Proposta não encontrada"));
        return propostaMapper.toDTO(proposta);
    }

    public PropostaDTO aprovarProposta(Long id) {
        Proposta proposta = propostaRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Proposta não encontrada"));

        proposta.aprovar();
        Proposta propostaAtualizada = propostaRepository.salvar(proposta);
        return propostaMapper.toDTO(propostaAtualizada);
    }

    public PropostaDTO rejeitarProposta(Long id, String motivo) {
        Proposta proposta = propostaRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Proposta não encontrada"));

        proposta.rejeitar(motivo);
        Proposta propostaAtualizada = propostaRepository.salvar(proposta);
        return propostaMapper.toDTO(propostaAtualizada);
    }

    public PropostaDTO cancelarProposta(Long id) {
        Proposta proposta = propostaRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Proposta não encontrada"));

        proposta.cancelar();
        Proposta propostaAtualizada = propostaRepository.salvar(proposta);
        return propostaMapper.toDTO(propostaAtualizada);
    }

    public PropostaDTO atualizarObservacoes(Long id, String observacoes) {
        Proposta proposta = propostaRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Proposta não encontrada"));

        propostaMapper.updateObservacoes(proposta, observacoes);
        Proposta propostaAtualizada = propostaRepository.salvar(proposta);
        return propostaMapper.toDTO(propostaAtualizada);
    }

    public PropostaDTO estenderValidade(Long id, int dias) {
        Proposta proposta = propostaRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Proposta não encontrada"));

        proposta.estenderValidade(dias);
        Proposta propostaAtualizada = propostaRepository.salvar(proposta);
        return propostaMapper.toDTO(propostaAtualizada);
    }

    public List<PropostaDTO> listarPropostasDoCliente(Long clienteId) {
        List<Proposta> propostas = propostaRepository.listarPorCliente(clienteId);
        return propostas.stream()
                .map(propostaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PropostaDTO> listarPropostasDoImovel(Long imovelId) {
        List<Proposta> propostas = propostaRepository.listarPorImovel(imovelId);
        return propostas.stream()
                .map(propostaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SimulacaoDTO simularFinanciamento(BigDecimal valorImovel, BigDecimal valorEntrada, int quantidadeParcelas, double taxaJuros) {
        BigDecimal valorFinanciado = valorImovel.subtract(valorEntrada);
        BigDecimal percentualEntrada = valorEntrada.divide(valorImovel, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));

        BigDecimal valorParcela;
        BigDecimal valorTotalComJuros;

        if (taxaJuros <= 0) {
            valorParcela = valorFinanciado.divide(BigDecimal.valueOf(quantidadeParcelas), 2, BigDecimal.ROUND_HALF_UP);
            valorTotalComJuros = valorImovel;
        } else {
            double taxaJurosMensal = taxaJuros / 12 / 100;
            double fator = Math.pow(1 + taxaJurosMensal, quantidadeParcelas);
            double valorParcelaCalculado = valorFinanciado.doubleValue() * (fator * taxaJurosMensal) / (fator - 1);

            valorParcela = BigDecimal.valueOf(valorParcelaCalculado).setScale(2, BigDecimal.ROUND_HALF_UP);
            valorTotalComJuros = valorParcela.multiply(BigDecimal.valueOf(quantidadeParcelas)).add(valorEntrada);
        }

        SimulacaoDTO simulacao = new SimulacaoDTO();
        simulacao.valorImovel = valorImovel;
        simulacao.valorEntrada = valorEntrada;
        simulacao.valorFinanciado = valorFinanciado;
        simulacao.percentualEntrada = percentualEntrada;
        simulacao.quantidadeParcelas = quantidadeParcelas;
        simulacao.valorParcela = valorParcela;
        simulacao.taxaJuros = taxaJuros;
        simulacao.valorTotalComJuros = valorTotalComJuros;

        return simulacao;
    }

    public static class SimulacaoDTO {
        public BigDecimal valorImovel;
        public BigDecimal valorEntrada;
        public BigDecimal valorFinanciado;
        public BigDecimal percentualEntrada;
        public int quantidadeParcelas;
        public BigDecimal valorParcela;
        public double taxaJuros;
        public BigDecimal valorTotalComJuros;
    }
}