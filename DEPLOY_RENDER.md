# Deploying to Render

This project is ready for a first deployment on Render using:

- one Render Web Service for the Spring Boot application
- one Render PostgreSQL database

## Required Environment Variables

Set these in the Render dashboard for the web service:

- `SERVER_PORT`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `TMDB_READ_ACCESS_TOKEN`
- `JPA_DDL_AUTO`
- `SHOW_SQL`

Recommended values:

```env
SERVER_PORT=10000
DB_URL=jdbc:postgresql://your-render-postgres-host:5432/your_database
DB_USERNAME=your_database_user
DB_PASSWORD=your_database_password
TMDB_READ_ACCESS_TOKEN=your_tmdb_read_access_token
JPA_DDL_AUTO=update
SHOW_SQL=false
```

## Build Command

Use Maven Wrapper so Render builds with the project-pinned Maven version:

```bash
./mvnw clean package -DskipTests
```

## Start Command

```bash
java -jar target/movie-recommendation-0.0.1-SNAPSHOT.jar
```

## Render Setup Steps

1. Push the project to GitHub.
2. Create a new PostgreSQL database on Render.
3. Create a new Web Service connected to your GitHub repository.
4. Set the build command:

```bash
./mvnw clean package -DskipTests
```

5. Set the start command:

```bash
java -jar target/movie-recommendation-0.0.1-SNAPSHOT.jar
```

6. Set the required environment variables in the Render dashboard.
7. Deploy the service.

## First-Run Checklist

After the first successful deploy:

- confirm the application starts without datasource errors
- confirm the database connection works
- open the home page
- register a test user
- log in successfully
- promote one test user to `ADMIN` in the database if needed
- open `/admin/movies` as admin
- trigger TMDb import with:
  `/admin/tmdb/import/popular?page=1`
- confirm movies appear in `/movies`
- confirm rating and recommendation flows work

## Notes

- The application already reads Render-friendly environment variables from `application.properties`.
- Maven Wrapper support is included through:
  - `mvnw`
  - `mvnw.cmd`
  - `.mvn/wrapper/maven-wrapper.properties`
- `spring.thymeleaf.cache=false` will not break deployment, but you may want to make that configurable later for production.
- `TMDB_READ_ACCESS_TOKEN` is required if you want TMDb import to work after deployment.
