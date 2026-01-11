package com.mliaedu.voiture.controller;

import com.mliaedu.voiture.model.Voiture;
import com.mliaedu.voiture.service.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour exposer l'API des voitures
 * 
 * Ce contrôleur expose un endpoint qui permet de récupérer la voiture
 * d'un client. Il sera appelé par le Service Client via différents
 * clients HTTP (RestTemplate, Feign, WebClient).
 */
@RestController
@RequestMapping("/api/cars")
public class VoitureController {

    @Autowired
    private VoitureService voitureService;

    /**
     * Endpoint principal : récupère la voiture d'un client
     * 
     * GET /api/cars/byClient/{clientId}
     * 
     * @param clientId Identifiant du client
     * @return La voiture du client ou 404 si non trouvée
     */
    @GetMapping("/byClient/{clientId}")
    public ResponseEntity<Voiture> getVoitureByClient(@PathVariable Long clientId) {
        Voiture voiture = voitureService.getVoitureByClientId(clientId);
        
        if (voiture == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(voiture);
    }
}