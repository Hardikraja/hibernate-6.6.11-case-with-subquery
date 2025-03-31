### Description
After upgrading the Spring Boot version from `3.3.5` to `3.4.4` in our project, we observed a significant increase in memory usage, rising from **300 MB to 2500 MB**. To investigate this, we created a **Proof of Concept (POC)** that reproduces the issue, helping to analyze and identify the root cause.

## Steps to Reproduce

### 1. Configure Database Properties
Update the database configuration in `application.properties` (currently set to **PostgreSQL** by default, but you can modify it if needed).

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
spring.datasource.username=<username>
spring.datasource.password=<password>
```

### 2. Review `pom.xml`

Check the **Spring Boot parent version** in `pom.xml`:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>

    <!-- Working with version 3.3.5 -->
    <version>3.3.5</version>

    <!-- Causes high memory usage in 3.4.4 -->
    <!-- <version>3.4.4</version> -->

    <relativePath/> <!-- Lookup parent from repository -->
</parent>
```

- The project is currently set to use **Spring Boot 3.3.5**.
- Run the application and monitor memory usage using **JProfiler** or **VisualVM**.
- Then, update the version to **3.4.4**, rerun the application, and observe the sharp increase in memory consumption.

### 3. Identify the Cause

The issue seems to be linked to a specific query in **`AppUserRepository.java`**:

- Locate the method **`findComplexAppUserByUserId()`** at the end of the class.
- This method contains a **CASE statement with a subquery**.
- **Comment out this query**, rerun the application, and observe the memory usage.
- You will notice that even with **Spring Boot 3.4.4**, the memory consumption returns to normal.

This suggests that the query structure might be triggering an internal issue in Spring Boot **3.4.4**, leading to excessive memory usage.

✅ **Note:** The same query method works **normally without any memory issues** in **Spring Boot 3.3.5**.
