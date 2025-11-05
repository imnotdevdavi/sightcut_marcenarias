package com.marcenaria.sightcut.controller;

import com.marcenaria.sightcut.model.Material;
import com.marcenaria.sightcut.service.MaterialService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/materiais")
@CrossOrigin(origins = "*")
public class MaterialController {

    private final MaterialService service;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @GetMapping("/{usuarioId}")
    public List<Material> listar(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @PostMapping
    public Material criar(@RequestBody Material material) {
        return service.salvar(material);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
