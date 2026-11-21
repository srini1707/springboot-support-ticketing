# Customer Support Ticketing Platform

    Support case lifecycle with priority, agent assignment, comments, requester queues, controlled transitions and audit timestamps.

    ## Technology
    - Java 21
    - Spring Boot 3.4.4
    - Maven
    - Spring Web, Bean Validation, Actuator
    - In-memory storage using Java collections (no database)

    ## Important database note
    This project has **no database dependency and no database configuration**. Data is held in application memory and is lost when the process/container restarts. This is intentional for a database-free DevOps lab.

    ## Run locally
    ```bash
    mvn clean verify
    mvn spring-boot:run
    ```
    Application port: `8085`

    Health check:
    ```bash
    curl http://localhost:8085/actuator/health
    ```

    ## Docker
    ```bash
    docker build -t springboot-support-ticketing:1.0 .
    docker run --rm -p 8085:8085 springboot-support-ticketing:1.0
    ```

    ## API endpoints
    ```text
    POST   /api/v1/tickets
GET    /api/v1/tickets/{id}
GET    /api/v1/tickets?requesterId=...
PATCH  /api/v1/tickets/{id}/assign?agentId=...
PATCH  /api/v1/tickets/{id}/status?target=IN_PROGRESS
POST   /api/v1/tickets/{id}/comments
GET    /api/v1/tickets/{id}/comments
    ```

    ## Example requests
    ```bash
    curl -X POST http://localhost:8085/api/v1/tickets -H 'Content-Type: application/json' -d '{"requesterId":"USER-100","subject":"Production login failure","description":"Users receive HTTP 500 after deployment","priority":"HIGH"}'
curl 'http://localhost:8085/api/v1/tickets?requesterId=USER-100'
curl -X PATCH 'http://localhost:8085/api/v1/tickets/1/assign?agentId=AGENT-20'
curl -X PATCH 'http://localhost:8085/api/v1/tickets/1/status?target=IN_PROGRESS'
    ```

    ## DevOps practice tasks
    1. Build the JAR with Maven and inspect the `target/` output.
    2. Build and run the Docker image; inspect logs and container health.
    3. Change the application port through an environment variable or JVM property.
    4. Add a GitHub Actions branch protection rule and inspect CI failures.
    5. Add structured logging and a correlation ID filter.
    6. Add API integration tests with MockMvc.
    7. Create Kubernetes Deployment and Service manifests; configure readiness and liveness probes.
    8. Add resource requests/limits and run a rolling update.
    9. Introduce a deliberate validation or business-rule bug and troubleshoot it.
    10. Document the impact of in-memory data loss during pod restart and propose a persistence strategy.

    ## Repository structure
    ```text
    src/main/java/   application, controller, service, records, exception handler
    src/main/resources/application.properties
    src/test/java/   service unit tests
    .github/workflows/ci.yml
    Dockerfile
    pom.xml
    ```
