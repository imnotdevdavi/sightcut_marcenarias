package com.marcenaria.sightcut.controller;

import com.marcenaria.sightcut.model.ProjetoMaterial;
import com.marcenaria.sightcut.service.ProjetoMaterialService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projetos-materiais")
@CrossOrigin(origins = "*")
public class ProjetoMaterialController {

    private final ProjetoMaterialService service;

    public ProjetoMaterialController(ProjetoMaterialService service) {
        this.service = service;
    }

    @GetMapping("/{projetoId}")
    public List<ProjetoMaterial> listar(@PathVariable Long projetoId) {
        return service.listarPorProjeto(projetoId);
    }

    @PostMapping
    public ProjetoMaterial criar(@RequestBody ProjetoMaterial pm) {
        return service.salvar(pm);
    }

    @DeleteMapping("/{projetoId}/{materialId}")
    public void deletar(
            @PathVariable Long projetoId,
            @PathVariable Long materialId
    ) {
        service.deletar(projetoId, materialId);
    }
}