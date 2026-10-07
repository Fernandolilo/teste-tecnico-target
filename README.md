# Teste Técnico Target

API REST desenvolvida em **Java 17** e **Spring Boot** para resolução do teste técnico, contemplando regras de comissão de vendas, controle de estoque e cálculo de juros por atraso.

## Sobre o projeto

O projeto foi desenvolvido seguindo uma arquitetura em camadas, separando responsabilidades entre:

* Controllers
* Services
* Repositories
* Entities
* DTOs

O objetivo é manter as regras de negócio isoladas da camada de exposição da API e da persistência dos dados.

---

## Funcionalidades

### 1. Comissão de vendas

O sistema permite registrar vendas e calcular automaticamente a comissão do vendedor conforme o valor de cada venda.

Regras:

| Valor da venda                       | Comissão |
| ------------------------------------ | -------: |
| Abaixo de R$ 100,00                  |       0% |
| De R$ 100,00 até abaixo de R$ 500,00 |       1% |
| A partir de R$ 500,00                |       5% |

A comissão calculada é armazenada na venda, permitindo posteriormente consultar o total de comissão acumulado por vendedor.

### 2. Controle de estoque

O sistema permite realizar movimentações de estoque, incluindo:

* Entrada de produtos;
* Saída de produtos;
* Atualização da quantidade disponível;
* Validação de estoque suficiente para realizar uma saída;
* Retorno da quantidade final após a movimentação.

### 3. Pagamento e juros

O sistema permite registrar o pagamento de uma venda e calcular juros quando o pagamento ocorre após a data de vencimento.

A regra utilizada é:

**2,5% de juros por dia de atraso.**

Quando o pagamento ocorre no vencimento ou antes dele, nenhum juros é aplicado.

---

## Tecnologias utilizadas

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
* Maven
* JUnit

---

## Arquitetura

O projeto utiliza uma arquitetura em camadas:

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
Controller
    ↓
VendaRequest
    ↓
VendaService
    ↓
VendaRepository
    ↓
Venda
```

---

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

---

## Validações

O projeto utiliza **Bean Validation** para validar os dados recebidos pela API.

Entre as validações implementadas estão:

* Campos obrigatórios;
* Nome do vendedor;
* Valores monetários maiores que zero;
* Quantidades não negativas;
* Produtos obrigatórios;
* Datas obrigatórias;
* Status de pagamento.

---

## Exemplos de regras de negócio

### Comissão

Para uma venda de R$ 1.000,00:

```text
R$ 1.000,00 × 5% = R$ 50,00
```

Para uma venda de R$ 200,00:

```text
R$ 200,00 × 1% = R$ 2,00
```

Para uma venda de R$ 80,00:

```text
Sem comissão
```

### Juros

Venda:

```text
Valor: R$ 1.000,00
Vencimento: 01/10/2026
Pagamento: 06/10/2026
```

Dias de atraso:

```text
5 dias
```

Cálculo:

```text
R$ 1.000,00 × 2,5% × 5
```

Resultado:

```text
R$ 125,00 de juros
```

---

## Banco de dados

Durante o desenvolvimento foi utilizado o **H2 Database**, facilitando a execução e os testes da aplicação sem necessidade de configurar um banco de dados externo.

```text
http://localhost:8080/h2-console
```
---

## Documentação da API

A API possui documentação através do **OpenAPI/Swagger**.

Após iniciar a aplicação, a documentação pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

A porta pode variar conforme a configuração da aplicação.

---

## Como executar o projeto

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

---

## Executar os testes

Windows:

```bash
mvnw.cmd test
```

Linux/macOS:

```bash
./mvnw test
```

---

## Estrutura do projeto

```text
src
├── main
│   ├── java
│   │   └── com.target
│   │       ├── controllers
│   │       ├── entities
│   │       ├── repositories
│   │       └── services
│   │
│   └── resources
│       └── application.properties
│
└── test
    └── java
```

---

## Objetivo

Este projeto foi desenvolvido como parte de um **teste técnico para avaliação de conhecimentos em desenvolvimento backend com Java e Spring Boot**, buscando aplicar conceitos de:

* Orientação a objetos;
* APIs REST;
* Spring Boot;
* Persistência com JPA;
* DTOs;
* Validação;
* Regras de negócio;
* Controle de estoque;
* Cálculos financeiros;
* Organização de código.

---
```text para executar uma venda entrar no banco pegar ID de vendedor e ID de produto em estoque.```
'''
{
  "instante": "2026-10-07",
  "vendedor": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "dataVencimento": "2026-10-07",
  "produtos": [
    {
      "produtoId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "quantidade": 0
    }
  ]
}
'''

```text Para acesso do h2 com o docker passe a senha jdbc:h2:mem:testdb ```

para fazer teste da aplicação com o Docker, basta dar um docker compose up na raiz do pejeto.

```
services:
  teste-tecnico-target:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: teste-tecnico-target
    ports:
      - "8000:8000"
    restart: always
    volumes:
      - h2-data:/data
    networks:
      - services

volumes:
  h2-data:

networks:
  services:
    driver: bridge
```

## Autor

**Fernando da Silva**

GitHub:

https://github.com/Fernandolilo
