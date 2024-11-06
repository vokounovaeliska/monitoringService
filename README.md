# Endpoints Monitoring Service

This project is a simple application for monitoring URL addresses, built with Java and Spring Boot as a REST API. The application supports basic CRUD operations for managing monitored URLs, performs periodic availability checks, and records status codes.
## Features

- **CRUD Operations**: Create, update, delete, and list monitored URLs.
- **Background Monitoring**: URLs are periodically checked, logging status codes.
- **Background Monitoring**: Periodically checks URL availability based on user settings and logs status codes
- **Authorization**: Access to resources is secured via an `accessToken` provided in the HTTP headers.
## Data Model

### MonitoredEndpoint
- **id**: Unique identifier (long)
- **name**: URL description (String)
- **url**: The URL to be monitored (String)
- **createdAt**: Creation date (OffsetDateTime)
- **lastCheckedAt**: Date of the last check (OffsetDateTime)
- **monitoredInterval**: Time interval in seconds (Integer)
- **owner**: User

### MonitoringResult
- **id**: Unique identifier (long)
- **checkedAt**: Date of check (OffsetDateTime)
- **statusCode**: HTTP status code returned (Integer)
- **payload**: Currently empty due to known issues with excessively large data volumes being returned. (String)
- **monitoredEndpointId**: Associated MonitoredEndpoint

### User
- **id**: Unique identifier (Long)
- **username**: User's name (String)
- **email**: User's email (String)
- **accessToken**: Access token for authorization (String - uuid)

## Prerequisites

- **Java 21**
- **Maven**
- **MySQL**

## Setup and Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/vokounovaeliska/monitoringService.git
   ```

2. **Configure the database**:
    - Make sure MySQL is running and update the `application.properties` file in `src/main/resources` with your database credentials:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/monitoring_db
      spring.datasource.username=your-username
      spring.datasource.password=your-password
      spring.jpa.hibernate.ddl-auto=update
      ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

### Authentication
- Authorization is **NOT** implemented in the current version of the service.

### MonitoredEndpoint Endpoints

- **GET /monitoredEndpoints**: Retrieve a list of ALL monitored endpoints.
- **POST /monitoredEndpoints**: Create a new monitored endpoint.
- **PUT /monitoredEndpoints/{id}**: Update a monitored endpoint.
- **DELETE /monitoredEndpoints/{id}**: Delete a monitored endpoint.

### MonitoringResult Endpoints

- **GET /monitoringResults/endpoint/{id}**: Retrieve the last 10 monitoring results for a specific monitored endpoint.
- **GET /monitoringResults**: Retrieve a list of last 500 monitoring results.

## Model Validations
- **MonitoredEndpoint**:
    - `name`: Must not be blank.
    - `url`: Must be a valid URL.
    - `monitoredInterval`: Must be a positive integer.

## Example Users
The database can be pre-seeded with example users:
```json
[
  {
    "name": "Captain Jack",
    "email": "captain@example.com",
    "accessToken": "123e4567-e89b-12d3-a456-426614174000"
  }
]
```

## Troubleshooting
- If the application fails to start, ensure that MySQL is running and the database credentials are correct.

## Known Issues

- **Payload Handling**: The payload field is currently empty due to issues with handling excessively large data volumes. This is a known problem, and the solution is under investigation.
- **Authentication and Authorization**: The API is currently accessible by all users. The authentication mechanism (using `accessToken` in the header) has not been implemented, so access control is not in place yet. This means any user can access and modify the monitored endpoints without restrictions.
