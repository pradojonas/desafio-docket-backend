package rh.docket.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rh.docket.desafio.dto.CartorioDTO;
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

}
