package rh.docket.desafio.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import rh.docket.desafio.dto.CartorioDTO;
import rh.docket.desafio.dto.CertidaoDTO;
import rh.docket.desafio.dto.NovoCartorioDTO;
import rh.docket.desafio.exceptions.MappedException;
import rh.docket.desafio.mappers.CartorioMapper;
import rh.docket.desafio.model.Cartorio;
import rh.docket.desafio.model.Certidao;
import rh.docket.desafio.repository.CartorioRepository;

@Service
public class CartorioService {

    @Autowired
    private CartorioRepository cartorioRepo;

    @Autowired
    private CertidaoService certidaoService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public List<CartorioDTO> list() {
        var modelList = cartorioRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> CartorioMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }

    public CartorioDTO getById(Long idCartorio) throws MappedException {
        var entity = cartorioRepo.findById(idCartorio)
                                 .orElseThrow(() -> new MappedException("Cartório não encontrado.",
                                                                        HttpStatus.NOT_FOUND));
        var dto    = CartorioMapper.INSTANCE.fromEntity(entity);
        return dto;
    }

    public CartorioDTO create(NovoCartorioDTO newCartorioDTO) throws MappedException {
        this.validateCartorioDTO(newCartorioDTO);
        Cartorio newCartorio = CartorioMapper.INSTANCE.fromDto(newCartorioDTO); // Transforming DTO in Entity

        cartorioRepo.save(newCartorio);
        return CartorioMapper.INSTANCE.fromEntity(newCartorio);
    }

    private void validateCartorioDTO(NovoCartorioDTO newCartorioDTO) throws MappedException {
        if (newCartorioDTO.getNome() == null || Strings.isNullOrEmpty(newCartorioDTO.getNome().trim()))
            throw new MappedException("Cadastro de cartório exige um nome válido.", HttpStatus.BAD_REQUEST);
        if (newCartorioDTO.getEndereco() == null || Strings.isNullOrEmpty(newCartorioDTO.getEndereco().trim()))
            throw new MappedException("Cadastro de cartório exige um nome válido.", HttpStatus.BAD_REQUEST);
        newCartorioDTO.setNome(newCartorioDTO.getNome().trim());
        newCartorioDTO.setEndereco(newCartorioDTO.getEndereco().trim());
    }

    public CartorioDTO vincularCertidoesCartorio(long idCartorio, List<Long> certidoesIds)
                                                                                           throws MappedException {
        Cartorio cartorioEditado = cartorioRepo.findById(idCartorio)
                                               .orElseThrow(() -> new MappedException("Cartório editado não encontrado na base de dados.",
                                                                                      HttpStatus.NOT_FOUND));

        List<MappedException> errosApiCertidoes = new ArrayList<>();
        var                   certidoes         = certidoesIds.stream().map(id -> {
                                                    try {
                                                        return certidaoService.getByIdFromApi(id);
                                                    } catch (MappedException e) {
                                                        errosApiCertidoes.add(e);
                                                    }
                                                    return null;
                                                }).collect(Collectors.toList());

        if (!errosApiCertidoes.isEmpty()) {
            // Captura erros da consulta ao serviço de certidões
            var message = errosApiCertidoes.stream()
                                           .map(e -> e.getMessage())
                                           .collect(Collectors.joining(";"));
            LOGGER.error("Erro ao obter certidões para cartório.");
            throw new MappedException(message, errosApiCertidoes.get(0).getStatusCode());
        }

        return this.vincularCertidoes(cartorioEditado, certidoes);
    }

    private CartorioDTO vincularCertidoes(Cartorio cartorioEditado, List<CertidaoDTO> certidoesAssociadas)
                                                                                                           throws MappedException {

        List<Long> idsCertidoesCartorio = certidoesAssociadas.stream()
                                                             .map(c -> c.getId())
                                                             .collect(Collectors.toList());

        List<Certidao> certidoesCartorioEmBanco = certidaoService.getFromDatabaseByIds(idsCertidoesCartorio);
        List<Long>     idCertidoesEmBanco       = certidoesCartorioEmBanco.stream()
                                                                          .map(c -> c.getId())
                                                                          .collect(Collectors.toList());

        List<CertidaoDTO> certidoesParaCriacao = certidoesAssociadas.stream()
                                                                    .filter(cert -> !idCertidoesEmBanco.contains(cert.getId()))
                                                                    .collect(Collectors.toList());

        HashSet<Certidao> certidoesCartorio = Sets.newHashSet(certidoesCartorioEmBanco);
        if (!certidoesParaCriacao.isEmpty()) {
            for (CertidaoDTO certidaoDTO : certidoesParaCriacao) {
                var certidaoCriada = certidaoService.add(certidaoDTO);
                certidoesCartorio.add(certidaoCriada);
            }
        }
        cartorioEditado.setCertidoesOferecidas(certidoesCartorio);
        cartorioRepo.save(cartorioEditado);

        var dto = CartorioMapper.INSTANCE.fromEntity(cartorioEditado);
        return dto;
    }

    public void delete(long idCartorio) throws MappedException {
        var entity = cartorioRepo.findById(idCartorio)
                                 .orElseThrow(() -> new MappedException("Cartório não encontrado para remoção.",
                                                                        HttpStatus.NOT_FOUND));
        cartorioRepo.delete(entity);
    }

}