package com.mliaedu.client.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration pour RestTemplate
 * 
 * RestTemplate est le client HTTP synchrone classique de Spring.
 * L'annotation @LoadBalanced permet d'utiliser le nom logique du service
 * (SERVICE-VOITURE) au lieu de l'URL complète. Spring Cloud LoadBalancer
 * résout automatiquement le nom via Eureka ou Consul.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Bean RestTemplate avec load balancing
     * 
     * @LoadBalanced : Active la résolution de noms via le service de découverte
     * Permet d'utiliser : restTemplate.getForObject("http://SERVICE-VOITURE/api/cars/...", ...)
     * au lieu de : restTemplate.getForObject("http://localhost:8081/api/cars/...", ...)
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}