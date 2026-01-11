#!/bin/bash

# Script pour tester les endpoints
# Usage: ./scripts/test-endpoints.sh

echo "🧪 Test des endpoints..."

echo ""
echo "=== Test Service Voiture ==="
echo "GET /api/cars/byClient/1"
curl -s http://localhost:8081/api/cars/byClient/1 | jq .

echo ""
echo "=== Test Service Client - RestTemplate ==="
echo "GET /api/clients/1/car/rest"
curl -s http://localhost:8080/api/clients/1/car/rest | jq .

echo ""
echo "=== Test Service Client - Feign ==="
echo "GET /api/clients/1/car/feign"
curl -s http://localhost:8080/api/clients/1/car/feign | jq .

echo ""
echo "=== Test Service Client - WebClient ==="
echo "GET /api/clients/1/car/webclient"
curl -s http://localhost:8080/api/clients/1/car/webclient | jq .

echo ""
echo "✅ Tests terminés"