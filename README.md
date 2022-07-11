## SortMyYoutube
Fampay Assignment

### Project Goal
To make an API to fetch latest videos sorted in reverse chronological order of the publishing date-time from YouTube for a given search query in paginated response

  

### Project Scope:
- Scheduled (async) calls to YouTube API continuously in background and storing data on our end
- Paginated response of the stored video details (Video title, description, publishing datetime, thumbnails URLs)
- Caching and conditional retrieval – The application is caching the most recent `ETag` associated with the queried resource if the resource has changed, the downstreap API call to youtube returns the modified resource and the `ETag` associated with that version of the resource. If the resource has not changed, the downstreap API call to youtube returns an HTTP 304 response (Not Modified), which indicates that the resource has not changed. This helps in reducing latency and bandwidth usage.
- Optimized Search API which also supports partial match for search query in either video title or description
- Dashboard to view the stored videos with options to search

### Proposed Solution :
- For async calls to Youtube API @Scheduled annotation marks a method to be scheduled 
- Processing of @Scheduled annotations is performed by registering a ScheduledAnnotationBeanPostProcessor

- For paginated response : A `Page<T>` instance is queried from mongo db, in addition to having the list of Videos, this also knows about the total number of available pages. It triggers an additional count query to achieve it.

- For optimized search TextIndex and SearchIndex are created through code on mongoDb atlas for fuzzy search  : relevant search results regardless of typos or spelling errors

- Swagger is used as an alternate to dashboard, the APIs documentation and execution can be easily observed.


### Tech Stack
- **Java 8**
- **Spring Boot 2.7.1**
- **Mongo DB**
- **Docker**

### Instructions
1. Clone the project
`git clone https://github.com/suryanshsh/SortMyYoutube.git`
2. For MongoDB : run command
`docker pull mongo:latest`

4. Run ```./mvnw clean install -DskipTests``` to build ***.jar*** of the code repository 
5. *.env file* :

```
			YT_ACCESS_KEYS  : <API access keys comma (,) saparated>
			MONGO_DB_HOST : < MongoDb container name>
			MONGO_DB_PORT : 2701
			SEARCH_QUERY : <I chose CRICKET, feel free to choose whatever you like! :)>

```
6. Run `docker build -t springboot-sortytsearch:1.0 .` to build docker image from Dockerfile
7. Run  `docker compose -f docker-compose.yml up` to start and link Spring and Mongo instances


## Dashboard 
Link to Swagger dashboard : http://localhost:8080/swagger-ui.html

## APIs
  	
### Search API
- Endpoint : /search?q=<Search String>
- Method : GET
- Description : Optimized Search API which also supports partial match for search query in either video title or description
   ```
   Reponse :
   video_id
   videoTitle
   description
   publishedDateTime
   thumbnailUrl
   channelId
   channelTitle
   ```
   
### Fetch API (Paginate)
- Endpoint : /fetchVideos?pageNo=<Page Number>&pageSize=<Page Size>
- Method : GET
- Description : Paginated response of the stored video details 
   ```
   Reponse :
   video_id
   videoTitle
   description
   publishedDateTime
   thumbnailUrl
   channelId
   channelTitle
   ```
   
  
### Fetch Videos from YT and Store
   - Endpoint : /video
   - Method : GET
   - Description : Explicit API for calling the youtube API and saving data to db
   This API can be hit by an external CRON job on AWS Lambda for removing dependency on @Scheduled background task which can lead to same API calls.
   

