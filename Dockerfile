FROM openjdk:8
EXPOSE 8080
ADD target/sortytsearch.jar sortytsearch.jar
ENTRYPOINT ["java","-jar","sortytsearch.jar"]