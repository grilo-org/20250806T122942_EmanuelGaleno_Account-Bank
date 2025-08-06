# 🏦 Projeto Spring Boot: API Simples de Conta Bancária

Este projeto é uma API REST desenvolvida com **Java + Spring Boot**, com o objetivo de praticar e demonstrar **boas práticas de arquitetura e organização de código** em um sistema simples de **cadastro de contas bancárias**.

---

## 🎯 Objetivo

Criar uma aplicação backend enxuta e funcional, voltada para Java e Spring Boot, com foco em:

- Separação clara por camadas (Controller, Service, Repository)
- Uso de DTOs para transporte de dados
- Organização limpa do projeto
- Fundamentos do Spring Data JPA
- Integração com banco de dados real (PostgreSQL via Docker)

---

## ⚙️ Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL (via Docker)
- Docker + Docker Compose
- Maven

---

## 🧪 Funcionalidades

### Funcionalidades Implementadas

- **Criação de Usuários**: Permite cadastrar usuários com nome, sobrenome, e-mail e telefone (opcionais).
- **Criação de Contas Bancárias**: Cria contas vinculadas aos usuários com saldo inicial e tipo de conta (Corrente ou Poupança) através de um `enum`.
- **Depósito**: Realiza depósitos nas contas e retorna uma mensagem com o valor depositado e o saldo atualizado.
- **Saque**: Permite saques, validando o saldo disponível e retornando uma mensagem com o valor restante após o saque.
- **Verificação de Saldo**: Verifica o saldo da conta e retorna uma mensagem com o valor disponível.
- **Consultas de Conta**: Realiza consultas para buscar contas bancárias vinculadas a um usuário pelo nome e sobrenome.

### Endpoints

- **POST** `/accounts/new_user`: Criação de uma nova conta bancária com usuário.
- **POST** `/accounts/deposit`: Realiza um depósito em uma conta bancária.
- **POST** `/accounts/withdraw`: Realiza um saque de uma conta bancária.
- **GET** `/accounts/balance`: Consulta o saldo de uma conta bancária.
- **GET** `/accounts/user`: Busca um usuário específico por nome, sobrenome e ID.

---

## 🧪 Testes Implementados

Para garantir a qualidade, foram criados testes unitários focados na camada de serviço (`AccountService`), cobrindo cenários como:

- Criação de conta com usuário
- Depósito em conta bancária
- Saque em conta bancária
- Exceção ao tentar sacar valor maior que o saldo disponível
- Recuperação da mensagem de saldo da conta

Esses testes utilizam mocks para os repositórios, garantindo execução rápida e isolada do Spring.

---


## 🏁 Como Rodar o Projeto Localmente

### Pré-requisitos

1. Instalar o **Java 17+**.
2. Ter o **Maven** instalado.
3. Instalar o **Docker** e **Docker Compose** para rodar o PostgreSQL.

### Passos

Clone este repositório:
   ```
   git clone https://github.com/seu-usuario/springboot-conta-bancaria.git
   cd springboot-conta-bancaria
   ```
Importe o projeto em sua IDE (IntelliJ, VS Code, Eclipse).

Execute o projeto pela IDE ou pelo terminal:

```
./mvnw spring-boot:run
Acesse a aplicação em:
```
http://localhost:8080
🚀 Testando
Com a API rodando localmente, você pode testar as funcionalidades usando o Postman ou qualquer outra ferramenta para enviar requisições HTTP. Exemplos de como realizar as requisições estão abaixo.

Exemplo de Criação de Conta:

Método: POST

URL: http://localhost:8080/accounts/new_user

Corpo da requisição:json
```
{
  "firstName": "João",
  "lastName": "Silva",
  "accountType": "CORRENTE",
  "initialBalance": 1000.00,
  "phone": "1234567890",
  "email": "joao.silva@example.com"
}
```
Exemplo de Depósito:

Método: POST

URL: http://localhost:8080/accounts/deposit

Corpo da requisição:
json
```
{
  "accountId": 1,
  "amount": 500.00
}
```
✍️ Autor

Duell
Desenvolvedor Backend Java
📍 Palhoça / Florianópolis - SC

