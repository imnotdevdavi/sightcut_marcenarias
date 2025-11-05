package com.marcenaria.sightcut.controller;

import com.marcenaria.sightcut.model.Projeto;
import com.marcenaria.sightcut.service.ProjetoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projetos")
@CrossOrigin(origins = "*")
public class ProjetoController {

    private final ProjetoService service;

    public ProjetoController(ProjetoService service) {
        this.service = service;
    }

    @GetMapping("/{usuarioId}")
    public List<Projeto> listar(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @PostMapping
    public Projeto criar(@RequestBody Projeto projeto) {
        return service.salvar(projeto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
