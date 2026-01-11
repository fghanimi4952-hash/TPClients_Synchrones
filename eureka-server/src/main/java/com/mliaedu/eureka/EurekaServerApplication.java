package com.mliaedu.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Application principale du serveur Eureka
 * 
 * Eureka Server est le registre de services centralisé qui permet aux
 * microservices de s'enregistrer et de découvrir les autres services.
 * 
 * @EnableEurekaServer : Active le serveur Eureka
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}