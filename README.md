## SortMyYoutube
Fampay Assignment
Note : To run the project directly skip to the Instructions section of this README

### Project Goal
To make an API to fetch latest videos sorted in reverse chronological order of the publishing date-time from YouTube for a given search query in paginated response

  

### Project Scope:
- Scheduled (async) calls to YouTube API continuously in background and storing data on our end
- Paginated response of the stored video details (Video title, description, publishing datetime, thumbnails URLs)
- Caching and conditional retrieval for reducing bandwidth and latency.
- Optimized Search API which also supports partial match for search query in either video title or description
- Dashboard to view the stored videos with options to search

### Proposed Solution :
- For async calls to Youtube API @Scheduled annotation marks a method to be scheduled 
- Processing of @Scheduled annotations is performed by registering a ScheduledAnnotationBeanPostProcessor
- The application is caching the most recent `ETag` associated with the queried resource if the resource has changed, the downstreap API call to youtube returns the modified resource and the `ETag` associated with that version of the resource. If the resource has not changed, the downstreap API call to youtube returns an HTTP 304 response (Not Modified), which indicates that the resource has not changed. This helps in reducing latency and bandwidth usage.
- For paginated response : A `Page<T>` instance is queried from mongo db, in addition to having the list of Videos, this also knows about the total number of available pages. It triggers an additional count query to achieve it.

- For optimized search TextIndex and SearchIndex are created through code on mongoDb atlas for fuzzy search  : relevant search results regardless of typos or spelling errors

- Swagger is used as an alternate to dashboard, the APIs documentation and execution can be easily observed.

### Improvement Scope : Cursor Based Pagenation
We can make use of a continuation token:
- On each request a `continuation_token` will be returned which will be a encrypted string representing the `<MD5 Hash of previous qury string>`_'<id of the last returned object>`_`<Sort field (here published_at)>`_`<Sort field value>`
Flow :
- On initial request a blank `continuation_token` will be passsed to the request parameter
- On retireval from DB a `continuation_token` will be cretated which will look like `a5b2111b666cd3bd5689effc1eda29b6_5ed6a45d0038622837ba60da_publishedAt_1590585400521` this value will be encrpted and sent back along with the response
- On the next request we will decrypt the token and use the values to form a mongo query like :
```
db.videos.find({
  $or: [
    { publishedAt: { $lt: 1590585400521} },
    { publishedt: 1590585400521,
      _id: { $lt: "5ed6a45d0038622837ba60da" }
    }
]})
.sort({ createdAt: -1, _id: -1 })
.limit(10);
```
Using this we can make use of Range queries which can use indexes to avoid scanning unwanted documents, typically yielding better performance as the offset grows compared to using cursor.skip() for pagination.


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
- Endpoint : /search?q=`<Search String>`
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
- Endpoint : /fetchVideos?pageNo=`<Page Number>`&pageSize=`<Page Size>`
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
   

