package com.aluracursos.LiterAlura_Challenge;

import com.aluracursos.LiterAlura_Challenge.principal.Principal;
import com.aluracursos.LiterAlura_Challenge.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraChallengeApplication implements CommandLineRunner {

    @Autowired
    private LibroService libroService;

    @Autowired
    private Principal principal;

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraChallengeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        principal.muestraMenu();

    }
}
