# ---- Build: compila e empacota o JAR com o Maven Wrapper ----
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Dependências primeiro, para aproveitar o cache de camadas do Docker
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -q -B dependency:go-offline

COPY src/ src/
RUN ./mvnw -q -B package -DskipTests

# ---- Execução: apenas o JRE e o JAR ----
FROM eclipse-temurin:25-jre
WORKDIR /app

RUN groupadd --system spring && useradd --system --gid spring spring
COPY --from=build /app/target/*.jar app.jar
USER spring

# O Render define PORT; localmente o padrão é 8080
EXPOSE 8080
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseSerialGC -XX:TieredStopAtLevel=1"
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
