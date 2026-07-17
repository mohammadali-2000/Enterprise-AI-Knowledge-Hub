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
