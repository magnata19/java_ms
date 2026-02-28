FROM maven:3.9.12-eclipse-temurin-17 as builder

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-ea-5-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar ./app.jar

CMD ["java", "-jar", "app.jar"]