### LetMeShip - URL analyser

#### Functions:
1. List All Links: Extract and display all links from the provided URL's webpage.
2. Search History: View a list of all past URL analysis requests.

#### APIs:
Page APIs:
- POST /page/analyse : Analyse a webpage for finding all links
- GET  /page/history : Retrieve search history

Web Filter APIs:
- GET /internal/filter/rate-limiting/status 
- POST /internal/filter/rate-limiting/enable 
- POST /internal/filter/rate-limiting/disable
        
Auth APIs:
- POST /auth/login
- GET  /auth/checkIfEmailExists
- POST /auth/refresh-token

#### Features:
1. Docker containerized application
2. Enhanced security by adding a rate limit web filter
4. Pagination support
5. URL validation
6. JWT Support

#### How to test via docker:
1. Run docker-compose up --build from the root path of the project
2. Access UI at http://localhost:4200 (Test user: user, Password: 1234)
3. Send requests to backend to http://localhost:8080
4. Verify from ui response and from H2 console at http://localhost:8080/h2-console (Username: sa, Password: none)

#### Tech stack:
1. Java 17
2. Spring Boot
3. Spring Security
4. MySQL/H2
5. Angular
6. Docker
