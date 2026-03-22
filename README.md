# JobPortal – Backend Microservices Application

## Overview
JobPortal is a backend-focused, microservices-based application built using Java and Spring Boot. The project demonstrates real-world backend engineering concepts such as scalability, clean architecture, asynchronous communication, and system design best practices.

---

## Key Features
- Microservices-based backend architecture
- Centralized API Gateway
- Job creation and job search with pagination and filtering
- User (Party) registration and profile management
- Resume upload and processing
- AI-powered resume feedback and chatbot features
- Kafka-based asynchronous processing
- Redis caching for performance optimization

---

## Tech Stack

- **Backend:** Java, Spring Boot  
- **Security:** Spring Security, JWT  
- **Architecture:** Microservices, REST APIs  
- **Messaging:** Kafka (Event-driven communication)  
- **Caching:** Redis  
- **Database:** MySQL / PostgreSQL  
- **File Storage:** Cloudinary  
- **Containerization:** Docker  
- **Deployment:** Render

---

## Architecture Overview

```
Client
  |
  v
API Gateway
  |
  |-------------------------------|-------------------------------|
  v                               v                               v
Job Service                  Party Service                   Resume Service
  |
  v
Kafka (Job / Resume Events)
  |
  v
Spring AI Service

Redis is used for caching and async responses.
Each microservice uses its own MySQL schema.

 **Live APIs**

> ⚠️ Note: APIs are exposed per microservice

- **Job Service (Swagger):**  
  https://job-service-1ltr.onrender.com/swagger-ui/index.html

- **Party Service (Swagger):**  
  https://jobportal-1-x061.onrender.com/swagger-ui/index.html

---

## Microservices
- API Gateway: Single entry point and request routing
- Job Service: Job management and search
- Party Service: User registration and profile management
- Resume Service: Resume upload and event publishing
- Spring AI Service: Resume analysis, chatbot processing and send email on job Publish.

---

## How to Run (High Level)
1. Clone the repository
2. Configure MySQL schemas
3. Start Kafka and Redis
4. Run each Spring Boot service
5. Access APIs via API Gateway

---

## Author
Saurav Priyadarshi  
Backend Software Engineer (Java, Spring Boot, Microservices)

LinkedIn: https://www.linkedin.com/in/saurav-priyadarshi-4b8b101bb/
