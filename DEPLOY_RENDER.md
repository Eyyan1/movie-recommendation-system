# Deploying to Render

This project is ready for a first deployment on Render using:

- one Render Web Service for the Spring Boot application
- one Render PostgreSQL database

## Required Environment Variables

Set these in the Render dashboard for the web service:

- `PORT`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `TMDB_READ_ACCESS_TOKEN`
- `JPA_DDL_AUTO`
- `SHOW_SQL`

Recommended values:

```env
PORT=10000
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

## Docker Deployment On Render

This project also supports Docker-based deployment on Render.

### Docker runtime behavior

- Render will build the image from the `Dockerfile`
- the Dockerfile performs a multi-stage Maven build
- the final image runs the packaged Spring Boot jar with:

```bash
java -jar app.jar
```

### Port behavior

The application is configured with:

```properties
server.port=${PORT:8080}
```

That means Render can inject its runtime `PORT` variable automatically, and the container will bind to the correct port.

### Docker build/start on Render

When using Docker on Render:

- Render uses the repository `Dockerfile`
- you do not need separate Maven build or Java start commands in Render
- the image build and runtime are controlled by the Dockerfile

### Required env vars for Docker deployment

- `PORT`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `TMDB_READ_ACCESS_TOKEN`
- `JPA_DDL_AUTO`
- `SHOW_SQL`

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
- The application now reads the runtime port from `PORT`, which is compatible with Render Docker deployment.
- Maven Wrapper support is included through:
  - `mvnw`
  - `mvnw.cmd`
  - `.mvn/wrapper/maven-wrapper.properties`
- `spring.thymeleaf.cache=false` will not break deployment, but you may want to make that configurable later for production.
- `TMDB_READ_ACCESS_TOKEN` is required if you want TMDb import to work after deployment.
