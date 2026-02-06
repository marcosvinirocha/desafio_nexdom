# Desafio Nexdom

O **Desafio Nexdom** é uma aplicação de gerenciamento de estoque desenvolvida com Spring Boot. O objetivo do projeto é facilitar o controle de produtos e suas movimentações (entradas e saídas) de forma eficiente.

## 🚀 Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias:

- **Java 21**: Linguagem de programação moderna e performática.
- **Spring Boot 3/4**: Framework para desenvolvimento ágil de aplicações Java.
- **Spring Data JPA**: Abstração para persistência de dados.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes rápidos.
- **SpringDoc OpenAPI**: Documentação automática da API (Swagger).
- **Maven**: Gerenciamento de dependências e build.

## ✨ Funcionalidades

O sistema oferece as seguintes funcionalidades principais:

- **Gestão de Produtos**:
  - Cadastro, consulta e atualização de produtos.
  - Informações detalhadas como código, descrição, tipo, valor do fornecedor e quantidade em estoque.
- **Controle de Estoque**:
  - Registro de movimentações de estoque (entradas e saídas).
  - Atualização automática da quantidade disponível.

## 📦 Estrutura do Banco de Dados

### Entidades Principais

- **Produto**: Representa os itens do estoque.
  - `codigo`: Identificador único do produto.
  - `descricao`: Nome ou descrição do produto.
  - `tipo`: Categoria do produto (Enum `TipoProduto`).
  - `valorFornecedor`: Custo de aquisição.
  - `quantidadeEstoque`: Quantidade atual disponível.
- **MovimentoEstoque**: Registra as operações de entrada e saída.

## 🛠️ Como Executar o Projeto

### Pré-requisitos

- Java JDK 21 instalado.
- Maven instalado (ou utilize o wrapper `./mvnw` incluído no projeto).

### Passos para Execução

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/seu-usuario/desafio_nexdom.git
    cd desafio_nexdom
    ```

2.  **Compile e execute a aplicação:**
    Utilizando o Maven Wrapper (Linux/Mac):

    ```bash
    ./mvnw spring-boot:run
    ```

    Utilizando o Maven Wrapper (Windows):

    ```cmd
    ./mvnw.cmd spring-boot:run
    ```

3.  **Acesse a aplicação:**
    A aplicação iniciará na porta `8080` por padrão.

## 📚 Documentação da API (Swagger)

A documentação interativa da API está disponível via Swagger UI. Após iniciar a aplicação, acesse:

👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Aqui você poderá visualizar todos os endpoints disponíveis, testar requisições e ver os modelos de dados.

## 🗄️ Banco de Dados (H2 Console)

O projeto utiliza o banco de dados H2 em memória. Para acessar o console de gerenciamento:

1.  Acesse: **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**
2.  Preencha com as configurações (conforme `application.yml`):
    - **JDBC URL**: `jdbc:h2:mem:estoque-db`
    - **User Name**: `sa`
    - **Password**: (deixe em branco)
3.  Clique em **Connect**.

## 🧪 Testes

Para executar os testes automatizados do projeto:

```bash
./mvnw test
```
