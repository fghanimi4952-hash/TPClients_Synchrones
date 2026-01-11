package com.mliaedu.client.service;

import com.mliaedu.client.feign.VoitureFeignClient;
import com.mliaedu.client.model.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Service métier pour le Service Client
 * 
 * Ce service implémente trois méthodes pour appeler le Service Voiture :
 * 1. via RestTemplate (synchrone classique)
 * 2. via Feign (déclaratif)
 * 3. via WebClient (réactif utilisé en synchrone)
 * 
 * Objectif : Comparer les performances et la simplicité de chaque méthode.
 */
@Service
public class ClientService {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    private VoitureFeignClient voitureFeignClient;

    // Instance de WebClient pour éviter de recréer à chaque appel
    private WebClient webClient;

    /**
     * Initialise WebClient avec le builder chargé
     */
    @Autowired
    public void initWebClient(@LoadBalanced WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    /**
     * Méthode 1 : Récupère la voiture via RestTemplate
     * 
     * RestTemplate est le client HTTP synchrone classique de Spring.
     * Il est simple mais considéré comme "legacy" (Spring le marque comme déprécié).
     * 
     * @param clientId Identifiant du client
     * @return La voiture du client ou null si non trouvée
     */
    public Voiture getVoitureByRestTemplate(Long clientId) {
        // Appel avec le nom logique du service (résolu via Eureka/Consul)
        String url = "http://SERVICE-VOITURE/api/cars/byClient/" + clientId;
        
        try {
            return restTemplate.getForObject(url, Voiture.class);
        } catch (Exception e) {
            // En cas d'erreur (service indisponible, timeout, etc.)
            System.err.println("Erreur RestTemplate : " + e.getMessage());
            return null;
        }
    }

    /**
     * Méthode 2 : Récupère la voiture via Feign
     * 
     * Feign est un client déclaratif : on définit une interface, Feign fait le reste.
     * Avantages :
     * - Code très simple et lisible
     * - Pas de gestion manuelle d'URL
     * - Gestion automatique de la sérialisation/désérialisation
     * 
     * @param clientId Identifiant du client
     * @return La voiture du client ou null si non trouvée
     */
    public Voiture getVoitureByFeign(Long clientId) {
        try {
            return voitureFeignClient.getVoitureByClient(clientId);
        } catch (Exception e) {
            System.err.println("Erreur Feign : " + e.getMessage());
            return null;
        }
    }

    /**
     * Méthode 3 : Récupère la voiture via WebClient
     * 
     * WebClient est le client HTTP réactif (non-bloquant) de Spring 5+.
     * Dans ce TP, on l'utilise en mode synchrone via block() pour comparer
     * à armes égales avec RestTemplate et Feign.
     * 
     * En production, WebClient est généralement utilisé en mode non-bloquant
     * (avec Mono/Flux) pour de meilleures performances avec beaucoup de requêtes.
     * 
     * @param clientId Identifiant du client
     * @return La voiture du client ou null si non trouvée
     */
    public Voiture getVoitureByWebClient(Long clientId) {
        try {
            // Utilisation du nom logique du service (résolu via Eureka/Consul)
            Mono<Voiture> monoVoiture = webClient
                    .get()
                    .uri("http://SERVICE-VOITURE/api/cars/byClient/{clientId}", clientId)
                    .retrieve()
                    .bodyToMono(Voiture.class);

            // block() rend l'appel synchrone (pour comparaison avec RestTemplate/Feign)
            // En production, on éviterait block() et on travaillerait avec Mono/Flux
            return monoVoiture.block();
        } catch (Exception e) {
            System.err.println("Erreur WebClient : " + e.getMessage());
            return null;
        }
    }
}