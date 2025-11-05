package com.marcenaria.sightcut.service;

import com.marcenaria.sightcut.model.Cliente;
import com.marcenaria.sightcut.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> listarPorUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }

    public Cliente salvar(Cliente cliente) {
        return repo.save(cliente);
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }
}
