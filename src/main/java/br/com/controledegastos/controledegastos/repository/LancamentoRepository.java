package br.com.controledegastos.controledegastos.repository;

import br.com.controledegastos.controledegastos.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}