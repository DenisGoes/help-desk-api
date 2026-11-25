package br.com.helpdeskapi.service;

import br.com.helpdeskapi.exception.ClientePossuiChamadosException;
import br.com.helpdeskapi.exception.EmailJaCadastradoException;
import br.com.helpdeskapi.exception.RecursoNaoEncontradoException;
import br.com.helpdeskapi.model.Cliente;
import br.com.helpdeskapi.repository.ChamadoRepository;
import br.com.helpdeskapi.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ChamadoRepository chamadoRepository;

    public Cliente cadastrarCliente(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())){
            throw new EmailJaCadastradoException("Já existe um cliente cadastrado com esse email.");
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));
    }

    public Cliente atualizarCliente(Cliente cliente, Long id){
        Cliente clienteAtualizado = buscarClientePorId(id);

        clienteAtualizado.setNome(cliente.getNome());
        clienteAtualizado.setEmail(cliente.getEmail());
        clienteAtualizado.setTelefone(cliente.getTelefone());

        return clienteRepository.save(clienteAtualizado);
    }

    public void deletarCliente(Long id){
        Cliente cliente = buscarClientePorId(id);

        if(chamadoRepository.existsByClienteId(id)){
            throw new ClientePossuiChamadosException("Não é possivel excluir o cliente, pois existem chamados vinculados");
        }

        clienteRepository.delete(cliente);
    }

}
