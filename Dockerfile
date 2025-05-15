# 1. Build stage
FROM eclipse-temurin:17-jdk-alpine AS builder

ARG GITHUB_TOKEN

WORKDIR /app
COPY . .

# Maven settings mit GitHub Token für private Packages
RUN mkdir -p ~/.m2 && \
    echo "<settings>
      <servers>
        <server>
          <id>github-other</id>
          <username>jodegen</username>
          <password>${GITHUB_TOKEN}</password>
        </server>
      </servers>
    </settings>" > ~/.m2/settings.xml

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# 2. Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]