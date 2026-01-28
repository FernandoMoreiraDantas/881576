Music API — Spring Boot + Docker

API REST para gerenciamento de Artistas, Álbuns e Regionais, desenvolvida com Java 17 + Spring Boot, utilizando autenticação JWT, migração de banco com Flyway, armazenamento de arquivos no MinIO e ambiente totalmente containerizado com Docker.

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

Ao subir o projeto com Docker, são criados automaticamente:

Serviço	   Porta	    Função
music-api	8080	API Spring Boot
postgres	5432	Banco de dados PostgreSQL
minio	9000	Storage de imagens (S3-like)
minio-ui	9001	Console administrativo do MinIO
Como Executar com Docker
Pré-requisitos
Docker Desktop instalado e rodando
WSL2 habilitado (Windows)

 Passos
1) Clone o repositório
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio

2) Gere o JAR da aplicação

Windows (cmd):
mvnw.cmd clean package -DskipTests

Git Bash / Linux / Mac:
./mvnw clean package -DskipTests

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

3️ Testar os Endpoints

Com o token ativo, você pode usar:

/api/artistas

/api/albuns

/api/regionais
