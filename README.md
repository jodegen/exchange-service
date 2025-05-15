---

# Exchange Rate Service

A microservice providing real-time and historical exchange rate data. Designed to be part of a modular, distributed microservice architecture.

## 🧩 Features

- Provides exchange rates between currencies
- Registers with Eureka for discovery
- Can be accessed via the API Gateway

## 🚀 Technologies

- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Spring Cloud Eureka Client

## 🛠️ How to Run

### Docker

```bash
docker build -t exchange-rate-service .
docker run -d -p 4455:4455 --name exchange-rate-service exchange-rate-service
