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
