package com.mliaedu.voiture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Application principale du Service Voiture
 * 
 * Ce service expose une API REST pour gérer les voitures des clients.
 * Il s'enregistre auprès d'un serveur de découverte (Eureka ou Consul)
 * pour être accessible par son nom logique.
 * 
 * @EnableEurekaClient : Active le client Eureka pour la découverte de services
 * Pour utiliser Consul : remplacer par @EnableDiscoveryClient et désactiver Eureka
 */
@SpringBootApplication
@EnableEurekaClient  // Pour Eureka
// @EnableDiscoveryClient  // Pour Consul (plus générique, fonctionne avec Eureka et Consul)
public class ServiceVoitureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVoitureApplication.class, args);
    }
}