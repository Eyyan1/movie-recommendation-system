# Movie Recommendation System
## Project Report Summary

**Author:** [Your Name]  
**Date:** March 28, 2026

---

## Overview

The Movie Recommendation System is a Java Spring Boot web application that allows users to register, log in, browse movies, rate them, and receive personalized recommendations. The system also supports admin movie management and TMDb-based movie import into a local PostgreSQL database.

The project was designed as an MVP-focused full-stack application that demonstrates secure authentication, server-rendered UI development, relational data modeling, external API integration, recommendation logic, and deployment readiness.

---

## Problem and Purpose

Movie platforms often contain large catalogs but lack transparent and understandable recommendation workflows for academic or demonstration projects. This system addresses that gap by providing a recommendation engine based on a simple and explainable rule set.

The goal of the project is to show how a modern Java web application can combine:

- authentication and authorization
- movie catalog management
- user ratings
- recommendation logic
- automated testing
- environment-based deployment preparation

---

## Core Features

- User registration and login with Spring Security
- Login using username or email
- Role-based access for `USER` and `ADMIN`
- Movie import from TMDb using Bearer token authentication
- Local movie browsing from PostgreSQL
- Search by title and filter by genre
- Movie detail page with metadata and description
- Rating system with create-or-update behavior
- “My Ratings” page
- Personalized recommendations based on highly rated genres
- Cold-start fallback recommendations
- Admin movie CRUD
- Shared Thymeleaf layout with role-aware navigation
- Custom access denied and error pages
- Automated tests for key MVP flows

---

## Architecture Summary

The system follows a layered monolithic architecture:

- **Controller layer**
  Handles routing and web requests
- **Service layer**
  Contains business logic
- **Repository layer**
  Uses Spring Data JPA for persistence
- **Entity layer**
  Models users, movies, genres, and ratings
- **Integration layer**
  Encapsulates TMDb API access
- **Frontend**
  Uses Thymeleaf and Bootstrap for server-rendered pages

[Insert architecture diagram here]

---

## Recommendation Logic

The recommendation engine uses a rule-based approach:

1. Select movies the user rated `>= 4`
2. Determine favorite genres from those ratings
3. Exclude movies already rated by the user
4. Rank candidate movies using:
   - genre preference strength
   - average movie rating
   - popularity

If the user has no strong rating history, the system falls back to recommending unrated movies with the best local average ratings and popularity.

This design keeps the recommendation engine explainable and suitable for an MVP.

---

## Security Summary

The project uses Spring Security with server-side sessions. Passwords are hashed using BCrypt, and route access is controlled by role.

- public pages: home, login, register
- authenticated pages: movies, ratings, recommendations
- admin-only pages: `/admin/**`

Secrets such as database credentials and the TMDb token are externalized through environment variables.

---

## Testing Summary

The project includes a practical automated test suite focused on high-value flows:

- registration service and controller
- movie browse/detail controller flow
- rating submission and update logic
- recommendation service logic
- admin access restriction

This testing approach balances confidence and simplicity for an MVP-scale project.

---

## Deployment Summary

The application is prepared for deployment using environment variables and can be deployed on a student-friendly platform such as Render.

Required environment variables include:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `TMDB_READ_ACCESS_TOKEN`
- `SERVER_PORT`
- `JPA_DDL_AUTO`
- `SHOW_SQL`

Recommended deployment approach:

- Render web service for Spring Boot app
- Render PostgreSQL database

---

## Future Improvements

- migrate frontend to Svelte + Tailwind
- expose a REST API backend
- add more advanced recommendation algorithms
- add watchlists and written reviews
- introduce pagination and analytics
- add CI/CD and Docker support

---

## Conclusion

The Movie Recommendation System is a strong portfolio-ready project that demonstrates full-stack Java development, secure user management, external API integration, recommendation logic, database design, testing, and deployment preparation.

It is suitable for portfolio presentation, academic documentation, and further extension into a larger platform.
