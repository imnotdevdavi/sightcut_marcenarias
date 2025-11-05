package com.marcenaria.sightcut.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    private String cpf;

    @Column(nullable = false)
    private String senhaHash;

    @Column(nullable = false)
    private String role = "USUARIO";

    private Boolean ativo = true;

    @Column(columnDefinition = "TEXT")
    private String fotoPerfil;
}