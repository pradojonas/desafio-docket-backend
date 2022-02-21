package rh.docket.desafio.mappers;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rh.docket.desafio.dto.CartorioDTO;
import rh.docket.desafio.model.Cartorio;
import rh.docket.desafio.repository.CartorioRepository;

@Component
public class CartorioMapperResolver {

    @Autowired
    private CartorioRepository cartorioRepository;

    @ObjectFactory
    public Cartorio resolve(CartorioDTO dto, @TargetType Class<Cartorio> type) {
        if (dto != null
            && dto.getId() != null) {
            var result = cartorioRepository.findById(dto.getId());
            return result.orElse(null);
        }
        return null;
    }

}