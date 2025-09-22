package com.example.pi_projsoftware;

import com.example.pi_projsoftware.model.Curso;
import com.example.pi_projsoftware.repository.CursoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoRepository repository;

    @Test
    void deveCadastrarCurso() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setTitulo("API Test");
        curso.setDescricao("Testando Controller");
        curso.setCargaHoraria(20);
        curso.setInstrutor("João");

        Mockito.when(repository.save(Mockito.any(Curso.class))).thenReturn(curso);

        mockMvc.perform(post("/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"API Test\",\"descricao\":\"Testando Controller\",\"cargaHoraria\":20,\"instrutor\":\"João\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("API Test"));
    }

    @Test
    void deveListarCursos() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setTitulo("Java");
        curso.setDescricao("Avançado");
        curso.setCargaHoraria(50);
        curso.setInstrutor("Ana");

        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(curso));

        mockMvc.perform(get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Java"));
    }

    @Test
    void deveExcluirCurso() throws Exception {
        mockMvc.perform(delete("/cursos/1"))
                .andExpect(status().isOk());

        Mockito.verify(repository).deleteById(1L);
    }
}
