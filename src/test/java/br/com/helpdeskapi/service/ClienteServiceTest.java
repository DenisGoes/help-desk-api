package br.com.helpdeskapi.service;

import br.com.helpdeskapi.exception.ClientePossuiChamadosException;
import br.com.helpdeskapi.exception.EmailJaCadastradoException;
import br.com.helpdeskapi.model.Cliente;
import br.com.helpdeskapi.repository.ChamadoRepository;
import br.com.helpdeskapi.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ChamadoRepository chamadoRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Deve cadastrar cliente com sucesso!")
    void cadastrarCliente() {
        //arrange
        Cliente cliente = new Cliente();
        cliente.setNome("Denis");
        cliente.setTelefone("1111");
        cliente.setEmail("denis33@gmail.com");

        when(clienteRepository.existsByEmail(cliente.getEmail())).thenReturn(false);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        //act - executar
        Cliente res = clienteService.cadastrarCliente(cliente);

        //assert - verificar
        assertNotNull(res);
        assertEquals("Denis", res.getNome());
        assertEquals("1111", res.getTelefone());
        assertEquals("denis33@gmail.com", res.getEmail());

        verify(clienteRepository, times(1)).existsByEmail(cliente.getEmail());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Deve listar todos os clientes com sucesso")
    void listarClientes() {
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Denis");

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Jose");

        List<Cliente> clientes = List.of(cliente1, cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> res = clienteService.listarClientes();


        assertNotNull(res);
        assertEquals(2, res.size());
        assertEquals("Denis", res.get(0).getNome());
        assertEquals("Jose", res.get(1).getNome());

        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Nao Deve Cadastrar Cliente Com Email Duplicado")
    void naoDeveCadastrarClienteComEmailDuplicado(){
        Cliente cliente = new Cliente();
        cliente.setEmail("denisgoes21@gmail.com");

        when(clienteRepository.existsByEmail(cliente.getEmail())).thenReturn(true);

        assertThrows(EmailJaCadastradoException.class, () -> clienteService.cadastrarCliente(cliente));

        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve buscar cliente por id com sucesso")
    void buscarClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente res = clienteService.buscarClientePorId(1L);

        assertNotNull(res);
        assertEquals(1L, res.getId());

        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar cliente com sucesso")
    void atualizarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Denis");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente res = clienteService.atualizarCliente(cliente, 1L);

        assertNotNull(res);
        assertEquals(1L, res.getId());
        assertEquals("Denis", res.getNome());

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void deletarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(chamadoRepository.existsByClienteId(1L)).thenReturn(false);

        clienteService.deletarCliente(1L);

        verify(clienteRepository, times(1)).findById(1L);
        verify(chamadoRepository, times(1)).existsByClienteId(1L);
        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    @DisplayName("Não deve deletar cliente que possui chamado")
    void naoDeveDeletarClienteComChamados(){
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(chamadoRepository.existsByClienteId(1L)).thenReturn(true);

        assertThrows(ClientePossuiChamadosException.class,
                () -> clienteService.deletarCliente(1L));

        verify(clienteRepository, never()).delete(any(Cliente.class));

    }
}