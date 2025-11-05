package com.marcenaria.sightcut.model;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoMaterialId implements Serializable {
    private Long projeto;
    private Long material;
}
