package br.com.helpdeskapi.repository;

import br.com.helpdeskapi.model.Chamado;
import br.com.helpdeskapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    boolean existsByClienteId(Long clienteId);

    List<Chamado> id(Long id);
}
