package com.mliaedu.client.controller;

import com.mliaedu.client.model.Voiture;
import com.mliaedu.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST du Service Client
 * 
 * Ce contrôleur expose trois endpoints pour tester les trois méthodes
 * d'appel au Service Voiture :
 * 1. /api/clients/{id}/car/rest      -> via RestTemplate
 * 2. /api/clients/{id}/car/feign     -> via Feign
 * 3. /api/clients/{id}/car/webclient -> via WebClient
 * 
 * Ces endpoints permettent de comparer les performances et la simplicité
 * de chaque méthode dans des conditions identiques.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * Endpoint 1 : Récupère la voiture via RestTemplate
     * 
     * GET /api/clients/{id}/car/rest
     * 
     * @param id Identifiant du client
     * @return La voiture du client
     */
    @GetMapping("/{id}/car/rest")
    public ResponseEntity<Voiture> getVoitureByRestTemplate(@PathVariable Long id) {
        Voiture voiture = clientService.getVoitureByRestTemplate(id);
        
        if (voiture == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(voiture);
    }

    /**
     * Endpoint 2 : Récupère la voiture via Feign
     * 
     * GET /api/clients/{id}/car/feign
     * 
     * @param id Identifiant du client
     * @return La voiture du client
     */
    @GetMapping("/{id}/car/feign")
    public ResponseEntity<Voiture> getVoitureByFeign(@PathVariable Long id) {
        Voiture voiture = clientService.getVoitureByFeign(id);
        
        if (voiture == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(voiture);
    }

    /**
     * Endpoint 3 : Récupère la voiture via WebClient
     * 
     * GET /api/clients/{id}/car/webclient
     * 
     * @param id Identifiant du client
     * @return La voiture du client
     */
    @GetMapping("/{id}/car/webclient")
    public ResponseEntity<Voiture> getVoitureByWebClient(@PathVariable Long id) {
        Voiture voiture = clientService.getVoitureByWebClient(id);
        
        if (voiture == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(voiture);
    }
}