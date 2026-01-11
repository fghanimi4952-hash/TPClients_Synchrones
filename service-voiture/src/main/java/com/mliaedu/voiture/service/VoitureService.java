package com.mliaedu.voiture.service;

import com.mliaedu.voiture.model.Voiture;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service métier pour la gestion des voitures
 * 
 * Dans ce TP, on utilise une base de données en mémoire (Map) pour éviter
 * que MySQL n'influence les mesures de performance. C'est suffisant pour
 * comparer les clients HTTP (RestTemplate, Feign, WebClient).
 */
@Service
public class VoitureService {

    // Base de données en mémoire pour simplifier le TP
    // En production, utiliser JPA/Hibernate avec une vraie base de données
    private final Map<Long, Voiture> voitures = new HashMap<>();

    /**
     * Constructeur : initialise quelques données de test
     */
    public VoitureService() {
        // Données de test pour le TP
        voitures.put(1L, new Voiture(1L, "Toyota", "Yaris", 1L));
        voitures.put(2L, new Voiture(2L, "Renault", "Clio", 2L));
        voitures.put(3L, new Voiture(3L, "Peugeot", "208", 3L));
        voitures.put(4L, new Voiture(4L, "Volkswagen", "Golf", 1L));
        voitures.put(5L, new Voiture(5L, "BMW", "Serie 3", 2L));
    }

    /**
     * Récupère la voiture d'un client
     * 
     * @param clientId Identifiant du client
     * @return La première voiture trouvée pour ce client, ou null si aucune
     */
    public Voiture getVoitureByClientId(Long clientId) {
        // Simulation d'un délai de traitement (optionnel mais utile pour le TP)
        // Ce délai rend les différences entre clients HTTP plus observables
        try {
            Thread.sleep(20);  // 20ms de délai artificiel
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Recherche de la première voiture du client
        return voitures.values().stream()
                .filter(voiture -> voiture.getClientId().equals(clientId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Récupère toutes les voitures (pour debug)
     */
    public Map<Long, Voiture> getAllVoitures() {
        return voitures;
    }
}