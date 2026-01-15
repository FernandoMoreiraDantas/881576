
Music API - Spring Boot
API REST para gerenciamento de Artistas, Álbuns e Regionais, desenvolvida com Java 17, Spring Boot e persistência de dados. Inclui segurança JWT com expiração de 5 minutos e documentação interativa via Swagger.

Tecnologias Utilizadas
Java 17 & Spring Boot 3
Spring Security + JWT (Autenticação e Autorização)
Spring Data JPA (Persistência)
H2 Database (Banco de dados em memória para testes)
Flyway (Migração de banco de dados)
SpringDoc OpenAPI (Swagger) (Documentação)

Como Executar o Projeto
Clone o repositório:
Bash
git clone https://github.com/seu-usuario/seu-repositorio.git
Importe no STS (Spring Tool Suite):
File -> Import -> Existing Maven Projects.
Execute a aplicação:
Clique com o botão direito na classe principal -> Run As -> Spring Boot App.
Acesse a API:
A API estará disponível em: http://localhost:8080
Instruções de Uso e Segurança
A API está protegida. Siga os passos abaixo para conseguir realizar requisições:

1. Obter Token de Acesso
Como o token expira a cada 5 minutos, você precisa primeiro se autenticar.
Endpoint: POST /api/auth/login
Corpo da Requisição (JSON):
JSON
{
  "login": "admin"
}
Resposta: Você receberá um campo token. Copie o valor gerado.

2. Autorizar no Swagger
Acesse: http://localhost:8080/swagger-ui/index.html

Clique no botão Authorize (ícone de cadeado no topo direito).
No campo de texto, cole o token copiado (não é necessário digitar "Bearer", o sistema já está configurado para isso).
Clique em Authorize e depois em Close.

3. Testar Endpoints
Agora, todos os endpoints de /api/artistas, /api/albuns e /api/regionais estarão liberados para uso enquanto o token for válido.