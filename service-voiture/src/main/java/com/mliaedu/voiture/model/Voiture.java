package com.mliaedu.voiture.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modèle représentant une Voiture
 * 
 * Cette classe représente les données d'une voiture associée à un client.
 * Utilise Lombok pour générer automatiquement les getters, setters, constructeurs, etc.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voiture {
    
    /**
     * Identifiant unique de la voiture
     */
    private Long id;
    
    /**
     * Marque de la voiture (ex: Toyota, Renault, etc.)
     */
    private String marque;
    
    /**
     * Modèle de la voiture (ex: Yaris, Clio, etc.)
     */
    private String modele;
    
    /**
     * Identifiant du client propriétaire de la voiture
     */
    private Long clientId;
}