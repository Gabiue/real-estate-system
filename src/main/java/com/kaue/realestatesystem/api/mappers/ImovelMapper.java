package com.kaue.realestatesystem.api.mappers;

import com.kaue.realestatesystem.api.dto.ImovelDTO;
import com.kaue.realestatesystem.api.dto.CriarImovelRequest;
import com.kaue.realestatesystem.api.dto.AtualizarImovelRequest;
import com.kaue.realestatesystem.domain.entities.Imovel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class ImovelMapper {

    public Imovel toEntity(CriarImovelRequest request){
        if(request == null){
            return null;
        }
        return Imovel.builder()
                .tipo(request.getTipo())
                .endereco(request.getEndereco())
                .preco(request.getPreco())
                .quartos(request.getQuartos())
                .descricao(request.getDescricao())
                .areaConstruida(request.getArea()!= null ? request.getArea().doubleValue() : null)

                .build();
    }
    public ImovelDTO toDTO(Imovel imovel) {
        if (imovel == null) {
            return null;
        }

        ImovelDTO dto = new ImovelDTO();
        dto.setId(imovel.getId());
        dto.setCodigo(imovel.getCodigo());
        dto.setTipo(imovel.getTipo());
        dto.setEndereco(imovel.getEndereco());
        dto.setPreco(imovel.getPreco());
        dto.setQuartos(imovel.getQuartos());
        dto.setDescricao(imovel.getDescricao());
        dto.setStatus(imovel.getStatus());
        dto.setCriadoEm(imovel.getCriadoEm());
        dto.setAtualizadoEm(imovel.getAtualizadoEm());

        dto.setArea(imovel.getAreaConstruida() != null ?
                BigDecimal.valueOf(imovel.getAreaConstruida()) : null);

        return dto;
    }

    public void updateEntity(Imovel imovel, AtualizarImovelRequest request) {
        if (imovel == null || request == null) {
            return;
        }

        // Campos simples - usando Optional.ifPresent()
        Optional.ofNullable(request.getTipo()).ifPresent(imovel::setTipo);
        Optional.ofNullable(request.getEndereco()).ifPresent(imovel::setEndereco);
        Optional.ofNullable(request.getQuartos()).ifPresent(imovel::setQuartos);

        // Campos com lógica de negócio - mantêm métodos específicos da Entity
        Optional.ofNullable(request.getPreco()).ifPresent(imovel::atualizarPreco);
        Optional.ofNullable(request.getDescricao()).ifPresent(imovel::atualizarDescricao);

        // Campo com conversão de tipo - BigDecimal → Double
        Optional.ofNullable(request.getArea())
                .ifPresent(area -> imovel.setAreaConstruida(area.doubleValue()));

        // Atualiza timestamp (Entity pode não fazer isso em todos os setters)
        imovel.setAtualizadoEm(java.time.LocalDateTime.now());
    }

}

