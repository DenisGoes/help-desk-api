package br.com.helpdeskapi.service;

import br.com.helpdeskapi.exception.RecursoNaoEncontradoException;
import br.com.helpdeskapi.model.Chamado;
import br.com.helpdeskapi.model.Cliente;
import br.com.helpdeskapi.repository.ChamadoRepository;
import br.com.helpdeskapi.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@AllArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final ClienteRepository clienteRepository;

    public Chamado abrirChamado(Chamado chamado, Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()
                        -> new RecursoNaoEncontradoException("Recurso não encontrado!"));

        chamado.setCliente(cliente);

        return chamadoRepository.save(chamado);
    }

    public List<Chamado> listarChamados(){
        return chamadoRepository.findAll();
    }

    public Chamado buscarChamadoPorId(Long id){
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Chamado não encontrado."));
    }

    public Chamado atualizarChamado(Chamado chamado, Long id, Long clienteId){
        Chamado chamadoAtualizado = buscarChamadoPorId(id);

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(()
                        -> new RecursoNaoEncontradoException("Cliente não encontrado"));

        chamadoAtualizado.setTitulo(chamado.getTitulo());
        chamadoAtualizado.setDescricao(chamado.getDescricao());
        chamadoAtualizado.setStatusChamado(chamado.getStatusChamado());
        chamadoAtualizado.setPrioridadeChamado(chamado.getPrioridadeChamado());
        chamadoAtualizado.setCliente(cliente);

        return chamadoRepository.save(chamadoAtualizado);
    }

    public void deletarChamado(Long id){
        Chamado chamado = buscarChamadoPorId(id);

        chamadoRepository.delete(chamado);
    }

}
