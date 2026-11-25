package br.com.helpdeskapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "chamado")
@Getter
@Setter
@NoArgsConstructor
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "É obrigatório o chamado possuir um titulo")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "É obrigatório o chamado possuir uma descrição")
    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusChamado statusChamado = StatusChamado.ABERTO;

    @Enumerated(EnumType.STRING)
    private PrioridadeChamado prioridadeChamado = PrioridadeChamado.MEDIA;

    private LocalDateTime dataAbertura;

    @PrePersist
    public void prePersist(){
        this.dataAbertura = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
