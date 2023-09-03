HELMES TEST ASSIGNMENT
======================

Complete the assignment using Java/Spring Boot and any RDMS (PostgreSQL, MySQL, etc.)

Tasks:
------
1. Correct all the deficiencies in index.html
2. "Sectors" selectbox:
   * 2.1. Add all the entries from the "Sectors" selectbox to database
   * 2.2. Compose the "Sectors" selectbox using data from database
3. Perform the following activities after the "Save" button has been pressed:
   * 3.1. Validate all input data (all fields are mandatory)
   * 3.2. Store all input data to database (Name, Sectors, Agree to terms)
   * 3.3. Refill the form using stored data
   * 3.4. Allow the user to edit his/her own data during the session

Solution
--------
The assignment was completed as a Spring Boot Java application with a Thymeleaf HTML template implementing an MVC pattern.
PostgreSQL database is running inside a docker container that starts on application start-up.

You can open this project in your favorite IDE and run it as a Spring Boot application or use Gradle.
Please make sure that you have Docker on your machine (in the case of Windows PC the Docker Desktop should be running) 
and port <b>5432</b> is free to use for database connection.
After this the web page will be available on http://localhost:8080

<b>NB!</b> Some simple visual tweaks such as font, alignment, and adding of a dark theme were voluntarily implemented to make the web page more pleasant to look at (though it is, of course, a matter of taste).

Tools
-----
* Java
* Spring Boot
* PostgreSQL
* Flyway
* Thymeleaf
* HTML/CSS/JavaScript
* JUnit/Mockito/AssertJ
