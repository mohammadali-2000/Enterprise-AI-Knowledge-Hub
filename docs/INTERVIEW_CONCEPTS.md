# Interview Concepts Guide
*This document contains detailed explanations of core architectural concepts to help you prepare for technical interviews.*

---

## 1. Monolith vs. Microservices

One of the most frequently asked questions in a System Design or Backend Developer interview is to explain the difference between a Monolith and Microservices, and when to use each.

### The Monolith (One Giant Building)
Imagine a restaurant where the Kitchen, the Bar, and the Cash Register are all crammed into one single, giant room. 

* **The Good:** It is very easy to build, test, and deploy. The waiter can just shout across the room to the chef. Everything is connected.
* **The Bad:** If the kitchen catches on fire, the **entire restaurant burns down**. You cannot even serve drinks anymore.
* **In Technical Terms:** A Monolithic Architecture means *all* of your code (User Login, PDF Uploading, AI Chatting) is stuffed into **one single project** and compiled into one single `.jar` file. It runs on a single server. If a bug in the PDF uploader causes the server to crash, the entire application crashes, and nobody can even log in.

### Microservices (A Food Court)
Now imagine a food court. You have a separate small building for Drinks, a separate building for Pizza, and a separate building for Burgers.

* **The Good (Fault Tolerance & Scaling):** If the Pizza oven explodes, the Drinks building is completely safe and still making money! Also, if 10,000 people suddenly want pizza, you can easily hire 50 more pizza chefs (add more pizza servers) without touching the burger building.
* **The Bad (Complexity):** It is very complicated to build and maintain. The buildings have to communicate with each other over radios (networks like HTTP or Kafka), which can be slow, fail unexpectedly, and are hard to debug.
* **In Technical Terms:** Every single feature is its own separate Spring Boot project, running on its own separate server, with its own separate database.

---

### What are WE building? (The "Modular Monolith")
If you start a brand new project with Microservices on Day 1, you will spend 80% of your time configuring servers instead of writing code. 

So, we are building a **Modular Monolith**. 

It is one big building (one Spring Boot project in our `backend` folder), but we are building **thick fireproof walls** between our code domains. We keep the `User` code completely isolated from the `Document` code. They do not share database tables directly (no Foreign Keys between domains).

**Why this is the best approach:**
It is easy to run on your laptop right now (just one click to start). But later, if your app goes viral and you need to split it into Microservices to handle a million users, you can easily take a sledgehammer, break the walls, and move the code into separate servers without having to rewrite the entire application.

---

## 2. Inversion of Control (IoC) & Dependency Injection (DI)

This is the most critical concept in Spring Boot. If you cannot explain this, you will likely fail a Java Backend interview.

### The Problem with Core Java (Tightly Coupled Code)
In standard Core Java, if a class needs another class to do its job, **YOU** (the programmer) must manually create it using the `new` keyword.

Imagine a `Car` that needs a `V8Engine` to drive:
```java
// Core Java Approach (BAD for Enterprise)
public class Car {
    private V8Engine engine;

    public Car() {
        // The Car is FORCED to manually build its own engine.
        this.engine = new V8Engine(); 
    }
}
```
**Why is this bad?** This code is "Tightly Coupled." What happens if next year, the company wants to switch to an `ElectricEngine`? You have to open the `Car.java` file, delete `new V8Engine()`, and rewrite the code. If your app has 500 classes that use an engine, you have to rewrite code in 500 places.

### The Spring Boot Solution: Inversion of Control (IoC)
Spring Boot says: **"Stop using the `new` keyword! Let me create, manage, and destroy objects for you."**

Because the *control* of creating objects has been taken away from the programmer and given to the Spring framework, we call this **Inversion of Control (IoC)**.

When Spring creates an object for you and stores it in its memory, that object is called a **Spring Bean**.

### How does Spring give us the object? (Dependency Injection)
If Spring is holding the Engine in its memory, how does the Car get it? Spring "injects" it into the Car. This is called **Dependency Injection (DI)**.

You simply put a special annotation (like `@Component` or `@Service`) on top of your class to tell Spring: *"Hey, make this a Bean and put it in your memory."*

```java
// Spring Boot Approach (GOOD for Enterprise)

@Component // Tells Spring: "Create one Engine and hold it in memory"
public class Engine {
    // Engine logic here
}

@Component
public class Car {
    private Engine engine;

    // @Autowired tells Spring: "Please inject the Engine from your memory into this Car"
    @Autowired 
    public Car(Engine engine) {
        this.engine = engine; 
    }
}
```

**Why is this beautiful?**
Notice there is **no `new` keyword**. The `Car` does not care if the engine is a V8 Engine or an Electric Engine. It just says, *"Hey Spring, give me whatever engine you have in your memory."* This is called "Loosely Coupled" code, and it is the foundation of every Enterprise Java application in the world.

---

## 3. Spring Boot Annotations (Cheat Sheet)

When you look at Spring Boot code (like `User.java`), you will see words starting with `@`. These are called **Annotations**. In Core Java, you have to write hundreds of lines of code to do simple things. In Spring Boot, you just type an `@Annotation` and Spring writes the code for you in the background!

Here are the annotations we just used in `User.java`:

### Database Annotations (JPA/Hibernate)
* `@Entity`: Tells Spring, *"This Java class represents a Table in the PostgreSQL database."*
* `@Table(name = "users")`: Explicitly tells Spring exactly what the database table is named.
* `@Id`: Tells Spring, *"This specific variable (id) is the Primary Key."*
* `@GeneratedValue(strategy = GenerationType.UUID)`: Tells Spring, *"When I create a new User, DO NOT ask me for an ID. Generate a random, unique UUID string for me automatically."*

### Spring Core Annotations (The Architecture)

* `@RestController`
  * **Simple Meaning:** This makes a class the "Front Desk Teller" of your application.
  * **Detail:** It tells Spring Boot that this class will talk directly to the internet. When a user visits a URL (like `/api/users/login`), this class takes their request and returns data back to them. It does not do any complex math or heavy thinking.
  * **Diagram:** `Internet Request  -->  [@RestController (Teller)]  -->  Passes work to the Service`

* `@Service`
  * **Simple Meaning:** This makes a class the "Manager" or "Chef". 
  * **Detail:** This is where the actual brain of your application lives. It contains all the "Business Logic". For example, if you need to check passwords, validate emails, or calculate taxes, you write that code here. The Service does the hard work.
  * **Diagram:** `[@RestController (Teller)]  -->  [@Service (Manager does the math)]  -->  Saves to Database`

* `@Component`
  * **Simple Meaning:** This tells Spring: "Please create this object and keep it in your memory box."
  * **Detail:** This is the most basic building block in Spring Boot. When the app starts, Spring looks for any class with `@Component` and creates one copy of it. `@RestController` and `@Service` are actually just special, specific versions of `@Component`.
  * **Diagram:** `[Spring Memory Box] holds --> [@Component (Object 1)], [@Component (Object 2)]`

* `@Autowired`
  * **Simple Meaning:** This is how we do "Dependency Injection" (plugging things together).
  * **Detail:** When one class needs help from another class, you don't build a new one from scratch. You use `@Autowired` to tell Spring: "Hey, look in your memory box, find the tool I need, and plug it into my code automatically."
  * **Diagram:** `[Controller needs a Service] + @Autowired = Spring grabs the [Service] and plugs it in.`

### Lombok Annotations (The Time Savers)
Lombok is a special plugin that saves you from writing boring code.
* `@Data`: If you write this, you **never have to write Getters and Setters again**. Lombok secretly writes `getEmail()` and `setEmail()` for every single variable in the background.
* `@NoArgsConstructor`: Secretly creates an empty constructor `public User() {}` which Spring Boot requires to work properly.

---

## 4. The 3-Tier Architecture (Spring Boot Folder Structure)

When you look at a Spring Boot project, there are a lot of folders. In an interview, if you are asked how you structure your code, you must explain the **3-Tier Architecture**. We separate our code into different layers so that our application is easy to maintain, test, and scale.

Think of our application as a **Large Bank**. 

### Layer 1: The Controller (`/controller`)
**Analogy:** The Front Desk Teller.
**Job:** The Controller is the only part of our code that talks to the outside world (the internet). It takes the customer's request (HTTP Request) and returns the final result (HTTP Response). 
**Rules:** 
* A Front Desk Teller does not approve million-dollar loans! 
* A Controller should *never* contain complex business logic (like checking if a password is valid). It just takes the request and passes it to the Manager.

### Layer 2: The Service (`/service`)
**Analogy:** The Bank Manager / Loan Officer.
**Job:** This is the brain of the application. All complex business rules live here. 
**Rules:** 
* If a user registers, the Service (Manager) does the hard work: checks if the email is valid, encrypts the password, and checks if the user already exists. 
* The Service does not talk to the customer directly (that's the Teller's job), and it does not walk into the vault to get money directly. It asks the Vault Guard.

### Layer 3: The Repository (`/repository`)
**Analogy:** The Vault Security Guard.
**Job:** This layer is strictly responsible for talking to the Database (the Vault). 
**Rules:** 
* The Vault Guard doesn't care about business rules; they just put data in or take data out when the Manager asks.
* Thanks to Spring Data JPA, we usually don't even have to write SQL code here. We just create an Interface, and Spring writes the SQL to open the Vault automatically.

---

### The Other Important Folders (The Supporting Cast)

Besides the 3 main tiers, you will see a few other folders. Here is what they do:

#### 1. Entity (`/entity`)
**Analogy:** The Raw Ingredients.
**Job:** These are plain Java classes that map perfectly to the tables in your database. One `User.java` class equals one row in the `USERS` database table.

#### 2. DTO - Data Transfer Object (`/dto`)
**Analogy:** The Takeout Box (or the Menu).
**Job:** We **NEVER** send our raw Entities (Ingredients) out to the internet, because an Entity contains secret data (like password hashes). Instead, we copy the safe data into a DTO (Takeout Box) and send that to the user. DTOs are also used to receive data safely.

#### 3. Config (`/config`)
**Analogy:** The Restaurant Manager.
**Job:** This is where we put configuration files. For example, if we want to set up Spring Security (our bouncer) or configure how our AI tools connect to the internet, we write those settings here.

#### 4. Exception (`/exception`)
**Analogy:** The Complaint Desk.
**Job:** If something goes wrong (e.g., a user asks for a document that doesn't exist), our code "throws an exception". We put special classes in this folder that catch those errors and send a nice, readable error message back to the user instead of crashing the server.

---

## 5. Q&A Review (Everything You Should Know: Phases 1 to 5)

If you are asked these questions in an interview *today*, you should be able to answer them confidently based on what we have built so far.

**Q1 (Phase 1): Why did you choose a Modular Monolith architecture instead of Microservices for this project?**
**Answer:** "Because starting with Microservices introduces massive infrastructure overhead and complexity. By building a Modular Monolith, we keep everything in a single application (easy to run and debug), but we enforce strict boundaries between domains (like User and Document). If the app scales massively later, we can easily split those boundaries into Microservices without having to rewrite the code."

**Q2 (Phase 2): What is Inversion of Control (IoC) and why is it useful?**
**Answer:** "IoC means giving control of object creation to the Spring Framework. Instead of tightly coupling our code by using the `new` keyword everywhere, Spring manages the objects (Beans) in its memory. This makes our code loosely coupled, easier to test, and highly maintainable."

**Q3 (Phase 2): How does Dependency Injection (DI) work in your application?**
**Answer:** "When a class needs a dependency—for example, our `UserService` needs the `UserRepository` to talk to the database—we don't instantiate the repository manually. We use `@Autowired` in the constructor, and Spring automatically 'injects' the repository Bean into our service when the application starts."

**Q4 (Phase 4): Can you explain the 3-Tier Architecture you are using?**
**Answer:** "We use a Controller-Service-Repository pattern. 
1. The **Controller** handles incoming HTTP requests and returns responses (like a Front Desk Teller). It contains no business logic.
2. The **Service** contains all the complex business rules and validations (like a Bank Manager).
3. The **Repository** is strictly responsible for database operations using Spring Data JPA (like a Vault Guard)."

**Q5 (Phase 5): What is the difference between an Entity and a Repository?**
**Answer:** "An Entity is a plain Java class annotated with `@Entity` that maps exactly to a table in the database (e.g., the `User` class maps to the `USERS` table). A Repository is an interface extending `JpaRepository` that gives us out-of-the-box methods to query, save, and delete those Entities without writing raw SQL."

---

## 6. Database Transactions (`@Transactional`)

Imagine you go to a bank to transfer $100 to a friend. The database must:
1. Subtract $100 from your account.
2. Add $100 to your friend's account.

If the server crashes exactly between step 1 and step 2, your money is gone, but your friend never got it. This is a catastrophic failure.

To prevent this, databases use **Transactions**. A transaction guarantees **ACID properties**, specifically **Atomicity** (All or Nothing). Either every single step succeeds, or if even a single step fails, the database automatically **Rolls Back** (undoes) everything so the data is never left in a corrupted state.

In Spring Boot, we implement this simply by adding the `@Transactional` annotation to our Service (Chef) methods. 

---

## 7. Swagger UI & OpenAPI

When building a Backend API, you write code that receives requests and sends responses. But how do the Frontend Developers (or the people writing Mobile Apps) know *how* to talk to your Backend? How do they know what URLs exist, what JSON to send, and what responses to expect?

You have to write **API Documentation**.

In the old days, developers had to manually write giant Word documents explaining their APIs. When the code changed, the Word documents got outdated, causing massive confusion.

### What is OpenAPI?
OpenAPI is a universal standard (a set of rules) for describing REST APIs. It is essentially a giant JSON file that formally describes every single endpoint, parameter, and response your application has.

### What is Swagger UI?
Swagger UI is a tool that reads that OpenAPI JSON file and automatically generates a beautiful, interactive web page (the one you just clicked on). 

**Why is it important in an enterprise?**
1. **Auto-Generated:** You never have to manually write documentation. Spring Boot (using the `springdoc-openapi` library) looks at your `@RestController` classes and automatically generates the Swagger page on the fly.
2. **Interactive Testing:** As you saw, you don't need to write complex Terminal/Curl commands or use tools like Postman just to test if your code works. You can test it directly from the browser by clicking "Try it out".
3. **Contract with Frontend:** It acts as an unbreakable "Contract". The Frontend team can look at Swagger and immediately know exactly how to build their screens without ever having to ask the Backend team questions.

---

## 8. Spring Security & JWT (The VIP Nightclub)

This is the most critical part of an enterprise application. By default, Spring Boot endpoints (like `/api/documents`) are wide open to the public internet. Anyone can access them. We have to lock the doors.

### The Problem
If a user logs in with their password, how do they stay logged in? We don't want them to have to send their password every single time they click a button or upload a document. That would be incredibly slow and insecure.

### The Solution: JWT (JSON Web Token)
Think of our Application as an exclusive VIP Nightclub.

1. **Spring Security (The Bouncer):** We configure a "Filter Chain" (like `JwtAuthenticationFilter`). This is a physical Bouncer standing at the front door. He intercepts *every single HTTP request* before it even reaches the Controller (Front Desk).
2. **Login (Showing ID):** When a user goes to `/api/users/login`, they give the Bouncer their email and password. 
3. **JWT (The VIP Wristband):** If the password is correct, the Bouncer doesn't want to ask for the password ever again. Instead, he hands the user a cryptographically secure "VIP Wristband" (A long string of random text called a JWT).
4. **Future Requests:** For all future requests (like uploading a document), the user just attaches that JWT Wristband to their request header (`Authorization: Bearer <token>`). The Bouncer looks at the wristband, sees it is valid, and lets them in instantly without needing a password!

**Why is JWT so popular? (Statelessness)**
In the old days, the server had to memorize exactly who was logged in (this is called storing "Sessions" in memory). But what if you have 10 million users and 50 servers? Memorizing everyone is impossible.
A JWT is **Stateless**. The server doesn't have to memorize anything! The server just looks at the math signature on the wristband. If the math checks out, the server knows it's a real wristband that *we* generated, so the user is allowed in. This makes scaling infinitely easier.
