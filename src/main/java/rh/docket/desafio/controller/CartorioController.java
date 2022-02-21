package rh.docket.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import rh.docket.desafio.dto.CartorioDTO;
import rh.docket.desafio.model.Cartorio;
import rh.docket.desafio.service.CartorioService;

@RestController
@RequestMapping("/v1/cartorio")
public class CartorioController {

    @Autowired
    CartorioService service;
    
    @ApiOperation(value="Lists all Voting Sessions", response = CartorioDTO.class)
    @GetMapping
    List<CartorioDTO> list() {
        return service.list();
    }
    
}
