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

## Setup and Installations

## Clone the Repository
```bash
git clone https://github.com/your-username/subscription-api.git
cd "java maven x"
```

## Configure Database and API Keys
```bash
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_URL=
STRIPE_SECRET_KEY=
STRIPE_WEBHOOK_SECRET=
SECRET_KEY=
```

## Docker Build Porject
```bash
./mvnw clean package -DskipTests
docker build -t demo-app:latest .
docker-compose up --build -d
```
