package com.marcenaria.sightcut.service;

import com.marcenaria.sightcut.model.Material;
import com.marcenaria.sightcut.repository.MaterialRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository repo;

    public MaterialService(MaterialRepository repo) {
        this.repo = repo;
    }

    public List<Material> listarPorUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public Material salvar(Material material) {
        return repo.save(material);
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }
}
