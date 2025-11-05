package com.marcenaria.sightcut.service;

import com.marcenaria.sightcut.model.Usuario;
import com.marcenaria.sightcut.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario registrar(Usuario u) {
        if (repo.findByEmail(u.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        u.setSenhaHash(BCrypt.hashpw(u.getSenhaHash(), BCrypt.gensalt()));
        return repo.save(u);
    }

    public Usuario login(String email, String senha) {
        Usuario u = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!BCrypt.checkpw(senha, u.getSenhaHash())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return u;
    }

    public Usuario buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario atualizar(Long id, Usuario novo) {
        Usuario u = repo.findById(id).orElseThrow();
        u.setNome(novo.getNome());
        u.setCpf(novo.getCpf());

        if (novo.getSenhaHash() != null && !novo.getSenhaHash().isEmpty()) {
            u.setSenhaHash(BCrypt.hashpw(novo.getSenhaHash(), BCrypt.gensalt()));
        }

        u.setFotoPerfil(novo.getFotoPerfil());
        return repo.save(u);
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }
}