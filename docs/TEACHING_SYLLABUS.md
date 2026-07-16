# Mentor's Teaching Syllabus
*This document tracks the topics the AI Mentor must cover to ensure the user is fully prepared for Senior-level Java/Spring Boot interviews.*

## Module 1: The Core Java to Spring Boot Bridge
- [ ] **Inversion of Control (IoC):** Explain why we stop using `new Object()` and let Spring manage objects.
- [ ] **Dependency Injection (DI):** Explain `@Autowired` and Constructor Injection (and why Constructor Injection is better).
- [ ] **Beans:** What is a Spring Bean, and what is its lifecycle?
- [ ] **Annotations:** Unpacking the magic behind `@SpringBootApplication` and `@Component`.

## Module 2: The 3-Tier Architecture
- [ ] **Controllers (`@RestController`):** The waiters taking the order. Handling HTTP requests and responses.
- [ ] **Services (`@Service`):** The chefs cooking the food. Where the complex business logic lives.
- [ ] **Repositories (`@Repository`):** The pantry. How Spring Data JPA interacts with PostgreSQL without writing manual SQL.
- [ ] **DTOs (Data Transfer Objects):** Why we never expose Database Entities directly to the outside world.

## Module 3: Security & Advanced Concepts
- [ ] **Spring Security Filter Chain:** How every HTTP request is intercepted and checked for a JWT.
- [ ] **Exception Handling (`@ControllerAdvice`):** How to catch errors globally and return clean JSON error messages.
- [ ] **Transactions (`@Transactional`):** ACID properties. What happens if saving a Document works, but saving the Chunks fails? (Rollback).
