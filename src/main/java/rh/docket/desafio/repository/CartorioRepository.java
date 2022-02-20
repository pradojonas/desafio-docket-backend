package rh.docket.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rh.docket.desafio.model.Cartorio;

@Repository
public interface CartorioRepository extends JpaRepository<Cartorio, Long> {

}