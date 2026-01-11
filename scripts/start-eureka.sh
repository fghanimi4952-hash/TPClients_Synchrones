#!/bin/bash

# Script pour démarrer Eureka Server
# Usage: ./scripts/start-eureka.sh

echo "🚀 Démarrage d'Eureka Server..."

cd eureka-server
mvn spring-boot:run

echo "✅ Eureka Server démarré sur http://localhost:8761"