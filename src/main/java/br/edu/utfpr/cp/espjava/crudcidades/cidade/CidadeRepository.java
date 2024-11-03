package br.edu.utfpr.cp.espjava.crudcidades.cidade;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<CidadeEntidade, Long> {
    public Optional<CidadeEntidade> findByNomeAndEstado(String nome, String Estado);
}
