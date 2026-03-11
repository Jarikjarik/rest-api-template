# REST API Template

Production-oriented Spring Boot REST API template for CRUD-style backends. The project keeps the domain intentionally small, but the surrounding architecture, validation, security, error handling and database setup are organized the way you would expect in a clean real-world service rather than in a classroom demo.

## Highlights

- Spring Boot 3 with layered architecture: controller, service, repository, config, mapper, dto
- REST-only API without MVC, Thymeleaf or server-side HTML
- Spring Security with HTTP Basic, stateless session policy and role-based access
- DTO validation with Jakarta Validation
- Global JSON error handling
- OpenAPI / Swagger UI
- Flyway migrations with deterministic schema and seed data
- PostgreSQL as the main database

## Stack

- Java 17
- Spring Boot 3.3
- Spring Web
- Spring Data JPA
- Spring Security
- Flyway
- PostgreSQL
- springdoc-openapi

## Architecture

- Controllers expose HTTP contracts and return API-friendly DTOs
- Services hold business logic and transactional boundaries
- Repositories are responsible only for persistence access
- Mappers isolate entity-to-DTO conversion
- Flyway owns schema creation and seed data
- Global exception handling keeps error responses consistent

## Project structure

```text
src/main/java/com/jarik/rest_api_template
|- config
|- controller
|- dto
|- exception
|- mapper
|- model
|- repository
\- service
```

## Local setup

### 1. Start PostgreSQL

```bash
docker compose up -d
```

### 2. Run the application

```bash
./mvnw spring-boot:run
```

Default local database settings:

- `jdbc:postgresql://localhost:5432/rest_api_template`
- `app_user / 12345`

Swagger UI:

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Default credentials:

- `admin / password`
- `user / password`

## Configuration

The application reads database settings from environment variables with sensible local defaults:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

Example:

```bash
DB_URL=jdbc:postgresql://localhost:5432/rest_api_template
DB_USERNAME=app_user
DB_PASSWORD=12345
```

## API overview

- `GET /api/auth/me` - current authenticated user
- `GET /api/admin/roles` - available roles, admin only
- `GET /api/admin/users` - list users, admin only
- `GET /api/admin/users/{id}` - get user by id, admin only
- `POST /api/admin/users` - create user, admin only
- `PUT /api/admin/users/{id}` - update user, admin only
- `DELETE /api/admin/users/{id}` - delete user, admin only

## Example request

```bash
curl -u admin:password http://localhost:8080/api/admin/users
```

```bash
curl -u admin:password \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Alex\",\"lastName\":\"Stone\",\"age\":29,\"email\":\"alex@example.com\",\"username\":\"alex\",\"password\":\"password123\",\"roleIds\":[1]}" \
  http://localhost:8080/api/admin/users
```

## Testing

```bash
./mvnw test
```

Tests run against H2 in PostgreSQL compatibility mode under the `test` profile, so the test suite stays self-contained and does not require a running PostgreSQL container.

## Notes

- PostgreSQL is the primary local development database
- The Docker container starts with `postgres / postgres` as the admin user and creates a dedicated application user `app_user / 12345`
- Flyway migrations make startup deterministic and remove manual schema drift
- HTTP Basic is used here as a minimal, transparent security baseline for a template project
- In a production system, authentication would typically move to JWT or OAuth2 and secrets would be externalized
