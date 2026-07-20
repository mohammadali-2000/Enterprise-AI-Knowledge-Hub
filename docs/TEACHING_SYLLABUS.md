# Mentor's Teaching Syllabus
*This document tracks the topics the AI Mentor must cover to ensure the user is fully prepared for Senior-level Java/Spring Boot interviews.*

## Module 1: The Core Java to Spring Boot Bridge
- [x] **Inversion of Control (IoC):** Explain why we stop using `new Object()` and let Spring manage objects.
- [x] **Dependency Injection (DI):** Explain `@Autowired` and Constructor Injection (and why Constructor Injection is better).
- [x] **Beans:** What is a Spring Bean, and what is its lifecycle?
- [x] **Annotations:** Unpacking the magic behind `@SpringBootApplication` and `@Component`.

## Module 2: The 3-Tier Architecture
- [x] **Controllers (`@RestController`):** The waiters taking the order. Handling HTTP requests and responses.
- [x] **Services (`@Service`):** The chefs cooking the food. Where the complex business logic lives.
- [x] **Repositories (`@Repository`):** The pantry. How Spring Data JPA interacts with PostgreSQL without writing manual SQL.
- [x] **DTOs (Data Transfer Objects):** Why we never expose Database Entities directly to the outside world.

## Module 3: Security & Advanced Concepts
- [ ] **Spring Security Filter Chain:** How every HTTP request is intercepted and checked for a JWT.
- [x] **Exception Handling (`@ControllerAdvice`):** How to catch errors globally and return clean JSON error messages.
- [x] **Transactions (`@Transactional`):** ACID properties. What happens if saving a Document works, but saving the Chunks fails? (Rollback).
