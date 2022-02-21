package rh.docket.desafio.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import rh.docket.desafio.dto.CartorioDTO;
import rh.docket.desafio.dto.NovoCartorioDTO;
import rh.docket.desafio.model.Cartorio;
import rh.docket.desafio.model.Certidao;

@Mapper(uses = { CartorioMapperResolver.class })
public interface CartorioMapper {
    CartorioMapper INSTANCE = Mappers.getMapper(CartorioMapper.class);

    Cartorio fromDto(NovoCartorioDTO dto);
    
    Cartorio fromDto(CartorioDTO dto); // 'certidoesOferecidas' Handled by CartorioMapperResolver

    @Mapping(source = "certidoesOferecidas", target = "idCertidoes", qualifiedByName = "getIdsCertidoes")
    CartorioDTO fromEntity(Cartorio a);

    @Named("getIdsCertidoes")
    public static Set<Long> getIdsCertidoes(Set<Certidao> certidoes) {
        return CollectionUtils.isEmpty(certidoes) ? null
                                                  : certidoes.stream()
                                                             .map(cert -> cert.getId())
                                                             .collect(Collectors.toSet());
    }

}