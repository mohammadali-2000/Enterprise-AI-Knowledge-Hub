# Architecture Decision Records (ADRs)

## ADR-001: Why start with a Modular Monolith?

**Context:** We need to decide on the initial deployment architecture for the Enterprise AI Knowledge Hub. The industry trend heavily favors Microservices, but our team is currently small, and the domain boundaries are not yet battle-tested.

**Decision:** We will build a Modular Monolith in Spring Boot for Phase 1. The code will be strictly divided into `User`, `Knowledge`, and `AI` modules.

**Alternatives considered:** 
- Starting with Microservices (rejected due to high operational overhead and complexity in CI/CD, networking, and distributed transactions).

**Pros:**
- Simple deployment (single JAR).
- Easy debugging and local development.
- Fast inter-module communication (method calls instead of HTTP).

**Cons:**
- All components must scale together (e.g., if the AI module needs more RAM, we must scale the whole monolith).
- A bug in one module can crash the entire application.

**Consequences:** 
We must rigorously enforce module boundaries in the code (e.g., using Java package visibility or ArchUnit tests) so we can easily break this into microservices later.

---

## ADR-002: Why PostgreSQL + pgvector?

**Context:** RAG (Retrieval-Augmented Generation) requires a way to store and perform mathematical similarity searches on vector embeddings.

**Decision:** We will use PostgreSQL as our primary relational database, and enable the `pgvector` extension to store and search the document vectors.

**Alternatives considered:**
- Dedicated Vector Databases (Pinecone, Milvus, Weaviate).

**Pros:**
- We already need a relational database for Users and standard Metadata.
- Reduces operational complexity (we only manage one database system instead of two).
- `pgvector` allows us to perform standard SQL `JOIN` operations alongside vector similarity searches (e.g., "Find the closest vectors BUT ONLY for documents uploaded by User X").

**Cons:**
- Not as specialized or potentially as fast as dedicated vector databases at massive scale (billions of vectors).

**Consequences:**
We must ensure our PostgreSQL instance is provisioned with enough memory, as vector index searches are memory-intensive.
