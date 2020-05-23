FROM gradle:jdk11 AS builder
WORKDIR /workspace/app
COPY . /workspace/app
RUN ./gradlew build || return 0
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

FROM openjdk:11-jre-slim
VOLUME /tmp
ADD /build/libs/*.jar app.jar
CMD java $JVM_OPTS -jar app.jar
