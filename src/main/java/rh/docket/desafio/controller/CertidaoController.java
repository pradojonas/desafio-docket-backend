package rh.docket.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import rh.docket.desafio.dto.CertidaoDTO;
import rh.docket.desafio.exceptions.MappedException;
import rh.docket.desafio.service.CertidaoService;

@RestController
@RequestMapping("/v1/certidao")
public class CertidaoController {

    @Autowired
    CertidaoService service;
    
    @ApiOperation(value="Lista possíveis certidões para cartórios", response = CertidaoDTO.class)
    @GetMapping
    List<CertidaoDTO> list() throws MappedException {
        return service.listFromApi();
    }
    
}