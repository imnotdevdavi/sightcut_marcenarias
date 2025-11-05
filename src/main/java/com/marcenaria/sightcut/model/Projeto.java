package com.marcenaria.sightcut.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projeto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String titulo;
    private String descricao;
    private Double custoTotal;
    private LocalDate dataInicio;
    private LocalDate dataEntregaEstimada;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProjetoMaterial> materiais = new ArrayList<>();

    public void setMateriais(List<ProjetoMaterial> materiais) {
        this.materiais.clear();
        if (materiais != null) {
            materiais.forEach(m -> m.setProjeto(this));
            this.materiais.addAll(materiais);
        }
    }
}