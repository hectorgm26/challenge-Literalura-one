package com.aluracursos.LiterAlura_Challenge.principal;

import com.aluracursos.LiterAlura_Challenge.model.Libro;
import com.aluracursos.LiterAlura_Challenge.service.LibroService;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    private final LibroService libroService;

    public Principal(LibroService libroService) {
        this.libroService = libroService;
    }

    public void muestraMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("|*****            BIENVENIDO                 ******|");
                System.out.println("1 - Buscar libro por su Titulo");
                System.out.println("2 - Buscar libro por su Autor");
                System.out.println("3 - Buscar Autores vivos en un determinado año");
                System.out.println("4 - Cantidad de libros por idioma registrados");
                System.out.println("5 - Top 10 libros más descargados");
                System.out.println("6 - Ver historial de libros buscados previamente");
                System.out.println("7 - Ver historial de autores buscados previamente");
                System.out.println("0 - Salir");
                System.out.print("|*****            INGRESE UNA OPCIÓN          ******|\n");

                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer

                switch (opcion) {
                    case 1:
                        buscarLibroPorNombre(scanner);
                        break;
                    case 2:
                        buscarLibrosPorNombreAutor(scanner);
                        break;
                    case 3:
                        buscarLibrosPorRangoDeVida(scanner);
                        break;
                    case 4:
                        buscarLibrosPorIdiomas(scanner);
                        break;
                    case 5:
                        List<String> top10Libros = libroService.obtenerTop10LibrosDeApi();
                        top10Libros.forEach(System.out::println);
                        break;
                    case 6:
                        verLibrosBuscados();
                        break;
                    case 7:
                        verAutoresBuscados();
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.nextLine();
            }
        }
    }

    // Métodos de búsqueda de libros y autores
    private void buscarLibroPorNombre(Scanner scanner) {
        System.out.print("Ingrese el nombre del libro que desea buscar: ");
        String nombreLibro = scanner.nextLine();

        // Llamamos al servicio para buscar y guardar libros por título
        libroService.buscarYGuardarLibrosPorTitulo(nombreLibro);

        // A continuación, mostramos el primer libro encontrado con los detalles especificados
        List<Libro> libros = libroService.buscarLibroPorNombre(nombreLibro);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con ese nombre.");
        } else {
            // Solo mostramos el primer libro encontrado y sus detalles
            Libro libro = libros.get(0);
            System.out.println("|*****            Datos buscados         ******|");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Número de Descargas: " + libro.getCantidadDescargas());
        }
    }


    private void verLibrosBuscados() {
        List<Libro> librosBuscados = libroService.obtenerLibrosBuscados();
        if (librosBuscados.isEmpty()) {
            System.out.println("No se han buscado libros.");
        } else {
            librosBuscados.forEach(System.out::println);
        }
    }

    private void verAutoresBuscados() {
        List<String> autoresBuscados = libroService.obtenerAutoresBuscados();
        if (autoresBuscados.isEmpty()) {
            System.out.println("No se han buscado autores.");
        } else {
            autoresBuscados.forEach(System.out::println);
        }
    }

    private void buscarLibrosPorRangoDeVida(Scanner scanner) {
        System.out.print("Ingrese el año de inicio: ");
        int anioInicio = scanner.nextInt();
        System.out.print("Ingrese el año de fin: ");
        int anioFin = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        List<Libro> libros = libroService.buscarLibrosPorRangoDeVidaAutor(anioInicio, anioFin);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese rango de años.");
        } else {
            // Agrupamos los libros por autor para mostrar solo uno por autor
            libros.stream()
                    .collect(Collectors.groupingBy(libro -> libro.getAutor().getNombre()))
                    .forEach((autorNombre, librosPorAutor) -> {
                        Libro libro = librosPorAutor.get(0);  // Tomamos el primer libro
                        System.out.println("|*****            Datos del autor y libro encontrado         ******|");
                        System.out.println("Autor: " + autorNombre);
                        System.out.println("Título: " + libro.getTitulo());
                        System.out.println("Idioma: " + libro.getIdioma());
                        System.out.println("Número de Descargas: " + libro.getCantidadDescargas());
                    });
        }
    }

    private void buscarLibrosPorIdiomas(Scanner scanner) {
        System.out.print("Ingrese los códigos de idioma separados por comas (ejemplo: en,es,fr): ");
        String idiomas = scanner.nextLine();

        // Convertimos la cadena de idiomas en un array
        String[] idiomasArray = idiomas.split(",");

        // Contamos los libros por cada idioma
        for (String idioma : idiomasArray) {
            long cantidadLibros = libroService.contarLibrosPorIdioma(idioma.trim());
            System.out.println("Cantidad de libros en el idioma " + idioma.trim() + ": " + cantidadLibros);
        }
    }


    private void buscarLibrosPorNombreAutor(Scanner scanner) {
        System.out.print("Ingrese el nombre del autor: ");
        String nombreAutor = scanner.nextLine();

        // Llamamos al servicio para buscar y guardar el primer libro por autor
        List<Libro> libros = libroService.buscarLibrosPorNombreAutor(nombreAutor);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros de ese autor.");
        } else {
            // Mostramos solo el primer libro encontrado
            Libro libro = libros.get(0);
            System.out.println("|*****            Datos del libro encontrado         ******|");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Número de Descargas: " + libro.getCantidadDescargas());
        }
    }


}
