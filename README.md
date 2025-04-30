# Treatment Task Scheduler

This is a Spring Boot application that manages and schedules treatment tasks for patients based on configurable
recurrence patterns. It supports both daily and weekly schedules and generates tasks just-in-time.

## Features

- **Human-readable recurrence patterns**  
  Define and manage treatment plans using simple strings like:
    - `every day at 08:00 and 18:00`
    - `every Monday at 09:00`

- **Extendable recurrence pattern parser**
    - Pattern parsing is handled by implementations of the `RecurrencePatternParser` interface, allowing easy extension
      for new recurrence patterns.

- **Task generation**
    - Automatically generates and persists treatment tasks for all active plans.

- **One-time activation model**
    - Once tasks are created, the corresponding plans are marked as inactive to avoid duplication.

- **Lightweight, custom scheduler**
    - Uses a custom in-app scheduler built on `ScheduledExecutorService`. No external tools like Quartz are required.

- **Built-in seed data and console visualization**
    - Predefined plans are automatically seeded, and created tasks are displayed in the console for easy validation.

- **Time zone-friendly design**
    - Recurrence patterns use `ZonedDateTime` and respect the system's default time zone.
    - All date/time values are stored as `Instant` in the database to ensure consistency and avoid time zone issues.
    - This separation of **display logic** (`ZonedDateTime`) and **storage format** (`Instant`) avoids common pitfalls
      with daylight saving transitions and internationalization.

## Technologies

- Java 17+
- Spring Boot
- Spring Data JPA (H2/PostgreSQL)
- Lombok
- JUnit, Mockito
- SLF4J for logging

## Getting Started

To easily visualize entity relationships and data, a docker-compose.yml file is provided to spin up a PostgreSQL
instance. To start it, simply run:
`docker compose up -d`
Alternatively, you can use the in-memory H2 database. To switch between PostgreSQL and H2, just comment or uncomment the
corresponding properties in the application.properties file.

### Prerequisites

- Java 17+
- Maven or Gradle

### Build and Run

```bash
./mvnw clean install
./mvnw spring-boot:run
