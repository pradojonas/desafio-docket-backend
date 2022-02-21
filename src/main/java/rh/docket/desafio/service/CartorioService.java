package rh.docket.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import rh.docket.desafio.dto.CartorioDTO;
import rh.docket.desafio.dto.NovoCartorioDTO;
import rh.docket.desafio.exceptions.MappedException;
import rh.docket.desafio.mappers.CartorioMapper;
import rh.docket.desafio.model.Cartorio;
import rh.docket.desafio.repository.CartorioRepository;

@Service
public class CartorioService {

    @Autowired
    private CartorioRepository cartorioRepo;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public List<CartorioDTO> list() {
        var modelList = cartorioRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> CartorioMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }
    
    public CartorioDTO getById(Long idCartorio) throws MappedException {
        var entity = cartorioRepo.findById(idCartorio).orElseThrow(() -> new MappedException("Cartório não encontrado.",
                HttpStatus.NOT_FOUND));
        var dto = CartorioMapper.INSTANCE.fromEntity(entity);
        return dto;
    }

    public CartorioDTO add(NovoCartorioDTO newCartorioDTO) throws MappedException {
        this.validateCartorioDTO(newCartorioDTO);
        Cartorio newCartorio = CartorioMapper.INSTANCE.fromDto(newCartorioDTO); // Transforming DTO in Entity
        cartorioRepo.save(newCartorio);
        return CartorioMapper.INSTANCE.fromEntity(newCartorio);
    }

    private void validateCartorioDTO(NovoCartorioDTO newCartorioDTO) throws MappedException {
        if (Strings.isNullOrEmpty(newCartorioDTO.getNome().trim()))
            throw new MappedException("Cadastro de cartório exige um nome válido.", HttpStatus.BAD_REQUEST);
        if (Strings.isNullOrEmpty(newCartorioDTO.getEndereco().trim()))
            throw new MappedException("Cadastro de cartório exige um nome válido.", HttpStatus.BAD_REQUEST);
    }

}
