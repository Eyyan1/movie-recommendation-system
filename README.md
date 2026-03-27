# Movie Recommendation System

A full-stack Java Spring Boot web application for discovering, rating, and managing movies with simple personalized recommendations.

This project uses a server-rendered MVC architecture with Thymeleaf, PostgreSQL, Spring Security, and TMDb integration. Users can register, browse imported movies, rate titles, and receive recommendations based on the genres they rate highly. Admin users can manage the movie catalog through a secure CRUD interface.

## Features

- User registration and login with Spring Security
- Login using username or email
- Password hashing with BCrypt
- TMDb movie import using Bearer token authentication
- Browse locally stored movies from PostgreSQL
- Search movies by title
- Filter movies by genre
- View detailed movie pages
- Rate movies from 1 to 5
- Update existing ratings without duplicates
- Personalized recommendations based on favorite genres
- Cold-start fallback recommendations for new users
- Admin-only movie management (create, edit, delete)
- Shared Thymeleaf layout with role-aware navigation
- Custom access denied and error pages
- Basic automated tests for core MVP flows
- Deployment-ready configuration using environment variables

## Tech Stack

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- Thymeleaf
- PostgreSQL
- Maven
- TMDb API
- Bootstrap 5
- JUnit 5
- Mockito

## Architecture Overview

This project follows a practical layered monolith architecture:

- `controller`
  Handles HTTP requests and Thymeleaf page rendering
- `service`
  Contains application and business logic
- `repository`
  Handles database access with Spring Data JPA
- `entity`
  Contains JPA domain models such as `User`, `Movie`, `Genre`, and `Rating`
- `integration.tmdb`
  Encapsulates TMDb API access and DTO mapping
- `templates`
  Server-rendered Thymeleaf views and shared fragments

Core modules:

- Authentication
- Movie browsing
- Ratings
- Recommendations
- TMDb import
- Admin movie management

## Project Structure

```text
src/main/java/com/example/movierec
  config/
  controller/
  dto/
  entity/
  integration/tmdb/
  repository/
  security/
  service/

src/main/resources
  templates/
  application.properties

src/test/java/com/example/movierec
  controller/
  service/
```

## Local Development Setup

### 1. Prerequisites

Install:

- Java 17+
- Maven 3.9+
- PostgreSQL 14+ or compatible

### 2. Create the database

Example:

```sql
CREATE DATABASE movie_rec;
CREATE USER movie_user WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE movie_rec TO movie_user;
```

### 3. Configure environment variables

This project supports environment-variable-based configuration for local and deployed environments.

Example `.env` values:

```env
SERVER_PORT=8080
DB_URL=jdbc:postgresql://localhost:5432/movie_rec
DB_USERNAME=movie_user
DB_PASSWORD=password
TMDB_READ_ACCESS_TOKEN=your_tmdb_read_access_token
JPA_DDL_AUTO=update
SHOW_SQL=true
```

If you do not use an `.env` loader, set them directly in your shell before running the app.

PowerShell example:

```powershell
$env:SERVER_PORT="8080"
$env:DB_URL="jdbc:postgresql://localhost:5432/movie_rec"
$env:DB_USERNAME="movie_user"
$env:DB_PASSWORD="password"
$env:TMDB_READ_ACCESS_TOKEN="your_tmdb_read_access_token"
$env:JPA_DDL_AUTO="update"
$env:SHOW_SQL="true"
```

## Running the Application

From the project root:

```powershell
mvn spring-boot:run
```

Or build a jar and run it:

```powershell
mvn clean package
java -jar target/movie-recommendation-0.0.1-SNAPSHOT.jar
```

Default local URL:

- [http://localhost:8080](http://localhost:8080)

## Running Tests

Run the full test suite:

```powershell
mvn test
```

Clean build plus tests:

```powershell
mvn clean test
```

Current automated tests cover:

- registration service logic
- registration controller flow
- movie browse/detail controller flow
- rating service create/update logic
- recommendation service logic
- admin route access restrictions

## Required Environment Variables

| Variable | Purpose | Example |
|---|---|---|
| `SERVER_PORT` | App port | `8080` |
| `DB_URL` | PostgreSQL JDBC URL | `jdbc:postgresql://localhost:5432/movie_rec` |
| `DB_USERNAME` | Database username | `movie_user` |
| `DB_PASSWORD` | Database password | `password` |
| `TMDB_READ_ACCESS_TOKEN` | TMDb API Read Access Token | `your_token_here` |
| `JPA_DDL_AUTO` | JPA schema mode | `update` |
| `SHOW_SQL` | SQL logging | `true` or `false` |

## TMDb Import

The application stores movie data locally in PostgreSQL and uses TMDb only for import.

### Import popular movies

1. Start the app
2. Log in
3. Trigger the import endpoint:

```text
GET /admin/tmdb/import/popular?page=1
```

Local example:

- [http://localhost:8080/admin/tmdb/import/popular?page=1](http://localhost:8080/admin/tmdb/import/popular?page=1)

The import flow:

- fetches genres from TMDb
- fetches popular movies from TMDb
- maps TMDb data into local `Movie` and `Genre` entities
- avoids duplicates using `tmdbId`

Imported fields:

- `tmdbId`
- `title`
- `description`
- `releaseDate`
- `posterUrl`
- `averageRating`
- `popularity`
- `genres`

## Creating an Admin User

Users register as `USER` by default.

To promote a local user to `ADMIN`, update the database directly:

```sql
UPDATE users
SET role = 'ADMIN'
WHERE username = 'your_username';
```

Or by email:

```sql
UPDATE users
SET role = 'ADMIN'
WHERE email = 'your_email@example.com';
```

After updating the role, log out and log back in.

## Main User Flows

### Standard User

- register an account
- log in with username or email
- browse imported movies
- search by title
- filter by genre
- view movie details
- rate movies
- view personal ratings
- receive recommendations

### Admin User

- access `/admin/movies`
- create movies manually
- edit existing movies
- delete movies
- continue using TMDb import as a separate catalog population flow

## Screenshots

Add screenshots here later for portfolio presentation.

Suggested screenshots:

- Home page
- Movie browse page
- Movie detail page
- Recommendations page
- My Ratings page
- Admin movie management page

Example placeholder layout:

```text
docs/screenshots/home.png
docs/screenshots/movies-list.png
docs/screenshots/movie-detail.png
docs/screenshots/recommendations.png
docs/screenshots/my-ratings.png
docs/screenshots/admin-movies.png
```

## Deployment (Render)

This project is suitable for a first deployment on Render with a managed PostgreSQL database.

### Recommended setup

- 1 Render Web Service for the Spring Boot app
- 1 Render PostgreSQL database

### Build and start commands

Build command:

```bash
mvn clean package
```

Start command:

```bash
java -jar target/movie-recommendation-0.0.1-SNAPSHOT.jar
```

### Environment variables on Render

Set these in the Render dashboard:

- `SERVER_PORT`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `TMDB_READ_ACCESS_TOKEN`
- `JPA_DDL_AUTO`
- `SHOW_SQL`

Recommended production-friendly values:

```env
SERVER_PORT=10000
JPA_DDL_AUTO=update
SHOW_SQL=false
```

Deployment notes:

- keep secrets out of source control
- use Render’s managed Postgres connection string
- use `SHOW_SQL=false` in deployment
- rotate any token that was previously committed locally

## Manual Pre-Deployment Checklist

Before deploying, verify these manually:

- user registration works
- login works with username
- login works with email
- movies import successfully from TMDb
- browse page loads with local database data
- search and genre filtering work
- detail page loads correctly
- users can rate and update ratings
- recommendations appear after rating movies highly
- cold-start recommendations work for a new user
- admin-only routes are blocked for non-admin users
- admin CRUD works
- custom error and access denied pages render correctly

## Future Improvements

- Add pagination to movie browse and admin pages
- Add database migrations with Flyway
- Add integration tests with Testcontainers
- Add user profile page
- Add poster upload fallback for manual admin entries
- Add recommendation explanation text per movie
- Improve scoring strategy and recommendation quality
- Add scheduled TMDb sync
- Add Docker support
- Add CI pipeline for tests and deployment

## Why This Project Matters

This project demonstrates:

- backend development with Spring Boot
- secure authentication and authorization
- relational data modeling with JPA
- external API integration
- server-rendered full-stack development with Thymeleaf
- recommendation logic based on real user activity
- admin tooling and role-based access control
- practical testing and deployment preparation

## License

Add a license here if you want to open-source the project.
