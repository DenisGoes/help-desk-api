package br.com.helpdeskapi.controller;

import br.com.helpdeskapi.model.Chamado;
import br.com.helpdeskapi.service.ChamadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamados")
@AllArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;


    @PostMapping
    public ResponseEntity<Chamado> abrirChamado(@RequestBody @Valid Chamado chamado, @RequestParam Long clienteId){
        Chamado novoChamado = chamadoService.abrirChamado(chamado, clienteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoChamado);
    }

    @GetMapping
    public ResponseEntity<List<Chamado>> listarChamados(){
        return ResponseEntity.ok(chamadoService.listarChamados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chamado> buscarChamadoPorId(@PathVariable Long id){
        return ResponseEntity.ok(chamadoService.buscarChamadoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chamado> atualizarChamado(@RequestBody @Valid Chamado chamado, @PathVariable Long id, @RequestParam Long clienteId){
        return ResponseEntity.ok(chamadoService.atualizarChamado(chamado, id, clienteId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarChamado(@PathVariable Long id){
        chamadoService.deletarChamado(id);
        return ResponseEntity.noContent().build();
    }


}
