package com.aluracursos.LiterAlura_Challenge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    // Relación ManyToOne con Autor
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String idioma;

    private Long cantidadDescargas;

    // Constructor que recibe DatosLibro
    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();

        if (datosLibro.autor() != null && !datosLibro.autor().isEmpty()) {
            this.autor = datosLibro.autor().get(0);
        }

        // Como 'idioma' es una lista, tomamos el primer idioma
        if (datosLibro.idioma() != null && !datosLibro.idioma().isEmpty()) {
            this.idioma = datosLibro.idioma().get(0);
        }

        this.cantidadDescargas = datosLibro.cantidadDescargas();
    }


    public Libro() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Long cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    @Override
    public String toString() {
        return "*******************************\n" +
                "Libro{" +
                "id=" + id +
                ", \nTitulo: '" + titulo + '\'' +
                ", \nAutor: " + autor +
                ", \nIdioma: '" + idioma + '\'' +
                ", \nCantidad de Descargas: " + cantidadDescargas +
                '}';
    }
}
