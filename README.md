# API MINI-AUTHORIZER - Vale Refeição

API  MiniAutorizador, desenvolvido em Java 17 com Spring-Boot.


## Características

- CRUD
- API RESTful
- Validation
- Enum
- MockMVC


## Requisitos

- Java JDK 17
- Apache Maven >= 3.8.6
- MySql 8
- Docker (Opcional)


## Tecnologias

- Java
- JPA
- Maven
- Spring
- Lombok
- Swagger
- H2
- JUnit
- SonarQube
- Docker


## Maven

Para rodar o projeto com Maven, é necessário ter a versão 3.8.6 instalada.
Além disso, é preciso ter o Java 17 e o MySql 8 instalado.


## Docker (Opcional)

Para rodar o projeto via Docker-Compose, basta executar o comando:

cd docker
docker-compose up



Aguarde carregar todo o serviço web. <br>
Após concluído, digite um dos endereços abaixo em seu navegador.

Listar cartões cadastrados: <br>
http://localhost:8080/cartoes

Listar transações cadastradas: <br>
http://localhost:8080/transacoes


## Swagger 

Documentação da API RESTful: <br>

http://localhost:8080/swagger-ui.html


## SonarQube

Para verificar a cobertura de testes:
http://localhost:9000

Depois efetue o login preenchendo "admin" no usuário e senha (login padrão).
Ao se logar crie um novo projeto e gere o token.


## Licença

Projeto licenciado The MIT License (MIT)


