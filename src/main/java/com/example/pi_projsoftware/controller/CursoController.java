package com.example.pi_projsoftware.controller;

import com.example.pi_projsoftware.model.Curso;
import com.example.pi_projsoftware.repository.CursoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoRepository repository;

    public CursoController(CursoRepository repository) {
        this.repository = repository;
    }

    // 1. Cadastrar curso
    @PostMapping
    public Curso cadastrar(@RequestBody Curso curso) {
        return repository.save(curso);
    }

    // 2. Excluir curso por id
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }

    // 3. Listar todos os cursos
    @GetMapping
    public List<Curso> listar() {
        return repository.findAll();
    }
}
