#!/bin/bash

# Script pour démarrer Service Client
# Usage: ./scripts/start-client.sh [consul]

MODE=${1:-eureka}

if [ "$MODE" = "consul" ]; then
    echo "🚀 Démarrage de Service Client (mode Consul)..."
    cd service-client
    mvn spring-boot:run -Dspring-boot.run.profiles=consul
else
    echo "🚀 Démarrage de Service Client (mode Eureka)..."
    cd service-client
    mvn spring-boot:run
fi

echo "✅ Service Client démarré sur http://localhost:8080"