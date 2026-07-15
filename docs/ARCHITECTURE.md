# Architecture Principles

This document outlines the core architectural principles governing the Enterprise AI Knowledge Hub.

## 1. Modular Monolith Architecture
We are starting with a **Modular Monolith** rather than jumping straight to Microservices. 

### Why?
- **Lower Cognitive Load:** We can focus on business logic (RAG, vector search) instead of distributed systems complexity (network failures, distributed tracing).
- **Refactoring is easy:** If we get the module boundaries wrong, fixing them inside a monolith is a simple IDE refactor. Fixing boundaries across microservices is a massive migration.
- **Future-Proof:** By enforcing strict module boundaries now (e.g., the User Module cannot directly access the Knowledge Module's internal classes), we guarantee that splitting into Microservices later (Phase 8) will be seamless.

## 2. Database Isolation (The "Shared Nothing" Principle)
**Design Decision:** The `User Module` and `Knowledge Module` will NOT share tables or foreign keys.

Even though they live in the same PostgreSQL database initially, their schemas are logically separated. 
- **Reasoning:** If we use Foreign Keys tying a `Document` table to a `User` table, we couple the two modules at the database level. When we eventually extract the `Knowledge Module` into its own microservice with its own database, that foreign key constraint will break. 
- **Solution:** Modules will only store IDs (e.g., the `Document` table stores an `uploaded_by_user_id` integer, but does not enforce a hard database foreign key to the User table).

## 3. Asynchronous Processing
**Design Decision:** Long-running tasks (like PDF ingestion) must not block the main thread.

- **The Problem:** Processing a 500-page PDF (extracting text, chunking, hitting an embedding API, saving to DB) can take several minutes. If the HTTP request waits for this, the connection will time out, and the user will stare at a frozen screen.
- **Solution:** 
  1. The API immediately accepts the upload, saves the file to disk/S3, and returns a `202 Accepted` status with a `job_id`.
  2. The actual processing is handed off to an asynchronous background worker (initially Spring's `@Async`, later Apache Kafka).
  3. The frontend polls the backend using the `job_id` (or listens via WebSockets) to update the UI progress bar.
