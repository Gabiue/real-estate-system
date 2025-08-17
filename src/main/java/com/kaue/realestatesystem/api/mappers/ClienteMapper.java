package com.kaue.realestatesystem.api.mappers;

import com.kaue.realestatesystem.api.dto.ClienteDTO;
import com.kaue.realestatesystem.api.dto.CriarClienteRequest;
import com.kaue.realestatesystem.api.dto.AtualizarClienteRequest;
import com.kaue.realestatesystem.domain.entities.Cliente;
import org.springframework.stereotype.Component;

import java.lang.ref.Cleaner;

@Component
public class ClienteMapper {

   public Cliente toEntity(CriarClienteRequest request){
       if(request == null){
           return null;
       }
       String cpfLimpo = request.getCpf();
       if (cpfLimpo != null) {
           cpfLimpo = cpfLimpo.replaceAll("[^0-9]", "");
       }
       return Cliente.builder()
               .nome(request.getNome())
               .cpf(cpfLimpo)
               .email(request.getEmail())
               .telefone(request.getTelefone())
               .dataNascimento(request.getDataNascimento())
               .build();
   }

   public ClienteDTO toDTO(Cliente cliente){
       if(cliente == null){
           return null;
       }
       ClienteDTO dto = new ClienteDTO();
       dto.setId(cliente.getId());
       dto.setNome(cliente.getNome());
       dto.setEmail(cliente.getEmail());
       dto.setDataNascimento(cliente.getDataNascimento());
       dto.setTelefone(cliente.getTelefone());
       dto.setCriadoEm(cliente.getCriadoEm());
       dto.setAtualizadoEm(cliente.getAtualizadoEm());

       return dto;
   }
   public void updateEntity(Cliente cliente, AtualizarClienteRequest request){
       if(cliente == null || request == null){
           return;
       }

       cliente.atualizarDados(
               request.getNome(),
               request.getTelefone(),
               request.getEmail(),
               null
       );

   }
    private String limparCpf(String cpfFormatado) {
        if (cpfFormatado == null) {
            return null;
        }
        return cpfFormatado.replaceAll("[^0-9]", "");
    }

}
