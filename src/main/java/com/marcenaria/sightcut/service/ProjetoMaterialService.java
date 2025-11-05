package com.marcenaria.sightcut.service;

import com.marcenaria.sightcut.model.ProjetoMaterial;
import com.marcenaria.sightcut.model.ProjetoMaterialId;
import com.marcenaria.sightcut.repository.ProjetoMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjetoMaterialService {

    private final ProjetoMaterialRepository repo;

    public ProjetoMaterialService(ProjetoMaterialRepository repo) {
        this.repo = repo;
    }

    public List<ProjetoMaterial> listarPorProjeto(Long projetoId) {
        return repo.findByProjetoId(projetoId);
    }

    public ProjetoMaterial salvar(ProjetoMaterial pm) {
        return repo.save(pm);
    }

    @Transactional
    public void deletar(Long projetoId, Long materialId) {
        ProjetoMaterialId id = new ProjetoMaterialId(projetoId, materialId);
        repo.deleteById(id);
    }

    @Deprecated
    public void deletar(Long id) {
        throw new UnsupportedOperationException(
                "Use deletar(Long projetoId, Long materialId) para chave composta"
        );
    }
}