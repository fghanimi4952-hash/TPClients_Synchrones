package com.mliaedu.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Application principale du Service Client
 * 
 * Ce service consomme l'API du Service Voiture en utilisant trois méthodes :
 * 1. RestTemplate (client HTTP synchrone classique)
 * 2. Feign (client déclaratif)
 * 3. WebClient (client réactif utilisé en mode synchrone dans ce TP)
 * 
 * @EnableEurekaClient : Active le client Eureka pour la découverte
 * @EnableFeignClients : Active Feign pour les appels déclaratifs
 */
@SpringBootApplication
@EnableEurekaClient  // Pour Eureka
@EnableFeignClients  // Active Feign Client
// @EnableDiscoveryClient  // Pour Consul (plus générique)
public class ServiceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceClientApplication.class, args);
    }
}