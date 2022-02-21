package rh.docket.desafio.service.external;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import rh.docket.desafio.config.ApplicationProperties;
import rh.docket.desafio.dto.CertidaoDTO;
import rh.docket.desafio.exceptions.MappedException;

@Service
public class CertidaoApiService {

    @Autowired
    private ApplicationProperties appProps;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public List<CertidaoDTO> consultaCertidoesUsingAPI() throws MappedException {
        String url = appProps.getCertidoesApiUrl();

        try {
            WebClient webClient = WebClient.builder()
                                           .baseUrl(url)
                                           .defaultHeader(HttpHeaders.CONTENT_TYPE,
                                                          MediaType.APPLICATION_JSON_VALUE)
                                           .build();

            Mono<List<CertidaoDTO>> response  = webClient.get()
                                                         .accept(MediaType.APPLICATION_JSON)
                                                         .retrieve()
                                                         .bodyToMono(new ParameterizedTypeReference<List<CertidaoDTO>>() {
                                                                                           })
                                                         .timeout(Duration.ofSeconds(appProps.getCpfApiTimeout()));
            List<CertidaoDTO>       certidoes = response.block();
            LOGGER.info(String.format("Resultado da consulta de certidoes: {%s}", certidoes.toString()));
            return certidoes;
        } catch (RuntimeException e) {
            // Thrown by 'block()'
            var cause = Exceptions.unwrap(e);
            if (cause instanceof WebClientRequestException
                || cause instanceof TimeoutException)
                throw new MappedException(String.format("O serviço de consulta de certidões (%s) não está respondendo. Por favor, entre em contato com a equipe de desenvolvimento.",
                                                        url), HttpStatus.SERVICE_UNAVAILABLE);
        }
        LOGGER.error("Erro ao consultar API de certidões");
        throw new MappedException(String.format("Erro ao consultar API de certidões.", url),
                                  HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
