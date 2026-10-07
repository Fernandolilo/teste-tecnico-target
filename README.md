# Teste Técnico Target

API REST desenvolvida em **Java 17** e **Spring Boot** para resolução do teste técnico, contemplando regras de comissão de vendas, controle de estoque e cálculo de juros por atraso.

## Sobre o projeto

O projeto utiliza uma arquitetura em camadas, separando as responsabilidades entre:

* Controllers
* Services
* Repositories
* Entities
* DTOs

O objetivo é manter as regras de negócio separadas da camada HTTP e da persistência dos dados.

## Funcionalidades

### 1. Comissão de vendas

O sistema calcula automaticamente a comissão do vendedor conforme o valor total da venda.

| Valor da venda                       | Comissão |
| ------------------------------------ | -------: |
| Abaixo de R$ 100,00                  |       0% |
| De R$ 100,00 até abaixo de R$ 500,00 |       1% |
| A partir de R$ 500,00                |       5% |

A comissão calculada é armazenada na venda e pode ser consultada de forma consolidada por vendedor.

### 2. Controle de estoque

O sistema permite:

* Cadastro de produtos;
* Entrada de produtos;
* Saída de produtos;
* Atualização da quantidade disponível;
* Validação de estoque suficiente;
* Retorno da quantidade final após a movimentação.

### 3. Pagamento e juros

O sistema permite registrar o pagamento de uma venda e calcular juros quando o pagamento ocorre após a data de vencimento.

A regra utilizada é:

**2,5% de juros por dia de atraso.**

Quando o pagamento ocorre no vencimento ou antes dele, não são aplicados juros.

Exemplo:

```text
Valor:       R$ 1.000,00
Vencimento:  01/10/2026
Pagamento:   06/10/2026

Dias de atraso: 5

R$ 1.000,00 × 2,5% × 5 = R$ 125,00
```

## Tecnologias

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* H2 Database
* Bean Validation
* Lombok
* ModelMapper
* Springdoc OpenAPI / Swagger
* JUnit
* Maven
* Docker
* Docker Compose

## Arquitetura

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Os DTOs são utilizados para separar os objetos de entrada e saída da API das entidades de persistência.

Exemplo:

```text
VendaController
    ↓
VendaRequest
    ↓
VendaService
    ↓
VendaRepository
    ↓
Venda
```

## Principais entidades

### Vendedor

Representa o vendedor responsável pelas vendas.

```text
Vendedor
 ├── id
 ├── nome
 └── vendas
```

### Venda

Representa uma venda realizada.

```text
Venda
 ├── id
 ├── data da venda
 ├── data de vencimento
 ├── data de pagamento
 ├── valor total
 ├── comissão
 ├── juros
 ├── status do pagamento
 ├── vendedor
 └── produtos
```

### Estoque

Representa os produtos disponíveis para movimentação.

```text
Estoque
 ├── id
 ├── código do produto
 ├── descrição
 ├── preço
 └── quantidade
```

## Validações

O projeto utiliza **Bean Validation** para validar os dados recebidos pela API.

Entre as validações implementadas:

* Campos obrigatórios;
* Nome do vendedor;
* Valores monetários maiores que zero;
* Quantidades válidas;
* Produtos obrigatórios;
* Datas obrigatórias;
* Status de pagamento.

As validações são tratadas por um `GlobalExceptionHandler`, retornando respostas HTTP padronizadas.

## Testes

Os testes automatizados estão focados principalmente nas **regras de negócio**, incluindo os cálculos de comissão e demais comportamentos relevantes da aplicação.

Para executar os testes:

### Windows

```bash
mvnw.cmd test
```

### Linux/macOS

```bash
./mvnw test
```

## Banco de dados

Durante o desenvolvimento é utilizado o **H2 Database**, permitindo executar a aplicação sem a necessidade de configurar um banco externo.

A aplicação utiliza:

```text
JDBC URL:
jdbc:h2:mem:testdb

Usuário:
sa
```

### H2 Console

Com a aplicação executando localmente:

```text
http://localhost:8000/api/target/h2-console
```

No campo **JDBC URL**, utilize:

```text
jdbc:h2:mem:testdb
```

Usuário:

```text
sa
```

Senha:

```text
deixe em branco
```

> Quando executada em Docker, a configuração do H2 segue o mesmo banco utilizado pela aplicação.

## Documentação da API

A API possui documentação utilizando **OpenAPI/Swagger**.

Com a aplicação em execução:

```text
http://localhost:8000/api/target/swagger-ui/index.html
```

A especificação OpenAPI pode ser acessada em:

```text
http://localhost:8000/api/target/v3/api-docs
```

## Executar localmente

### Pré-requisitos

* Java 17 ou superior
* Maven

Verifique a versão do Java:

```bash
java -version
```

### Clonar o projeto

```bash
git clone https://github.com/Fernandolilo/teste-tecnico-target.git
```

Entrar no projeto:

```bash
cd teste-tecnico-target
```

### Executar

No Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Ou utilizando Maven instalado:

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8000/api/target
```

## Executar com Docker

O projeto possui `Dockerfile` e `docker-compose.yml`.

Para executar utilizando Docker Compose:

```bash
docker compose up --build
```

Para executar em segundo plano:

```bash
docker compose up -d --build
```

Para visualizar os logs:

```bash
docker compose logs -f
```

Para parar os containers:

```bash
docker compose down
```

A API estará disponível em:

```text
http://localhost:8000/api/target
```

O container utiliza a porta `8000`.

## Exemplo de criação de venda

Antes de criar uma venda, é necessário possuir um vendedor e um produto cadastrados.

Exemplo:

```json
{
  "instante": "2026-10-07",
  "vendedor": "ID_DO_VENDEDOR",
  "dataVencimento": "2026-10-07",
  "produtos": [
    {
      "produtoId": "ID_DO_PRODUTO",
      "quantidade": 1
    }
  ]
}
```

Os valores `ID_DO_VENDEDOR` e `ID_DO_PRODUTO` devem ser substituídos pelos UUIDs retornados pelos respectivos cadastros.

## Estrutura do projeto

```text
src
├── main
│   ├── java
│   │   └── com.target
│   │       ├── controllers
│   │       ├── entities
│   │       ├── exceptions
│   │       ├── repositories
│   │       └── service
│   │
│   └── resources
│       ├── application.yml
│       └── application-test.yml
│
└── test
    └── java
```

## Objetivo

Este projeto foi desenvolvido como parte de um teste técnico para avaliação de conhecimentos em desenvolvimento backend com Java e Spring Boot, aplicando conceitos de:

* Orientação a objetos;
* APIs REST;
* Spring Boot;
* Persistência com JPA;
* DTOs;
* Bean Validation;
* Tratamento de exceções;
* Regras de negócio;
* Controle de estoque;
* Cálculos financeiros;
* Testes unitários;
* Docker;
* Organização de código.

## Autor

**Fernando da Silva**

GitHub: [Fernandolilo](https://github.com/Fernandolilo)
