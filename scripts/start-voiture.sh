#!/bin/bash

# Script pour démarrer Service Voiture
# Usage: ./scripts/start-voiture.sh [consul]

MODE=${1:-eureka}

if [ "$MODE" = "consul" ]; then
    echo "🚀 Démarrage de Service Voiture (mode Consul)..."
    cd service-voiture
    mvn spring-boot:run -Dspring-boot.run.profiles=consul
else
    echo "🚀 Démarrage de Service Voiture (mode Eureka)..."
    cd service-voiture
    mvn spring-boot:run
fi

echo "✅ Service Voiture démarré sur http://localhost:8081"