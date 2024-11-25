# WatchVault Server
WatchVault Server is the backend API for the WatchVault application. Built using Java 17, Spring Boot, and Maven, it uses PostgreSQL as the database to store and manage user data efficiently. This server provides RESTful APIs for all CRUD operations and integrates seamlessly with the frontend application.

# Features
* CRUD Operations: Handle user data, movies, series, and other media efficiently.
* PostgreSQL Integration: Relational database for robust and scalable data storage.
* RESTful APIs: Clean and well-structured endpoints for frontend communication.
* Configurable: Easy-to-update configurations for database and application settings.

# Prerequisites
To run this project, you need:

* Java 17 (JDK 17 or later)
* Maven (for dependency management and build)
* PostgreSQL (for the database)
* Ensure the frontend client is running and accessible. See the client setup instructions here: [WatchVault Client](https://github.com/akballal/WatchVault_Server/blob/main/README.md)

# Getting Started
1. Clone the Repository
```bash
   git clone https://github.com/akballal/WatchVault_Server
   cd WatchVault_Server
   ```
2. Set Up the Database
   1. Install and run PostgreSQL.
   2. Create a new database for the application:
   ```bash
   CREATE DATABASE watchvault;
   ```
   3. Update the database credentials in application.properties or application.yml:
   properties
    ```bash
    # src/main/resources/application.properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/watchvault
    spring.datasource.username=<your_username>
    spring.datasource.password=<your_password>
    spring.jpa.hibernate.ddl-auto=update
    ```
3. Configure the JWT Secret Key:

    Set the JWT_SECRET_KEY as an environment variable. This key will be used for signing and validating JWT tokens:

    ```bash
    export JWT_SECRET_KEY=<your_secret_key>
    ```
    Alternatively, you can define it in a .env file or provide it directly in the application.properties file
    ```bash
    application.security.jwt.secret-key=${JWT_SECRET_KEY}
    ```

4. Build the Project
   Use Maven to build the project and resolve dependencies:
    ```bash
    mvn clean install
    ```
5. Run the Server
   Run the application using Maven:
    ```bash
    mvn spring-boot:run
    ```
    By default, the server will run on http://localhost:8080.

# Running the Server with Docker
## Using Docker Compose
The server is preconfigured to run using Docker Compose. Ensure Docker and Docker Compose are installed on your system.

1. Create a .env file

    Add the necessary environment variables in a .env file in the root directory:

    ```bash
    SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/watchvault
    SPRING_DATASOURCE_USERNAME=your_postgres_username
    SPRING_DATASOURCE_PASSWORD=your_postgres_password
    POSTGRES_DB=watchvault
    POSTGRES_USER=your_postgres_username
    POSTGRES_PASSWORD=your_postgres_password
    JWT_SECRET_KEY=your_secret_key
    ```

2. Run Docker Compose

    Build and start the application along with its PostgreSQL database:

    ```bash
    docker-compose up --build
    ```

3. Access the Server
   * The server will be available at http://localhost:8080.
   * PostgreSQL will be accessible at localhost:5433.

## Stopping the Containers
   To stop the running containers, use:

```bash
docker-compose down
```
This will stop all containers and free up resources.

# Contributing
We welcome contributions! To get started:

* Fork the repository.
* Clone your fork locally.
* Create a new branch for your feature/fix.
* Commit your changes with descriptive messages.
* Push to your fork and submit a pull request.

# Feedback
Have ideas or suggestions? Feel free to open an issue or reach out!

