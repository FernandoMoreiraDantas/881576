Candidato: N° Inscrição : 16358

Nome: FERNANDO MOREIRA DANTAS

CPF: 881.576.791-68

Music API — Spring Boot + Docker

Tecnologias Utilizadas

Java 17

Spring Boot 4

Spring Security + JWT (Autenticação e Autorização)

Spring Data JPA

PostgreSQL

Flyway (migração de banco)

MinIO (storage S3-like para imagens de álbuns)

SpringDoc OpenAPI (Swagger)

Docker & Docker Compose

Arquitetura do Ambiente (Docker)

 Passos
1) Clone o repositório
git clone https://github.com/FernandoMoreiraDantas/881576.git
cd seu-repositorio

2) Gere o JAR da aplicação

Windows (cmd):
mvnw.cmd clean package

Git Bash / Linux / Mac:
./mvnw clean package

3) Suba todo o ambiente
docker compose up --build

Acessos
Recurso	URL
API	http://localhost:8080

Swagger	http://localhost:8080/swagger-ui/index.html

MinIO Console	http://localhost:9001

Login MinIO:

admin
admin123

Parar o ambiente
docker compose down

Segurança e Uso da API

A API é protegida por JWT com expiração de 5 minutos.

Obter Token de Acesso

Endpoint

POST /api/auth/login


Body

{
  "login": "admin"
}


A resposta retornará:

{
  "token": "..."
}

Autorizar no Swagger

Acesse:

http://localhost:8080/swagger-ui/index.html


Clique em Authorize

Cole o token gerado

Clique em Authorize → Close

Testar os Endpoints

Com o token ativo, você pode usar:

/api/artistas

/api/albuns

/api/regionais

/api/v1/albuns/{id}/imagem

Recuperação de Imagem do Álbum (Link Pré-Assinado)

A API retornará uma URL semelhante a:

http://localhost:9000/capas/8c1504e0-...-capa.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&..

Health Checks

http://localhost:8080/actuator/health

http://localhost:8080/actuator/health/liveness

http://localhost:8080/actuator/health/readiness


