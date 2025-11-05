package com.marcenaria.sightcut.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "material")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private String nome;
    private Double espessura;
    private String tipo;
    private String cor;
    private Double precoUnitario;
    private String unidade;
}
