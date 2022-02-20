package rh.docket.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rh.docket.desafio.model.Certidao;

@Repository
public interface CertidaoRepository extends JpaRepository<Certidao, Long> {

}