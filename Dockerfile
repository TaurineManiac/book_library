#FROM alpine   why not import linux? Because out jdk already a basics on some linux version and it is unreasonable to import one more linux.  
FROM eclipse-temurin:21-jre
	#what we import : version of it
WORKDIR /app
COPY /build/libs/book_library-0.0.1-SNAPSHOT.jar /app/library.jar
ENTRYPOINT ["java", "-jar", "library.jar"]
#Why we write in the end only filename without path? Because we already write later out current working directory and we dont need a path