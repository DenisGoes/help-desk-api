package br.com.helpdeskapi.service;

import br.com.helpdeskapi.model.Chamado;
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
class ChamadoServiceTest {

    @Mock
    private ChamadoRepository chamadoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ChamadoService chamadoService;

    @Test
    @DisplayName("Deve abrir chamado com sucesso")
    void abrirChamado() {
        Chamado chamado = new Chamado();
        chamado.setTitulo("Problema de tela azul");

        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        when(chamadoRepository.save(chamado)).thenReturn(chamado);


        Chamado res = chamadoService.abrirChamado(chamado, 1L);

        assertNotNull(res);
        assertEquals("Problema de tela azul", res.getTitulo());
        assertEquals(cliente, res.getCliente());

        verify(clienteRepository, times(1)).findById(1L);
        verify(chamadoRepository, times(1)).save(chamado);

    }

    @Test
    @DisplayName("Deve listar todos os chamados com sucesso")
    void listarChamados() {
        Chamado chamado1 = new Chamado();
        chamado1.setTitulo("Problema no teclado");

        Chamado chamado2 = new Chamado();
        chamado2.setTitulo("Problema na impressora");

        List<Chamado> chamados = List.of(chamado1, chamado2);

        when(chamadoRepository.findAll()).thenReturn(chamados);

        List<Chamado> res = chamadoService.listarChamados();

        assertEquals(2, res.size());
        assertEquals("Problema no teclado", res.get(0).getTitulo());
        assertEquals("Problema na impressora", res.get(1).getTitulo());

        verify(chamadoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar chamado por id com sucesso")
    void buscarChamadoPorId() {
        Chamado chamado = new Chamado();
        chamado.setId(1L);

        when(chamadoRepository.findById(1L)).thenReturn(Optional.of(chamado));

        chamadoService.buscarChamadoPorId(1L);

        verify(chamadoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar chamado com sucesso")
    void atualizarChamado() {
        Chamado chamado = new Chamado();
        chamado.setId(1L);
        chamado.setTitulo("Problema com a net");

        Cliente cliente = new Cliente();
        cliente.setId(2L);

        when(chamadoRepository.findById(1l)).thenReturn(Optional.of(chamado));
        when(clienteRepository.findById(2L)).thenReturn(Optional.of(cliente));
        when(chamadoRepository.save(chamado)).thenReturn(chamado);

        Chamado res = chamadoService.atualizarChamado(chamado, 1L, 2L);

        assertNotNull(res);
        assertEquals(1L, res.getId());
        assertEquals("Problema com a net", res.getTitulo());
        assertEquals(cliente, res.getCliente());

        verify(chamadoRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).findById(2L);
        verify(chamadoRepository, times(1)).save(chamado);
    }

    @Test
    @DisplayName("Deve deletar chamado com sucesso")
    void deletarChamado() {
        Chamado chamado = new Chamado();
        chamado.setId(1L);

        when(chamadoRepository.findById(1L)).thenReturn(Optional.of(chamado));

        chamadoService.deletarChamado(1L);

        verify(chamadoRepository, times(1)).findById(1L);
    }
}