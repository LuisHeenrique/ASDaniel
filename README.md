# ASDaniel

**#API de Usuários e Tarefas

Esta é uma API RESTful construída com Spring Boot que gerencia Usuários e suas Tarefas, com relacionamento do tipo One-to-Many.**

Tecnologias utilizadas

Java 17+
Spring Boot
Spring Data JPA
H2 Database (memória)
Lombok
Postman (para testes)
Como executar o projeto

Método | Endpoint | Descrição
GET | /usuarios | Lista todos os usuários
GET | /usuarios/{id} | Busca usuário por ID
POST | /usuarios | Cria novo usuário
PUT | /usuarios/{id} | Atualiza usuário existente
DELETE | /usuarios/{id} | Deleta usuário

Método | Endpoint | Descrição

GET | /tarefas | Lista todas as tarefas
GET | /tarefas/{id} | Busca tarefa por ID
POST | /tarefas | Cria nova tarefa
PUT | /tarefas/{id} | Atualiza tarefa existente
DELETE | /tarefas/{id} | Deleta tarefa

Você pode testar os endpoints utilizando o Postman:

1 - Abra o Postman
2 - Crie requisições HTTP com base nos endpoints acima

