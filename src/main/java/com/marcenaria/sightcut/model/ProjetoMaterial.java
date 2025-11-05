package com.marcenaria.sightcut.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "projeto_material")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ProjetoMaterialId.class)
public class ProjetoMaterial {

    @Id
    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    @JsonBackReference
    private Projeto projeto;

    @Id
    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    private Double quantidade;
    private Double precoUnitarioSnapshot;
    private String unidade;
    private String observacao;
}