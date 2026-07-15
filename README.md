# Enterprise AI Knowledge Hub

Welcome to the **Enterprise AI Knowledge Hub**! 

This repository contains a fully-fledged, enterprise-grade application built from the ground up to serve as a conversational AI knowledge base for internal enterprise documents. It is designed to demonstrate real-world architecture, scalable patterns, and production-ready code.

## 📁 Repository Structure

* `docs/`: Contains all project documentation (SRS, Architecture, API specs, Decisions).
* `backend/`: The Spring Boot Java backend (Microservices, RAG pipeline, Database).
* `frontend/`: The Angular user interface.
* `docker/`: Docker Compose and containerization configurations.
* `diagrams/`: System architecture, sequence, and flow diagrams.

## 🚀 Tech Stack

* **Backend:** Java, Spring Boot, Spring Security, Spring Cloud
* **Database:** PostgreSQL, pgvector (for vector embeddings), Redis (for caching)
* **Messaging:** Apache Kafka
* **AI/RAG:** LangChain4j, Gemini / OpenAI API
* **Frontend:** Angular
* **DevOps:** Docker, Git

*(For a detailed breakdown, please see [docs/Tech_Stack.md](docs/Tech_Stack.md))*

## 🎯 Project Goals

This project follows a **concept-driven** approach. We introduce technologies (like Kafka or Redis) only when the business problem or scale demands it, mirroring how real enterprise systems evolve from monoliths to microservices.

---
*This repository is currently under active development. Stay tuned for updates!*
