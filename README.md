# Price Management Service API

This repository contains the source code for a Price Management Service API built using Spring Boot. This service provides endpoints for managing prices, including creating, retrieving, updating, and deleting price information.

## Requirements

To build and run this application, you will need the following:

*   **Java 17 SDK:** Make sure you have Java 17 or higher installed on your system. You can download it from [https://adoptium.net/](https://adoptium.net/).
*   **Maven:** This project uses Maven for dependency management. Download and install Maven from [https://maven.apache.org/](https://maven.apache.org/).

## Design Patterns and Clean Code Principles

This project adheres to several design patterns and clean code principles to ensure maintainability, scalability, and readability:

*   **Separation of Concerns:** The application follows the principle of separation of concerns, dividing responsibilities into distinct modules.
*   **Layered Architecture:** The project is structured using a layered architecture:
    *   **Controller Layer:** Handles incoming requests and interacts with the service layer.
    *   **Service Layer:** Contains the business logic and interacts with the persistence layer.
    *   **Persistence Layer:**  Handles database interactions.
*   **Global Exception Handler:** A global exception handler is implemented to gracefully handle exceptions and provide consistent error responses.  This centralizes error handling and improves the user experience.

## Database Structure and Migrations

The database schema is designed to store price information, including GROUPCHAIN, PRICE_RATE and OFFER tables.

Flyway is used for database migrations.  Migration scripts are located in the `src/main/resources/db/migration` directory. Flyway helps in managing database schema changes and ensures smooth deployments across different environments.  It automatically applies migrations in the correct order and keeps track of the database version.


>**Note on Database Representation:**
While the underlying database (H2) includes tables for `GROUPCHAIN`, `PRICE_RATE`, and `OFFER`, this application currently only provides a representation of the `OFFER` entity for simplicity.  The `GROUPCHAIN` and `PRICE_RATE` entities, along with their relationships to the `OFFER` entity, are not yet fully implemented in the application's code.
However, it's important to note that the database schema is designed to accommodate these entities and their relationships.  Implementing the corresponding JPA entities and defining the relationships (e.g., using `@ManyToOne` for `OFFER` to `GROUPCHAIN` and `PRICE_RATE`, and `@OneToMany` if needed) would be straightforward.
This simplified representation was chosen for the initial iteration of the application to focus on core `OFFER` management functionality.  Implementing the full data model, including `GROUPCHAIN` and `PRICE_RATE`, is planned for future iterations and can be easily achieved by creating the necessary JPA entities and defining their relationships as needed.


## Docker Support

This project supports Docker, allowing you to easily containerize and deploy the application.  A `Dockerfile` is included in the project root directory, defining the steps to build the Docker image.

**Building the Docker Image:**

1.  Navigate to the project root directory in your terminal.
2.  Run the following command to build the Docker image:

    ```bash
    docker build -t price-management-service .
    ```

    This command will build the image and tag it as `price-management-service`. The `.` at the end of the command specifies the build context (the current directory).

**Running the Docker Container:**

To run the Docker container and expose the application on the default port 8080, use the following command:

```bash
docker run -p 8080:8080 price-management-service
```

## Running the Server

1.  Clone the repository: `git clone https://github.com/cgsuarez/price-management-service`
2.  Navigate to the project directory: `cd price-management-service`
3.  Build the application: `mvn clean install`
4.  Run the application: `mvn spring-boot:run`

The application will start on the default port (8080). You can configure the port in the `application.yml` file.

## Accessing API Documentation (Swagger 3)

Once the server is running, you can access the API documentation using Swagger 3.  Navigate to the following URL in your browser:

`http://localhost:8080/swagger-ui.html`

This will open the Swagger UI, where you can explore the available endpoints, their request/response formats, and test them directly.

## Running Unit and Integration Tests

The project includes unit and integration tests to ensure code quality and functionality.  To execute the tests, run the following command:

`mvn test`

This will run all the tests and generate a test report.


## Continuous Integration with GitHub Actions

This project utilizes GitHub Actions for Continuous Integration (CI).  A workflow file (`.github/workflows/CI_buildAndPublish.yml`) is included in the repository, defining the CI pipeline.

The CI workflow performs the following steps:

1.  **Build:** Builds the Spring Boot application using Maven.
2.  **Test:** Runs unit and integration tests.
3.  **Docker Image Build and Push:** Builds a Docker image of the application and pushes it to Docker Hub.

**GitHub Secrets:**

To enable the Docker image build and push to Docker Hub, you need to configure the following environment variables as secrets in your GitHub repository's settings:

*   `DOCKER_USERNAME`: Your Docker Hub username.
*   `DOCKER_PASSWORD`: Your Docker Hub password or a personal access token with write permissions to your repository.

**Future Continuous Deployment (CD) Considerations:**

In future iterations, a Continuous Deployment (CD) pipeline could be added to automate the deployment of the application to a cloud environment.  One approach would be to use Terraform to provision an Amazon ECS (Elastic Container Service) cluster on AWS and then deploy the Docker image as a task within that cluster.

The CD pipeline could be triggered by events such as a push to the main branch or a new release tag.  Terraform templates would define the infrastructure as code, allowing for reproducible and consistent deployments.  This would enable a fully automated CI/CD process, from code changes to deployment in the cloud.  This is currently outside of the scope of this iteration but is a planned enhancement.

---

**Author:** Christian Suarez
