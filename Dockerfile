FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/counterapi.jar counter.jar
ENTRYPOINT ["java","-jar","counter.jar"]