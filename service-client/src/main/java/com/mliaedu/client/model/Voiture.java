package com.mliaedu.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modèle Voiture côté Service Client
 * 
 * Cette classe représente les données d'une voiture reçues du Service Voiture.
 * Elle doit correspondre exactement au modèle du Service Voiture pour que
 * la désérialisation JSON fonctionne correctement.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voiture {
    
    private Long id;
    private String marque;
    private String modele;
    private Long clientId;
}