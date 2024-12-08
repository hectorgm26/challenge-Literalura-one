package com.aluracursos.LiterAlura_Challenge.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsumoApi {

    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al conectar con la API: " + e.getMessage());
            throw new RuntimeException("No se pudo conectar a la API", e);
        }

        String json = response.body();
        // System.out.println("Respuesta de la API: " + json); // Verifica la respuesta de la API
        return json;
    }
}
