package com.kaue.realestatesystem.domain.repositories;
import com.kaue.realestatesystem.domain.entities.Cliente;
import com.kaue.realestatesystem.domain.entities.Proposta;
import com.kaue.realestatesystem.domain.enums.StatusProposta;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository {

    Proposta salvar (Proposta proposta);

    Optional<Proposta> buscarPorId(Long id);

    Optional<Proposta> buscarPorNumero(String numero);

    List<Proposta> listarTodos();

    List<Proposta> listarPorStatus(StatusProposta status);

    List<Proposta> listarPorCliente(Long ClienteId);

    List<Proposta> listarPorImovel(Long ImovelId);

    List<Proposta> buscarPropostarExpiradas();

    boolean existePorNumero(String numero);

    void deletar(Long id);
}

