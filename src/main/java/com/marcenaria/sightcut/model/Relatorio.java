package com.marcenaria.sightcut.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "relatorio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    @ManyToOne @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String caminhoArquivo;
    private LocalDateTime criadoEm = LocalDateTime.now();
}
