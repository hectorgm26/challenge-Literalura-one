package com.aluracursos.LiterAlura_Challenge.repository;

import com.aluracursos.LiterAlura_Challenge.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findTop10ByOrderByCantidadDescargasDesc();

    List<Libro> findByIdiomaContaining(String idioma);  // Cambio aquí

    List<Libro> findByTituloContainingIgnoreCase(String nombre);

    long countByIdiomaContaining(String idioma);

}
