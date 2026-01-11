# TP : Clients Synchrones (RestTemplate vs Feign vs WebClient) avec Eureka et Consul

## 📋 Objectifs pédagogiques

À la fin du lab, vous serez capable de :
- ✅ Implémenter deux microservices communiquant synchroniquement
- ✅ Configurer la découverte de services avec **Eureka** et avec **Consul**
- ✅ Implémenter 3 clients HTTP côté Service Client : **RestTemplate**, **Feign**, **WebClient**
- ✅ Réaliser des tests de performance (latence / débit) et collecter des métriques
- ✅ Tester la résilience (panne service voiture, panne discovery, etc.) et analyser les résultats

## 🏗️ Architecture

```
┌─────────────────┐
│  Eureka/Consul  │  (Découverte de services)
└────────┬────────┘
         │
    ┌────┴────┐
    │         │
┌───▼────┐ ┌──▼────────┐
│Service │ │ Service   │
│Voiture │ │ Client    │
│        │ │           │
│ 8081   │ │  8080     │
└───┬────┘ └───┬───────┘
    │          │
    └────┬─────┘
         │
    ┌────▼────┐
    │ RestTemplate│
    │ Feign      │
    │ WebClient  │
    └───────────┘
```

### Services
- **Service Voiture** (port 8081) : Expose l'API des voitures
- **Service Client** (port 8080) : Consomme l'API Voiture avec 3 techniques
- **Eureka Server** (port 8761) : Registre de services (mode Eureka)
- **Consul** (port 8500) : Registre de services (mode Consul)

## 🚀 Installation et Démarrage

### Prérequis
- Java 17+
- Maven 3.6+
- (Optionnel) Consul pour tester le mode Consul

### Étape 1 : Cloner et Compiler

```bash
# Compiler tous les projets
cd service-voiture && mvn clean install
cd ../service-client && mvn clean install
cd ../eureka-server && mvn clean install
cd ..
```

### Étape 2 : Mode Eureka

#### 2.1 Lancer Eureka Server

```bash
cd eureka-server
mvn spring-boot:run
# OU
java -jar target/eureka-server-1.0.0.jar
```

Vérifier : http://localhost:8761 (UI Eureka)

#### 2.2 Lancer Service Voiture

```bash
cd service-voiture
mvn spring-boot:run
# OU
java -jar target/service-voiture-1.0.0.jar
```

✅ Test : http://localhost:8081/api/cars/byClient/1

#### 2.3 Lancer Service Client

```bash
cd service-client
mvn spring-boot:run
# OU
java -jar target/service-client-1.0.0.jar
```

✅ Test :
- http://localhost:8080/api/clients/1/car/rest
- http://localhost:8080/api/clients/1/car/feign
- http://localhost:8080/api/clients/1/car/webclient

### Étape 3 : Mode Consul (Alternative)

#### 3.1 Installer et Lancer Consul

```bash
# Télécharger Consul : https://www.consul.io/downloads
# OU via Homebrew (Mac)
brew install consul

# Lancer Consul
consul agent -dev -ui -client=0.0.0.0
```

Vérifier : http://localhost:8500 (UI Consul)

#### 3.2 Modifier les Services pour Consul

**Dans `service-voiture/pom.xml` :**
```xml
<!-- Commenter Eureka -->
<!--
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
-->

<!-- Décommenter Consul -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```

**Dans `service-voiture/src/main/java/.../ServiceVoitureApplication.java` :**
```java
// Commenter @EnableEurekaClient
// @EnableEurekaClient
@EnableDiscoveryClient  // Utiliser celui-ci à la place
```

**Même chose pour `service-client`**

#### 3.3 Lancer avec profil Consul

```bash
# Service Voiture
cd service-voiture
mvn spring-boot:run -Dspring-boot.run.profiles=consul

# Service Client
cd service-client
mvn spring-boot:run -Dspring-boot.run.profiles=consul
```

## 🧪 Tests de Performance

### Outils recommandés
- **JMeter** : Tests de charge
- **Postman** : Tests manuels
- **curl** : Tests rapides

### Scénarios de test

#### Test 1 : Latence (10 requêtes séquentielles)

```bash
# RestTemplate
for i in {1..10}; do time curl -s http://localhost:8080/api/clients/1/car/rest > /dev/null; done

# Feign
for i in {1..10}; do time curl -s http://localhost:8080/api/clients/1/car/feign > /dev/null; done

# WebClient
for i in {1..10}; do time curl -s http://localhost:8080/api/clients/1/car/webclient > /dev/null; done
```

#### Test 2 : Charge (JMeter)

**Configuration recommandée :**
- **Nombre d'utilisateurs** : 10, 50, 100, 200, 500
- **Ramp-up** : 10 secondes
- **Durée** : 1 minute
- **Endpoints à tester** :
  - `/api/clients/1/car/rest`
  - `/api/clients/1/car/feign`
  - `/api/clients/1/car/webclient`

**Métriques à noter :**
- Temps moyen (ms)
- P95 (ms)
- Débit (req/s)
- Taux d'erreur (%)

### Plan JMeter (exemple)

Créer un plan JMeter avec :
1. Thread Group (nombre d'utilisateurs)
2. HTTP Request (endpoint)
3. View Results Tree (débug)
4. Summary Report (statistiques)

## 📊 Tests de Résilience

### Test 1 : Panne du Service Voiture

1. Lancer un test de charge (100 req/s)
2. **Arrêter** Service Voiture pendant 10-20 secondes
3. **Redémarrer** Service Voiture
4. Observer :
   - Taux d'échec (%)
   - Temps de reprise
   - Comportement de chaque client (rest/feign/webclient)

### Test 2 : Panne du Serveur de Découverte

1. Services démarrés et enregistrés
2. Lancer un test de charge
3. **Arrêter** Eureka Server ou Consul pendant le test
4. Observer :
   - "Cache actif" ou "échec immédiat"
   - Dégradation progressive selon charge
5. **Redémarrer** le serveur de découverte

### Test 3 : Panne du Service Client

1. Arrêter Service Client
2. Relancer Service Client
3. Vérifier la re-registration dans discovery

## 📈 Collecte de Métriques

### CPU / Mémoire

#### Via Task Manager / htop

```bash
# Observer les processus Java
top -p $(pgrep -f service-voiture)
top -p $(pgrep -f service-client)
```

**Noter :**
- CPU% (pendant test 100/200/500)
- RAM (MB)

#### Via Spring Boot Actuator

```bash
# Métriques
curl http://localhost:8080/actuator/metrics

# Health
curl http://localhost:8080/actuator/health
```

### Prometheus / Grafana (Optionnel)

1. Ajouter dépendance Prometheus dans `pom.xml`
2. Exposer métriques : `/actuator/prometheus`
3. Configurer Prometheus pour scraper
4. Visualiser dans Grafana

## 📝 Résultats Attendus

### Tableau 1 : Performance (Eureka)

| Méthode | Temps moyen (ms) | Débit (req/s) | P95 (ms) |
|---------|------------------|---------------|----------|
| RestTemplate | | | |
| Feign | | | |
| WebClient | | | |

### Tableau 2 : Performance (Consul)

| Méthode | Temps moyen (ms) | Débit (req/s) | P95 (ms) |
|---------|------------------|---------------|----------|
| RestTemplate | | | |
| Feign | | | |
| WebClient | | | |

### Tableau 3 : CPU / Mémoire

| Méthode | CPU% (100 req/s) | RAM (MB) |
|---------|------------------|----------|
| RestTemplate | | |
| Feign | | |
| WebClient | | |

### Tableau 4 : Résilience

| Scénario | RestTemplate | Feign | WebClient |
|----------|-------------|-------|-----------|
| Panne Service Voiture | | | |
| Panne Discovery | | | |

### Tableau 5 : Simplicité

| Méthode | Configuration | Lignes de code | Complexité |
|---------|--------------|----------------|------------|
| RestTemplate | | | |
| Feign | | | |
| WebClient | | | |

## 🔍 Analyse et Discussion

### Points d'analyse obligatoires

1. **Quelle méthode donne la meilleure latence en charge ?**
   - Comparer les temps moyens et P95
   - Justifier avec les données

2. **Le débit maximal observé pour chaque méthode ?**
   - Identifier la méthode la plus performante
   - Analyser les goulots d'étranglement

3. **Quelle méthode est la plus simple à maintenir ?**
   - Lisibilité du code
   - Effort de configuration
   - Facilité de débogage

4. **Quel impact du discovery (Eureka vs Consul) sur latence et stabilité ?**
   - Comparer les performances
   - Analyser la stabilité en cas de panne

5. **Que se passe-t-il pendant une panne ?**
   - Échec immédiat ou délai ?
   - Temps de reprise
   - Comportement de chaque client

## 📦 Livrables

1. ✅ **Code des 2 services** (client + voiture)
2. ✅ **Preuve d'enregistrement** (capture Eureka/Consul)
3. ✅ **Résultats de tests** (latence, débit, CPU/RAM)
4. ✅ **Analyse comparée** (1-2 pages)

## 🛠️ Structure du Projet

```
.
├── service-voiture/          # Microservice Voiture
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/mliaedu/voiture/
│   │   │   │       ├── ServiceVoitureApplication.java
│   │   │   │       ├── controller/
│   │   │   │       ├── model/
│   │   │   │       └── service/
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── application-consul.yml
│   └── pom.xml
├── service-client/           # Microservice Client
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/mliaedu/client/
│   │   │   │       ├── ServiceClientApplication.java
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── config/
│   │   │   │       ├── feign/
│   │   │   │       └── model/
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── application-consul.yml
│   └── pom.xml
├── eureka-server/           # Serveur Eureka
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/mliaedu/eureka/
│   │   │   │       └── EurekaServerApplication.java
│   │   │   └── resources/
│   │   │       └── application.yml
│   └── pom.xml
└── README.md                # Ce fichier
```

## 📚 Ressources

- [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign)
- [Spring WebClient](https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html)
- [Netflix Eureka](https://github.com/Netflix/eureka)
- [HashiCorp Consul](https://www.consul.io/)

## ⚠️ Notes Importantes

1. **Délai artificiel** : Le Service Voiture inclut un délai de 20ms pour rendre les différences entre clients plus observables. Ce délai est optionnel et peut être supprimé.

2. **WebClient en mode synchrone** : Dans ce TP, WebClient est utilisé en mode synchrone via `block()` pour comparer à armes égales avec RestTemplate et Feign. En production, WebClient est généralement utilisé en mode non-bloquant.

3. **Base de données en mémoire** : Pour éviter que MySQL n'influence les mesures, on utilise une Map en mémoire. C'est suffisant pour comparer les clients HTTP.

4. **Ordre de démarrage** : Démarrer Eureka/Consul **avant** les services. Sinon, l'enregistrement peut échouer ou être retardé.

## 🐛 Dépannage

### Problème : Service non enregistré dans Eureka/Consul

- Vérifier que Eureka/Consul est démarré
- Vérifier la configuration dans `application.yml`
- Vérifier les logs pour les erreurs de connexion

### Problème : Erreur 404 lors de l'appel par nom logique

- Vérifier que le service est bien enregistré
- Vérifier le nom du service dans `application.yml`
- Vérifier la présence de `@LoadBalanced` pour RestTemplate/WebClient

### Problème : Port déjà utilisé

- Changer le port dans `application.yml`
- Ou arrêter le processus utilisant le port

## 📧 Contact

Pour toute question, contactez votre enseignant ou consultez la documentation Spring Cloud.

---

**Bon TP ! 🚀**