# 📚 Library Management API

![Java](https://img.shields.io/badge/java-21-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![H2](https://img.shields.io/badge/h2-database-blue?style=for-the-badge)
![PostgreSQL](https://img.shields.io/badge/postgresql-future-%23336791.svg?style=for-the-badge&logo=postgresql&logoColor=white)

Uma API REST para gerenciamento de biblioteca desenvolvida com **Java 21 + Spring Boot**, permitindo o controle de livros, usuários e empréstimos.

---

## ✨ Funcionalidades

### 👤 Usuários
- Registro de usuários
- Login de usuário (sem JWT no momento)

### 📚 Livros
- Cadastro de livros
- Busca por ID
- Listagem de todos os livros
- Listagem de livros disponíveis
- Atualização de dados
- Remoção de livros

### 🔄 Empréstimos
- Criação de empréstimos
- Registro de devolução
- Listagem de empréstimos por usuário
- Filtro de empréstimos ativos
- Filtro de empréstimos atrasados

---

## 🛠️ Tecnologias

- Java 21
- Spring Boot
- H2 Database (dev)
- PostgreSQL (futuro)
- Spring Data JPA
- Lombok

---

## 🚀 Como Executar

### Pré-requisitos
- Java 21+
- Maven

### Passo a passo

```bash
git clone https://github.com/viniciusmarlin/library-management-api.git
cd library-management-api
mvn clean install
mvn spring-boot:run
```

A API estará disponível em:
http://localhost:8080

---

## 🧪 H2 Console

http://localhost:8080/h2-console

---

## 📌 Endpoints da API

### 📚 Books

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/books/create` | Cria um novo livro |
| `GET` | `/books/{id}` | Busca um livro por ID |
| `GET` | `/books/all` | Lista todos os livros |
| `GET` | `/books/available` | Lista livros disponíveis |
| `PUT` | `/books/update/{id}` | Atualiza um livro por ID |
| `DELETE` | `/books/delete/{id}` | Remove um livro por ID |

### 👤 Users

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/users/register` | Cadastra um novo usuário |
| `POST` | `/users/login` | Realiza login do usuário |

### 🔄 Loans

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/loans` | Cria um novo empréstimo |
| `PATCH` | `/loans/{loanId}/return` | Registra devolução de um empréstimo |
| `GET` | `/loans/user/{userId}` | Lista todos os empréstimos do usuário |
| `GET` | `/loans/user/{userId}/active` | Lista empréstimos ativos do usuário |
| `GET` | `/loans/user/{userId}/late` | Lista empréstimos atrasados do usuário |

---

## 📂 Estrutura

```
src/main/java/com/seuusuario/library/
├── controller/   # Endpoints da API (Books, Users, Loans)
├── service/      # Regras de negócio
├── repository/   # Acesso ao banco de dados
├── dto/          # Objetos de transferência de dados
├── model/        # Entidades do sistema
└── exception/    # Tratamento de exceções
```

---

## 📈 Roadmap

- Spring Security + JWT
- PostgreSQL (produção)
- Docker
- Swagger
- Testes

---

## 👨‍💻 Autor

- Feito por Vinicius Marlin 🚀
