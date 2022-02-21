package rh.docket.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rh.docket.desafio.model.Certidao;

@Repository
public interface CertidaoRepository extends JpaRepository<Certidao, Long> {

    List<Certidao> findByIdIn(List<Long> idsCertidoes);

}