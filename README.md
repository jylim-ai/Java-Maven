# Subscription Platform Backend

A scalable subscription management backend built with Spring Boot.
This system supports user registration, subscription plans, payment integration.


## Tech Stack

Java 21+

Spring Boot

Spring Web

Spring Data JPA

Spring Security (JWT-based authentication)

Hibernate

PostgreSQL

Maven

## Architecture Overview
```bash
Client (Web)
        ↓
Controller (Spring Boot)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (JPA / Hibernate)
        ↓
Database (MySQL / PostgreSQL)
```

## Features

### User Management

User registration

Login / JWT authentication

### Subscription Management

Create subscription plans

Subscribe / cancel plan

### Post Management

Create, update post 

### Billing

Stipe API for payment gateway
