# My Learning Tracker
*This document tracks everything I have successfully learned and can confidently explain in an interview.*

## Phase 1: Architecture & Data
- [x] **Monolithic Architecture:** I know why we start with a Modular Monolith (simplicity, easy refactoring, single JVM) before jumping to Microservices.
- [x] **Database Isolation (Shared Nothing):** I know we must not use Foreign Keys between logical modules (like User and Knowledge) so that they can be easily separated into Microservices later.
- [x] **Asynchronous Processing:** I know that heavy tasks (like processing a 500-page PDF) must be done asynchronously (background threads/Kafka) so the user's HTTP request doesn't time out.
- [x] **Database Design:** I know how a 1-to-Many relationship works (1 Document has Many Document_Chunks).
- [x] **pgvector:** I know we use PostgreSQL with the `pgvector` extension to store and search mathematical vectors for our AI RAG pipeline.

## Phase 2: Core Java & Spring Boot
- [x] **Inversion of Control (IoC):** I know that Spring manages object creation so I don't have to use the `new` keyword everywhere.
- [x] **Dependency Injection (DI):** I know that if one class needs another, Spring "injects" it automatically (usually via the constructor).
- [ ] *Pending: What is a Bean?*
- [x] **3-Tier Architecture:** I know the difference between a Controller (waiter), a Service (chef), and a Repository (pantry), and why they are separated.
