package rh.docket.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import rh.docket.desafio.dto.CertidaoDTO;
import rh.docket.desafio.exceptions.MappedException;
import rh.docket.desafio.mappers.CertidaoMapper;
import rh.docket.desafio.model.Certidao;
import rh.docket.desafio.repository.CertidaoRepository;
import rh.docket.desafio.service.external.CertidaoApiService;

@Service
public class CertidaoService {

    @Autowired
    private CertidaoRepository certidaoRepo;
    
    @Autowired
    private CertidaoApiService certidaoApiService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public List<CertidaoDTO> list() {
        var modelList = certidaoRepo.findAll();
        var dtoList   = modelList.stream()
                                 .map(entity -> CertidaoMapper.INSTANCE.fromEntity(entity))
                                 .collect(Collectors.toList());
        return dtoList;
    }
    
    public List<CertidaoDTO> listFromApi() throws MappedException {
        var modelList = certidaoApiService.consultaCertidoesUsingAPI();
        return modelList;
    }
    
    public CertidaoDTO getByIdFromApi(Long idConsultado) throws MappedException {
        var modelList = certidaoApiService.findById(idConsultado);
        return modelList;
    }

    public CertidaoDTO getById(Long idCertidao) throws MappedException {
        var entity = certidaoRepo.findById(idCertidao)
                                 .orElseThrow(() -> new MappedException("Certidão não encontrada.",
                                                                        HttpStatus.NOT_FOUND));
        var dto    = CertidaoMapper.INSTANCE.fromEntity(entity);
        return dto;
    }

    public CertidaoDTO add(CertidaoDTO newCertidaoDTO) throws MappedException {
        this.validateCertidaoDTO(newCertidaoDTO);
        Certidao newCertidao = CertidaoMapper.INSTANCE.fromDto(newCertidaoDTO); // Transforming DTO in Entity
        certidaoRepo.save(newCertidao);
        return CertidaoMapper.INSTANCE.fromEntity(newCertidao);
    }

    private void validateCertidaoDTO(CertidaoDTO newCertidaoDTO) throws MappedException {
        if (Strings.isNullOrEmpty(newCertidaoDTO.getNome().trim()))
            throw new MappedException("Cadastro de cartório exige um nome válido.", HttpStatus.BAD_REQUEST);
    }

}
