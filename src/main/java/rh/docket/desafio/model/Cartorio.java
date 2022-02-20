package rh.docket.desafio.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Cartorio ")
public class Cartorio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @NotNull
    @Column(name = "endereco", nullable = false)
    private String endereco;
    
    @ManyToMany
    @JoinTable(
      name = "cartorio_certidao", 
      joinColumns = @JoinColumn(name = "cartorio_id"), 
      inverseJoinColumns = @JoinColumn(name = "certidao_id"))
    Set<Certidao> certidoesOferecidas;  

}
