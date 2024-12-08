package com.aluracursos.LiterAlura_Challenge.repository;

import com.aluracursos.LiterAlura_Challenge.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);

    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    Iterable<Object> findByNombreContaining(String nombreAutor);
}
