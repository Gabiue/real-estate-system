package com.kaue.realestatesystem.application.services;

import com.kaue.realestatesystem.api.dto.AtualizarImovelRequest;
import com.kaue.realestatesystem.api.dto.CriarImovelRequest;
import com.kaue.realestatesystem.api.dto.ImovelDTO;
import com.kaue.realestatesystem.api.mappers.ImovelMapper;
import com.kaue.realestatesystem.domain.entities.Imovel;
import com.kaue.realestatesystem.domain.repositories.ImovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImovelService {

    private final ImovelRepository imovelRepository;
    private final ImovelMapper imovelMapper;

    public ImovelDTO cadastrarImovel(CriarImovelRequest request) {
        Imovel imovel = imovelMapper.toEntity(request);
        Imovel imovelSalvo = imovelRepository.salvar(imovel);
        return imovelMapper.toDTO(imovelSalvo);
    }

    public List<ImovelDTO> listarImoveis(String tipo, BigDecimal precoMin, BigDecimal precoMax, Integer quartos, String status) {
        if (precoMin != null && precoMax != null) {
            List<Imovel> imoveis = imovelRepository.buscarPorFaixaPreco(precoMin, precoMax);
            return imoveis.stream()
                    .filter(imovel -> tipo == null || tipo.equals(imovel.getTipo()))
                    .filter(imovel -> quartos == null || quartos.equals(imovel.getQuartos()))
                    .filter(imovel -> status == null || status.equals(imovel.getStatus()))
                    .map(imovelMapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            List<Imovel> imoveis = imovelRepository.listarDisponiveis();
            return imoveis.stream()
                    .filter(imovel -> tipo == null || tipo.equals(imovel.getTipo()))
                    .filter(imovel -> precoMin == null || imovel.getPreco().compareTo(precoMin) >= 0)
                    .filter(imovel -> precoMax == null || imovel.getPreco().compareTo(precoMax) <= 0)
                    .filter(imovel -> quartos == null || quartos.equals(imovel.getQuartos()))
                    .filter(imovel -> status == null || status.equals(imovel.getStatus()))
                    .map(imovelMapper::toDTO)
                    .collect(Collectors.toList());
        }
    }

    public ImovelDTO buscarImovelPorId(Long id) {
        Imovel imovel = imovelRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));
        return imovelMapper.toDTO(imovel);
    }

    public ImovelDTO atualizarImovel(Long id, AtualizarImovelRequest request) {
        Imovel imovel = imovelRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

        imovelMapper.updateEntity(imovel, request);
        Imovel imovelAtualizado = imovelRepository.salvar(imovel);
        return imovelMapper.toDTO(imovelAtualizado);
    }

    public void deletarImovel(Long id) {
        imovelRepository.deletar(id);
    }

    public ImovelDTO buscarImovelPorCodigo(String codigo) {
        List<Imovel> imoveis = imovelRepository.listarDisponiveis();
        Imovel imovel = imoveis.stream()
                .filter(i -> codigo.equals(i.getCodigo()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));
        return imovelMapper.toDTO(imovel);
    }

    public List<ImovelDTO> listarImoveisDisponiveis() {
        List<Imovel> imoveis = imovelRepository.listarDisponiveis();
        return imoveis.stream()
                .map(imovelMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ImovelDTO alterarStatusImovel(Long id, String acao) {
        Imovel imovel = imovelRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

        switch (acao.toUpperCase()) {
            case "RESERVAR" -> imovel.reservar();
            case "VENDER" -> imovel.vender();
            case "CANCELAR_RESERVA" -> imovel.cancelarReserva();
            case "INATIVAR" -> imovel.inativar();
            case "REATIVAR" -> imovel.reativar();
            default -> throw new IllegalArgumentException("Ação inválida: " + acao);
        }

        Imovel imovelAtualizado = imovelRepository.salvar(imovel);
        return imovelMapper.toDTO(imovelAtualizado);
    }

    public List<ImovelDTO> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        List<Imovel> imoveis = imovelRepository.buscarPorFaixaPreco(precoMin, precoMax);
        return imoveis.stream()
                .map(imovelMapper::toDTO)
                .collect(Collectors.toList());
    }
}
