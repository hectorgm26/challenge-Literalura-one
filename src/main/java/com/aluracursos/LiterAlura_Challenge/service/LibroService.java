package com.aluracursos.LiterAlura_Challenge.service;

import com.aluracursos.LiterAlura_Challenge.model.Autor;
import com.aluracursos.LiterAlura_Challenge.model.DatosLibro;
import com.aluracursos.LiterAlura_Challenge.model.Libro;
import com.aluracursos.LiterAlura_Challenge.model.LibrosRespuestaApi;
import com.aluracursos.LiterAlura_Challenge.repository.AutorRepository;
import com.aluracursos.LiterAlura_Challenge.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private ConsumoApi consumoApi;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public void buscarYGuardarLibrosPorTitulo(String titulo) {
        String encodedTitulo = encodeSearchQuery(titulo);
        String url = "https://gutendex.com/books/?search=" + encodedTitulo;
        String json = consumoApi.obtenerDatos(url);

        if (json == null || json.trim().isEmpty()) {
            System.out.println("No se recibió una respuesta válida de la API.");
            return;
        }

        try {
            LibrosRespuestaApi respuesta = convierteDatos.obtenerDatos(json, LibrosRespuestaApi.class);

            if (respuesta != null && respuesta.getResultadoLibros() != null && !respuesta.getResultadoLibros().isEmpty()) {
                DatosLibro datosLibro = respuesta.getResultadoLibros().get(0);

                Libro libro = new Libro(datosLibro);

                if (libro.getAutor() != null) {
                    Autor autor = libro.getAutor(); // Obtener el único autor
                    autorRepository.save(autor);  // Guardamos el autor
                }

                libroRepository.save(libro);
                System.out.println("Libro guardado: " + libro);
            } else {
                System.out.println("No se encontraron resultados para el título: " + titulo);
            }
        } catch (RuntimeException e) {
            System.err.println("Error al procesar la respuesta de la API: " + e.getMessage());
        }
    }


    private String encodeSearchQuery(String query) {
        try {
            return URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return query;
        }
    }

    public List<Libro> buscarLibrosPorRangoDeVidaAutor(int anioInicio, int anioFin) {
        String url = "https://gutendex.com/books/?author_year_start=" + anioInicio + "&author_year_end=" + anioFin;
        String json = consumoApi.obtenerDatos(url);

        try {
            LibrosRespuestaApi respuesta = convierteDatos.obtenerDatos(json, LibrosRespuestaApi.class);

            if (respuesta != null && respuesta.getResultadoLibros() != null) {
                List<Libro> libros = respuesta.getResultadoLibros().stream()
                        .map(Libro::new)
                        .collect(Collectors.toList());

                // Guardar los libros y sus autores en la base de datos
                libros.forEach(libro -> {
                    if (libro.getAutor() != null) {
                        autorRepository.save(libro.getAutor());
                    }
                    libroRepository.save(libro);
                });

                return libros;
            }
        } catch (RuntimeException e) {
            System.err.println("Error al procesar la respuesta de la API: " + e.getMessage());
        }
        return List.of();
    }

    public long contarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdiomaContaining(idioma).stream().count();
    }

    public List<Libro> buscarLibrosPorNombreAutor(String nombreAutor) {
        String encodedNombre = encodeSearchQuery(nombreAutor);
        String url = "https://gutendex.com/books/?search=" + encodedNombre;
        String json = consumoApi.obtenerDatos(url);

        try {
            LibrosRespuestaApi respuesta = convierteDatos.obtenerDatos(json, LibrosRespuestaApi.class);

            if (respuesta != null && respuesta.getResultadoLibros() != null && !respuesta.getResultadoLibros().isEmpty()) {

                DatosLibro datosLibro = respuesta.getResultadoLibros().get(0);
                Libro libro = new Libro(datosLibro);


                if (libro.getAutor() != null) {
                    Autor autor = libro.getAutor();
                    autorRepository.save(autor);
                }

                libroRepository.save(libro);  // Guardamos el libro

                // Solo devolvemos el primer libro
                return List.of(libro);
            }
        } catch (RuntimeException e) {
            System.err.println("Error al procesar la respuesta de la API: " + e.getMessage());
        }
        return List.of();  // Si no se encuentra ningún libro
    }


    public List<String> obtenerTop10LibrosDeApi() {
        String url = "https://gutendex.com/books/";
        String json = consumoApi.obtenerDatos(url);

        try {
            LibrosRespuestaApi respuesta = convierteDatos.obtenerDatos(json, LibrosRespuestaApi.class);

            if (respuesta != null && respuesta.getResultadoLibros() != null) {
                return respuesta.getResultadoLibros().stream()
                        .sorted(Comparator.comparing(DatosLibro::cantidadDescargas).reversed()) // Usa el nombre del campo
                        .limit(10) // Solo toma los 10 primeros
                        .map(DatosLibro::titulo) // Usa el nombre del campo
                        .map(String::toUpperCase) // Transformar a mayúsculas
                        .collect(Collectors.toList());
            }
        } catch (RuntimeException e) {
            System.err.println("Error al procesar la respuesta de la API: " + e.getMessage());
        }
        return List.of(); // Devuelve una lista vacía si ocurre un error
    }


    public List<Libro> obtenerLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdiomaContaining(idioma);
    }

    public List<Libro> obtenerTop10LibrosMasDescargados() {
        return libroRepository.findTop10ByOrderByCantidadDescargasDesc();
    }

    public List<Autor> buscarAutoresPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Libro> buscarLibroPorNombre(String nombre) {
        return libroRepository.findByTituloContainingIgnoreCase(nombre);
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    public List<Libro> obtenerLibrosBuscados() {
        return libroRepository.findAll();
    }

    public List<String> obtenerAutoresBuscados() {
        List<Libro> librosBuscados = libroRepository.findAll();
        return librosBuscados.stream()
                .map(libro -> libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido") // Obtener el nombre del autor
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Libro> buscarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdiomaContaining(idioma);
    }
}
