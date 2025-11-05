package com.marcenaria.sightcut.repository;

import com.marcenaria.sightcut.model.ProjetoMaterial;
import com.marcenaria.sightcut.model.ProjetoMaterialId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjetoMaterialRepository extends JpaRepository<ProjetoMaterial, ProjetoMaterialId> {
    List<ProjetoMaterial> findByProjetoId(Long projetoId);

    void deleteByProjetoIdAndMaterialId(Long projetoId, Long materialId);
}