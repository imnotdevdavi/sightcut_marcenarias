package com.marcenaria.sightcut.service;

import com.marcenaria.sightcut.model.Material;
import com.marcenaria.sightcut.model.Projeto;
import com.marcenaria.sightcut.model.ProjetoMaterial;
import com.marcenaria.sightcut.repository.MaterialRepository;
import com.marcenaria.sightcut.repository.ProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjetoService {

    private final ProjetoRepository repo;
    private final MaterialRepository materialRepository;

    public ProjetoService(ProjetoRepository repo, MaterialRepository materialRepository) {
        this.repo = repo;
        this.materialRepository = materialRepository;
    }

    public List<Projeto> listarPorUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Projeto salvar(Projeto projeto) {
        double total = 0;

        if (projeto.getMateriais() != null && !projeto.getMateriais().isEmpty()) {
            for (ProjetoMaterial pm : projeto.getMateriais()) {
                if (pm.getMaterial() != null && pm.getMaterial().getId() != null) {
                    Material materialCompleto = materialRepository
                            .findById(pm.getMaterial().getId())
                            .orElseThrow(() -> new RuntimeException(
                                    "Material não encontrado: " + pm.getMaterial().getId()
                            ));
                    pm.setMaterial(materialCompleto);
                }

                pm.setProjeto(projeto);

                if (pm.getQuantidade() != null && pm.getPrecoUnitarioSnapshot() != null) {
                    total += pm.getQuantidade() * pm.getPrecoUnitarioSnapshot();
                }
            }
        }

        projeto.setCustoTotal(total);
        return repo.save(projeto);
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }
}