package com.mliaedu.client.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration pour WebClient
 * 
 * WebClient est le client HTTP réactif (non-bloquant) de Spring 5+.
 * Dans ce TP, on l'utilise en mode synchrone via block() pour comparer
 * à armes égales avec RestTemplate et Feign.
 * 
 * @LoadBalanced : Active la résolution de noms via le service de découverte
 */
@Configuration
public class WebClientConfig {

    /**
     * Bean WebClient.Builder avec load balancing
     * 
     * Le builder permet de créer des instances de WebClient personnalisées.
     * @LoadBalanced active la résolution de noms logiques.
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}