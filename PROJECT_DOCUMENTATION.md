# Movie Recommendation System
## Full Software Project Documentation

**Author:** [Your Name]  
**Institution / Organization:** [Optional]  
**Date:** March 28, 2026  
**Version:** 1.0

---

## Table of Contents

1. [Title Page](#title-page)
2. [Executive Summary](#executive-summary)
3. [Project Objectives](#project-objectives)
4. [System Overview](#system-overview)
5. [Features](#features)
6. [Functional Requirements](#functional-requirements)
7. [Non-Functional Requirements](#non-functional-requirements)
8. [System Architecture](#system-architecture)
9. [Technology Stack](#technology-stack)
10. [Database Design](#database-design)
11. [Recommendation Engine Design](#recommendation-engine-design)
12. [Security Design](#security-design)
13. [API / Route Overview](#api--route-overview)
14. [Testing Strategy](#testing-strategy)
15. [Deployment Preparation](#deployment-preparation)
16. [Challenges and Solutions](#challenges-and-solutions)
17. [Future Improvements](#future-improvements)
18. [Conclusion](#conclusion)
19. [Appendix](#appendix)

---

<!-- pagebreak -->

## Title Page

**Project Title:** Movie Recommendation System  
**Subtitle:** A Java Spring Boot Web Application for Movie Discovery, Ratings, Recommendations, and Administration  
**Author:** [Your Name]  
**Date:** March 28, 2026

---

## Executive Summary

The Movie Recommendation System is a full-stack Java Spring Boot web application designed to help users discover movies, rate them, and receive personalized recommendations. The system integrates with The Movie Database (TMDb) to import popular movie data into a local PostgreSQL database, where the application manages browsing, filtering, ratings, and recommendation logic.

The project solves a practical problem: movie discovery platforms often provide large catalogs but do not always give transparent, understandable recommendations. This system demonstrates how a structured, server-rendered web application can provide personalized results using a clear, rule-based recommendation engine.

The primary target users are:

- general users who want to browse and rate movies
- administrators who manage movie records locally
- reviewers, instructors, or recruiters evaluating backend and full-stack engineering skills

The project’s key value proposition is that it combines authentication, database-driven CRUD, external API integration, recommendation logic, and deployment readiness in one coherent system suitable for both educational and portfolio use.

---

## Project Objectives

### Main Goals

- Build a secure movie recommendation website using Java Spring Boot
- Allow users to register, log in, browse movies, and rate them
- Import real movie data from TMDb into a local PostgreSQL database
- Generate personalized recommendations from user rating behavior
- Provide an admin interface for managing movie records
- Prepare the system for local testing and initial cloud deployment

### MVP Goals

- User registration and login
- Role-based access with `USER` and `ADMIN`
- Local movie storage in PostgreSQL
- Browse, search, and filter movies
- Movie detail pages
- Rating system with update support
- Rule-based recommendation engine
- Admin CRUD for movies
- Basic automated testing
- Environment-variable-based configuration

### Non-Goals

The following were intentionally excluded from the MVP:

- collaborative filtering or machine learning recommendation models
- REST API backend for a separate frontend client
- SPA frontend architecture
- social features such as comments, follows, or friend activity
- watchlist, review essays, or moderation tools
- pagination and advanced analytics
- production-grade observability and CI/CD pipelines

---

## System Overview

The system is a server-rendered web application built with Spring Boot and Thymeleaf. Users authenticate through Spring Security, browse locally stored movie records, submit ratings, and receive personalized recommendations based on genre preferences inferred from high ratings.

Movie data is imported from TMDb through a secured integration flow. Imported records are persisted in PostgreSQL and reused for all browse and recommendation pages. Admin users can also create and edit movies manually through the web interface.

### High-Level Flow

1. A user registers or logs in.
2. The user browses movies stored in the local database.
3. The user searches by title or filters by genre.
4. The user opens a movie detail page and submits a rating.
5. The system stores or updates the rating and recalculates the movie average rating.
6. The recommendation engine analyzes highly rated movies to determine genre preference strength.
7. The system presents unrated movies from preferred genres as personalized recommendations.

### User Flow Summary

- Register account
- Log in using username or email
- Browse movies
- Search and filter movies
- View movie details
- Rate movies
- View personal ratings
- View recommendations

### Admin Flow Summary

- Log in with `ADMIN` role
- Access admin movie management
- Create new movie entries
- Edit existing movies
- Delete movies
- Trigger TMDb import flow

---

## Features

### Authentication

- User registration with username, email, and password
- Login using username or email
- Session-based authentication using Spring Security
- BCrypt password hashing
- Role-based access control

### Movie Import from TMDb

- TMDb API integration using Bearer token authentication
- Import of popular movies
- Import of movie genres
- Upsert logic using `tmdbId` to avoid duplicates

### Browse, Search, and Filter

- Movie list page backed by local PostgreSQL data
- Title-based search
- Genre-based filtering
- Combined browse flow with search and filter together

### Movie Detail Page

- Title
- Poster
- Description
- Genres
- Release date
- Average rating
- Popularity

### Ratings

- Authenticated users can rate movies from 1 to 5
- Existing ratings are updated instead of duplicated
- Average movie rating is recalculated after rating changes
- “My Ratings” page for logged-in users

### Recommendation Engine

- Uses movies rated `>= 4`
- Determines favorite genres from those ratings
- Excludes already-rated movies
- Ranks candidate movies using genre match, average rating, and popularity
- Supports cold-start fallback recommendations

### Admin Panel

- Admin-only movie CRUD
- Manual creation and editing of local movie records
- Genre assignment through Thymeleaf forms
- Deletion with confirmation flow

### Testing

- Service-level tests for registration, ratings, and recommendations
- Controller/web tests for registration, browsing, and admin access

### Deployment Readiness

- Environment-variable-based configuration
- Database and TMDb secrets externalized
- Suitable for first deployment to a platform such as Render

---

## Functional Requirements

1. The system shall allow users to register with a unique username and email.
2. The system shall securely hash user passwords before storing them.
3. The system shall allow users to log in using either username or email.
4. The system shall allow authenticated users to browse movies stored in PostgreSQL.
5. The system shall allow users to search movies by title.
6. The system shall allow users to filter movies by genre.
7. The system shall display a detailed movie page for a selected movie.
8. The system shall allow authenticated users to submit ratings from 1 to 5.
9. The system shall update an existing rating if the user has already rated the same movie.
10. The system shall prevent duplicate user-movie ratings.
11. The system shall compute and display updated average ratings for movies.
12. The system shall generate personalized recommendations for authenticated users.
13. The system shall provide fallback recommendations for users without enough rating history.
14. The system shall allow admin users to create, update, and delete movie records.
15. The system shall import popular movies and genres from TMDb.
16. The system shall avoid importing duplicate movies by checking `tmdbId`.

---

## Non-Functional Requirements

### Security

- Passwords must never be stored in plaintext.
- Admin routes must be restricted to users with `ADMIN` role.
- Secrets must be externalized through environment variables.

### Maintainability

- Controllers should remain thin.
- Business logic should remain in service classes.
- Persistence logic should remain in repository classes.

### Usability

- The UI should be clean, simple, and server-rendered.
- Navigation should reflect user role and authentication status.
- Empty states and common errors should be handled clearly.

### Performance

- Browse and detail pages should use local DB data only.
- Lazy-loading issues should be avoided in views by loading required associations in service/repository layers.

### Reliability

- Core user flows should be covered by basic automated tests.
- Error pages should provide graceful fallback for common failures.

### Portability

- The application should run locally and on a simple cloud platform with minimal changes.

---

## System Architecture

The application uses a layered monolithic architecture.

### Backend

- Spring MVC controllers manage request routing and page rendering
- Service classes contain business rules
- Repositories encapsulate data access via Spring Data JPA
- Security is configured centrally through Spring Security

### Frontend

- Thymeleaf renders server-side HTML pages
- Bootstrap provides lightweight styling and layout
- Shared fragments provide consistent navbar, footer, and flash message rendering

### Database

- PostgreSQL stores users, movies, genres, and ratings
- JPA entities map directly to relational tables
- Relationship mappings support ratings and movie genres

### External API Integration

- TMDb is used only for import, not for normal user browsing
- Integration code is isolated in a dedicated `integration.tmdb` package

### High-Level Component Interaction

[Insert architecture diagram here]

Text description:

- Controller receives request
- Controller delegates to service
- Service loads data from repositories
- Service may call TMDb client only for import-related flows
- Thymeleaf renders final response using model data

---

## Technology Stack

### Core Platform

- Java 17
- Spring Boot 3
- Maven

### Backend Frameworks

- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate

### Frontend

- Thymeleaf
- Thymeleaf Spring Security extras
- Bootstrap 5

### Database

- PostgreSQL

### External Integration

- TMDb API

### Testing Stack

- JUnit 5
- Spring Boot Test
- Mockito
- Spring Security Test
- MockMvc

---

## Database Design

### Entities

#### User

Represents an application user.

Key fields:

- `id`
- `username`
- `email`
- `passwordHash`
- `role`
- `createdAt`
- `updatedAt`

#### Movie

Represents a locally stored movie record.

Key fields:

- `id`
- `tmdbId`
- `title`
- `description`
- `releaseDate`
- `posterUrl`
- `averageRating`
- `popularity`
- `createdAt`
- `updatedAt`

#### Genre

Represents a movie genre shared across movies.

Key fields:

- `id`
- `name`

#### Rating

Represents a user’s rating for a movie.

Key fields:

- `id`
- `user`
- `movie`
- `ratingValue`
- `createdAt`
- `updatedAt`

### Table Responsibilities

- `users`
  Stores user identity, login data, and role
- `movies`
  Stores local movie catalog data
- `genres`
  Stores reusable genre values
- `ratings`
  Stores per-user movie ratings
- `movie_genres`
  Stores many-to-many relationships between movies and genres

### Relationships

- One `User` has many `Rating`
- One `Movie` has many `Rating`
- One `Rating` belongs to one `User`
- One `Rating` belongs to one `Movie`
- One `Movie` has many `Genre`
- One `Genre` can belong to many `Movie`

### Key Constraints

- unique `username`
- unique `email`
- unique `tmdbId`
- unique `(user_id, movie_id)` rating pair
- unique genre name

[Insert ERD here]

---

## Recommendation Engine Design

The recommendation engine is a rule-based MVP implementation designed for clarity and predictability.

### Recommendation Computation

1. Load the current user’s ratings.
2. Select movies rated `>= 4`.
3. Build a genre preference map from those highly rated movies.
4. Exclude movies already rated by the user.
5. Score candidate movies that match preferred genres.
6. Return the top-ranked recommendations.

### Favorite Genre Logic

Favorite genres are derived from movies the user rated highly. Each genre receives a weight based on the rating value of movies that contain that genre.

Example:

- Movie A rated 5 and tagged as Action
- Movie B rated 4 and tagged as Action + Sci-Fi

Resulting preference strength:

- Action = 9
- Sci-Fi = 4

This makes the genre profile more expressive than a simple genre count.

### Scoring Logic

Each candidate movie is scored using:

- genre preference strength as the primary factor
- movie average rating as a secondary quality signal
- popularity as a minor ranking boost

This keeps recommendations personalized while still favoring stronger local movie records.

### Cold-Start Fallback

If the user has:

- no ratings, or
- no ratings `>= 4`

the system returns fallback recommendations based on:

- highest average rating
- then popularity

This ensures the recommendations page remains useful for new users.

### Limitations of the Current MVP Logic

- no collaborative filtering
- no user-user similarity modeling
- no content-based vector similarity
- no watch history, favorites, or reviews
- recommendations remain relatively simple and transparent by design

---

## Security Design

### Authentication Flow

- Users register through a public registration form
- Passwords are hashed using BCrypt before persistence
- Users log in through Spring Security form login
- Login accepts either username or email
- Successful login creates a server-side session

### Authorization Roles

- `USER`
  Standard authenticated user
- `ADMIN`
  User with access to `/admin/**`

### Password Hashing

- BCrypt is used through Spring Security’s `PasswordEncoder`
- Plaintext passwords are never stored

### Route Protection

Public routes:

- `/`
- `/login`
- `/register`
- static resources under `/css/**`, `/js/**`, `/images/**`

Restricted routes:

- all application routes require authentication by default
- `/admin/**` requires `ADMIN`

### Secret Handling

Sensitive values are externalized via environment variables:

- database URL
- database username
- database password
- TMDb token

This reduces the risk of leaking credentials through source control.

---

## API / Route Overview

### Public Routes

- `GET /`
- `GET /login`
- `POST /login`
- `GET /register`
- `POST /register`

### User Routes

- `GET /movies`
- `GET /movies/{id}`
- `POST /movies/{movieId}/ratings`
- `GET /my-ratings`
- `GET /recommendations`

### Admin Routes

- `GET /admin/movies`
- `GET /admin/movies/new`
- `POST /admin/movies`
- `GET /admin/movies/{id}/edit`
- `POST /admin/movies/{id}`
- `POST /admin/movies/{id}/delete`

### TMDb Import Route

- `GET /admin/tmdb/import/popular?page=1`

### Recommendation Route

- `GET /recommendations`

---

## Testing Strategy

The testing strategy focuses on high-value MVP flows rather than broad, exhaustive coverage.

### What Was Tested

- registration service behavior
- registration web flow
- movie list and detail controller flow
- rating creation and update logic
- recommendation logic
- admin route restriction

### Unit / Service Tests

- password hashing and default role assignment
- duplicate username rejection
- rating create-or-update behavior
- average rating recalculation
- personalized recommendations from favorite genres
- cold-start recommendation fallback

### Controller / Web Tests

- public access to registration page
- successful registration redirect
- browse page requires authentication
- movie detail page rendering
- admin route restrictions for anonymous, user, and admin roles

### Manual Test Checklist

- register and log in
- browse movies after import
- search by title
- filter by genre
- open detail page
- submit a rating
- update a rating
- view “My Ratings”
- view recommendations
- test cold-start recommendations
- test admin CRUD
- test access denied flow
- test 404/error pages

---

## Deployment Preparation

The application is configured for environment-variable-based deployment and is suitable for a first deployment on a student-friendly platform.

### Required Environment Variables

- `SERVER_PORT`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `TMDB_READ_ACCESS_TOKEN`
- `JPA_DDL_AUTO`
- `SHOW_SQL`

### Local Run Instructions

```powershell
mvn spring-boot:run
```

Or:

```powershell
mvn clean package
java -jar target/movie-recommendation-0.0.1-SNAPSHOT.jar
```

### Test Commands

```powershell
mvn test
```

### Deployment Target Suggestion

A practical first deployment target is Render because it provides:

- simple Java web service deployment
- managed PostgreSQL
- beginner-friendly dashboard configuration

### Production Considerations

- rotate any secret that was previously exposed locally
- set `SHOW_SQL=false`
- keep secrets out of source control
- prefer environment variables over hardcoded configuration
- consider database migrations later for schema control

---

## Challenges and Solutions

### Database Permissions

During local setup, PostgreSQL access and database ownership needed explicit configuration. The solution was to standardize JDBC configuration and move credentials into environment variables.

### Thymeleaf and Spring Security Integration

Early template errors occurred when using incorrect Thymeleaf security expressions. The solution was to switch to the official Spring Security dialect with `sec:authorize`.

### Ambiguous `/error` Mapping

A custom `/error` controller conflicted with Spring Boot’s built-in `BasicErrorController`. The solution was to remove the custom route and rely on template-based error resolution using `templates/error.html` and status-specific templates.

### TMDb Configuration Issues

TMDb integration required correct Bearer token handling, DTO mapping, and image URL construction. The solution was to isolate TMDb config and client behavior in dedicated classes and externalize the token.

### Recommendation Logic Fixes

Initial recommendation logic allowed unrelated high-quality movies into the personalized list. The logic was corrected so only movies matching preferred genres are included in personalized results, while unrelated movies remain part of cold-start fallback only.

### Security and Access Control

Role-aware UI and admin route restrictions required careful coordination between Spring Security, Thymeleaf fragments, and controller routing. This was solved by keeping route protection in `SecurityConfig` and role-based rendering in Thymeleaf.

### Practical Debugging Lessons

- read the first meaningful stack trace line rather than guessing
- keep controllers thin so bugs localize more easily
- test critical service logic early
- use template fragments to reduce repeated UI errors
- isolate external API integration from user-facing pages

---

## Future Improvements

- Svelte + Tailwind frontend migration
- REST API backend refactor for frontend/backend separation
- improved recommendation algorithm using collaborative filtering or embeddings
- watchlist support
- review text and richer social movie interactions
- browse and admin pagination
- richer admin analytics dashboard
- database migrations with Flyway
- Docker support
- CI pipeline for build and test automation
- observability and structured logging

---

## Conclusion

The Movie Recommendation System demonstrates a complete MVP-scale full-stack software project built with Java Spring Boot. It integrates secure authentication, relational data management, external API import, user-driven ratings, recommendation logic, admin capabilities, testing, and deployment preparation in a cohesive architecture.

The project is intentionally practical rather than overengineered. It provides a strong foundation for academic submission, portfolio presentation, or future extension into a more advanced production-style platform.

---

## Appendix

### Sample Environment Variables

```env
SERVER_PORT=8080
DB_URL=jdbc:postgresql://localhost:5432/movie_rec
DB_USERNAME=movie_user
DB_PASSWORD=password
TMDB_READ_ACCESS_TOKEN=your_tmdb_read_access_token
JPA_DDL_AUTO=update
SHOW_SQL=true
```

### Sample Commands

Run application:

```powershell
mvn spring-boot:run
```

Run tests:

```powershell
mvn test
```

Build jar:

```powershell
mvn clean package
```

Import movies:

```text
http://localhost:8080/admin/tmdb/import/popular?page=1
```

Promote admin user:

```sql
UPDATE users
SET role = 'ADMIN'
WHERE username = 'your_username';
```

### Glossary

- **MVP**
  Minimum Viable Product
- **TMDb**
  The Movie Database, used as the external movie data source
- **JPA**
  Java Persistence API
- **CRUD**
  Create, Read, Update, Delete
- **Cold Start**
  A recommendation scenario where the user has little or no prior rating data

