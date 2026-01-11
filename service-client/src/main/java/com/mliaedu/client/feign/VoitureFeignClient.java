package com.mliaedu.client.feign;

import com.mliaedu.client.model.Voiture;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Interface Feign Client pour appeler le Service Voiture
 * 
 * Feign est un client HTTP déclaratif : on définit une interface avec les
 * annotations Spring MVC, et Feign génère automatiquement l'implémentation.
 * 
 * Avantages :
 * - Code très simple et lisible
 * - Pas de gestion manuelle d'URL
 * - Intégration automatique avec la découverte de services
 * 
 * @FeignClient : Déclare que cette interface est un client Feign
 * name = "SERVICE-VOITURE" : Nom logique du service cible (résolu via Eureka/Consul)
 */
@FeignClient(name = "SERVICE-VOITURE", path = "/api/cars")
public interface VoitureFeignClient {

    /**
     * Récupère la voiture d'un client
     * 
     * Cette méthode sera automatiquement implémentée par Feign.
     * L'appel sera fait à : http://SERVICE-VOITURE/api/cars/byClient/{clientId}
     * 
     * @param clientId Identifiant du client
     * @return La voiture du client
     */
    @GetMapping("/byClient/{clientId}")
    Voiture getVoitureByClient(@PathVariable("clientId") Long clientId);
}