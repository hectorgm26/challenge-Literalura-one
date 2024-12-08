package com.aluracursos.LiterAlura_Challenge.dto;

public enum Genero {
    ACCION("Action"),
    ROMANCE("Romance"),
    CRIMEN("Crime"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    AVENTURA("Adventure"),
    FICCION("Fiction"),
    DESCONOCIDO("Desconocido");

    private String genero;

    Genero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public static Genero fromString(String text) {
        for (Genero generoEnum : Genero.values()) {
            if (generoEnum.genero.equalsIgnoreCase(text)) {
                return generoEnum;
            }
        }
        return Genero.DESCONOCIDO;
    }
}
