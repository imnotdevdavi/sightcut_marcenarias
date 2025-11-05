package com.marcenaria.sightcut.repository;

import com.marcenaria.sightcut.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByUsuarioId(Long usuarioId);
}