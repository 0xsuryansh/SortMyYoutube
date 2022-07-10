## SortMyYoutube
Fampay Assignment

### Project Goal
To make an API to fetch latest videos sorted in reverse chronological order of the publishing date-time from YouTube for a given search query in paginated response

  

### Project Scope:
- Scheduled (async) calls to YouTube API continuously in background and storing data on our end
- Paginated response of the stored video details (Video title, description, publishing datetime, thumbnails URLs)
- Optimized Search API which also supports partial match for search query in either video title or description
- Dashboard to view the stored videos with options to search

### Tech Stack
- **Java 8**
- **Spring Boot 2.7.1**
- **Mongo DB**
- **Docker**

### Instructions
1. Clone the project
`git clone https://github.com/suryanshsh/SortMyYoutube.git`
2. For MongoDB : run command
```docker pull mongo:latest```
4. Run ```mvn clean install``` to build ***.jar*** of the code repository 
5. *.env file* :

```
			YT_ACCESS_KEYS  : API access keys comma (,) saparated
			MONGO_DB_HOST : 
			MONGO_DB_PORT :
			SEARCH_QUERY :

```
6. Run `docker build -t springboot-sortytsearch:1.0 .` to build docker image from Dockerfile
7. Run  `docker compose -f docker-compose.yml up` to start and link Spring and Mongo instances


## Dashboard 
Link to Swagger dashboard : http://localhost:8080/swagger-ui.html
![alt txt](Screenshot 2022-07-10 at 3.56.49 PM.png

## APIs
