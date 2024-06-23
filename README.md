### LetMeShip - URL analyser

Functions:
1. List all links that showed in the entered url
2. List all searching history

Public APIs:
    Page APIs:
- POST /page/analyse 
- GET  /page/history

Internal APIs: Web Filter APIs:
- GET /internal/filter/rate-limiting/status
- POST /internal/filter/rate-limiting/enable
- POST /internal/filter/rate-limiting/disable
        
Features:
1. Docker containerlized application
2. Improved security by adding a rate limit web filter to limit the maxmium request count in a certain period
3. Global exception handler in backend
4. Support pageable feature in both UI and backend
5. URL validation
6. Added several test cases
7. Support AWS RDS

How to test:
1. Run docker-compose up --build from the root path of the project
2. Access UI via http://localhost:4200
3. Send request to backend to http://localhost:8080
4. Verify from ui response and from H2 console at http://localhost:8080/h2-console ("sa" as username and no password)

Tech stack:
1. Java 17
2. Spring Boot 3.3
3. MySQL/H2
4. Angular 14
5. Docker
