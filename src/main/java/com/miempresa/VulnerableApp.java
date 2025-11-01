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