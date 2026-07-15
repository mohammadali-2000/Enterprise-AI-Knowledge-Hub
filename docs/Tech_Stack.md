# 🛠️ Enterprise AI Knowledge Hub - Tech Stack

This document outlines the **stable, production-ready technology stack** we will be using for this project. We are selecting industry-standard tools that you will frequently encounter in real enterprise environments.

---

## 1. Core Languages & Frameworks
* **Java (version 17 or 21):** The foundation of our backend. We will rely heavily on Core Java (Collections, Streams, Lambdas) and Object-Oriented Programming principles.
* **Spring Boot (version 3.x):** The core framework for building our backend APIs rapidly and with enterprise-grade standards.
* **Spring Framework:** Specifically, we will use **Spring Web (MVC)** for REST APIs, **Spring Data JPA** for database interactions, and **Spring Security** for securing our endpoints.
* **Angular (latest stable, e.g., 17/18):** A robust, component-based framework for building our frontend user interface.

## 2. Database & Data Storage
* **PostgreSQL:** A highly stable, open-source relational database. We will use this as our primary data store.
* **pgvector:** A PostgreSQL extension that allows us to store and query vector embeddings (crucial for our AI/RAG features) without needing a completely separate vector database.
* **Redis:** An in-memory data structure store, used as a highly performant cache (e.g., for caching frequently accessed user data or chat history).
* **Hibernate / JPA:** The ORM (Object-Relational Mapping) framework used by Spring to translate Java objects into PostgreSQL database tables.

## 3. Architecture & Microservices
*Initially, we will start as a Monolith and gradually evolve into this Microservices stack:*
* **Spring Cloud Netflix Eureka:** For Service Discovery (allowing microservices to find each other).
* **Spring Cloud Gateway:** Our API Gateway to route frontend requests to the correct backend microservice.
* **Spring Cloud Config:** For centralized configuration management across all microservices.
* **OpenFeign:** For seamless and clean REST client communication between our microservices.

## 4. Asynchronous Messaging
* **Apache Kafka:** A distributed event streaming platform. We will use Kafka to handle long-running, asynchronous tasks, such as document processing and vectorization in the background.

## 5. Security
* **JWT (JSON Web Tokens):** For stateless, secure authentication between the Angular frontend and Spring Boot backend.
* **Spring Security:** For intercepting requests, enforcing role-based access control (RBAC), and validating JWTs.

## 6. Artificial Intelligence (AI) & RAG
* **LangChain4j:** The Java equivalent of LangChain. It provides a standard, easy-to-use interface to connect our Spring Boot app with AI models and vector databases.
* **Gemini API (or OpenAI API):** The Large Language Model (LLM) we will use to generate conversational answers based on the uploaded documents.

## 7. DevOps, Containerization & Deployment
* **Docker:** To containerize our applications (Frontend, Backend, Database, Kafka, Redis) so they run identically on any machine.
* **Docker Compose:** To easily spin up our entire local development environment with a single command.
* **Git:** For version control, utilizing branching strategies like Git Flow.

## 8. Testing & Monitoring
* **JUnit 5 & Mockito:** For writing robust unit tests and mocking dependencies.
* **Testcontainers:** For running integration tests using real, lightweight Docker containers (e.g., spinning up a real Postgres DB just for a test).
* **Spring Boot Actuator & Micrometer:** To expose metrics and health checks for our application.
* **Prometheus & Grafana:** For scraping metrics and visualizing application health, performance, and API traffic in real-time dashboards.

---
### 💡 Concept-Driven Reminder:
We won't install all of these at once. For example, we won't add Kafka until we actually hit a performance bottleneck with document processing that requires asynchronous messaging. We will introduce each technology **only when the business problem demands it**.
