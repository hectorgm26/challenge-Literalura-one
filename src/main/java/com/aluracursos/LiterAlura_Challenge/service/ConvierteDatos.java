package com.aluracursos.LiterAlura_Challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ConvierteDatos implements IConvierteDatos {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        if (json == null || json.trim().isEmpty()) {
            throw new RuntimeException("Error al procesar el JSON: La respuesta está vacía");
        }

        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            System.err.println("Error al procesar el JSON: " + e.getMessage());
            throw new RuntimeException("Fallo al procesar JSON", e);
        }
    }
}
