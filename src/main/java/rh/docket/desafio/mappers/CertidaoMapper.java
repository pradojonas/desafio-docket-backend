package rh.docket.desafio.mappers;

import org.mapstruct.factory.Mappers;

import rh.docket.desafio.dto.CertidaoDTO;
import rh.docket.desafio.model.Certidao;

public interface CertidaoMapper {
    CertidaoMapper INSTANCE = Mappers.getMapper(CertidaoMapper.class);

    Certidao fromDto(CertidaoDTO dto);  
    
    CertidaoDTO fromEntity(Certidao a);

}