package com.kaue.realestatesystem.domain.repositories;
import com.kaue.realestatesystem.domain.entities.Imovel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ImovelRepository {

    Imovel salvar(Imovel imovel);

    Optional<Imovel> buscarPorId(Long id);

    List<Imovel> listarDisponiveis();

    List<Imovel> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax);

    boolean existePorCodigo (String codigo);

    void deletar(Long id);

}
