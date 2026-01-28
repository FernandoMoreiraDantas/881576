# Imagem base Java 17 leve
FROM eclipse-temurin:17-jdk-alpine

# Pasta da aplicação dentro do container
WORKDIR /app

# Copia o JAR gerado pelo Maven
COPY target/*.jar app.jar

# Porta da aplicação
EXPOSE 8080

# Comando para iniciar a API
ENTRYPOINT ["java","-jar","app.jar"]