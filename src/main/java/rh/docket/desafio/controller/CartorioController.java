package rh.docket.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import rh.docket.desafio.dto.CartorioDTO;
import rh.docket.desafio.dto.NovoCartorioDTO;
import rh.docket.desafio.exceptions.MappedException;
import rh.docket.desafio.service.CartorioService;

@RestController
@RequestMapping("/v1/cartorio")
public class CartorioController {

    @Autowired
    CartorioService service;

    @ApiOperation(value = "Lista todos os cartórios e suas informações", response = CartorioDTO.class)
    @GetMapping
    List<CartorioDTO> list() {
        return service.list();
    }

    @ApiOperation(value = "Busca Cartório e suas certidões", response = CartorioDTO.class)
    @GetMapping("{idCartorio}")
    CartorioDTO getById(@PathVariable long idCartorio) throws MappedException {
        return service.getById(idCartorio);
    }

    @ApiOperation(value = "Insere um novo cartório", response = String.class) // TODO: retornar URL para consultar recurso criado
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CartorioDTO add(@RequestBody NovoCartorioDTO newCartorioDTO) throws MappedException {
        return service.create(newCartorioDTO);
    }

    @ApiOperation(value = "Atualiza certidões de um cartório", response = String.class) // TODO: retornar URL para consultar recurso criado
    @PostMapping("{idCartorio}/certidoes")
    @ResponseStatus(HttpStatus.CREATED)
    CartorioDTO add(@PathVariable long idCartorio, @RequestBody List<Long> certidoesIds)
                                                                                             throws MappedException {
        return service.vincularCertidoesCartorio(idCartorio, certidoesIds);
    }

}
