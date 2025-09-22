package com.example.pi_projsoftware;

import com.example.pi_projsoftware.model.Curso;
import com.example.pi_projsoftware.repository.CursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
public class CursoRepositoryTest {

    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CursoRepository cursoRepository;

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        cursoRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        Curso curso = new Curso();
        curso.setTitulo("Matemática");
        cursoRepository.save(curso);

        Optional<Curso> found = cursoRepository.findById(curso.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitulo()).isEqualTo("Matemática");
    }

    @Test
    void testFindAll() {
        Curso curso1 = new Curso();
        curso1.setTitulo("Física");
        Curso curso2 = new Curso();
        curso2.setTitulo("Química");

        cursoRepository.save(curso1);
        cursoRepository.save(curso2);

        assertThat(cursoRepository.findAll()).hasSize(2);
    }
}
