package com.miempresa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
@RestController
public class VulnerableApp {

    private static final Logger logger = LogManager.getLogger(VulnerableApp.class);

    public static void main(String[] args) {
        SpringApplication.run(VulnerableApp.class, args);
        System.out.println("APLICACION VULNERABLE INICIADA - Puerto 8080");
    }

    @GetMapping("/api/login")
    public String login(@RequestHeader(value = "X-Api-Version", defaultValue = "1.0") String apiVersion) {
        // AQUÍ ESTÁ LA VULNERABILIDAD:
        // Logueamos lo que nos manda el usuario sin sanitizar.
        // Si mandan "${jndi:ldap://...}", Log4j intentará resolverlo.
        logger.info("Intento de acceso con versión de API: " + apiVersion);

        return "Login procesado (versión: " + apiVersion + ")";
    }

    @PostMapping("/log")
    public String logUserInput(@RequestHeader("User-Agent") String userAgent) {
        logger.info("User-Agent recibido: {}", userAgent);
        return "Logged: " + userAgent;
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String query) {
        logger.warn("Busqueda ejecutada: {}", query);
        return "Resultados para: " + query;
    }

    @PostMapping("/api/data")
    public String processData(@RequestBody String data) {
        logger.error("Datos procesados: {}", data);

        return "Procesado: " + data;
    }

    @GetMapping("/health")
    public String health(){
        return "OK";
    }
}