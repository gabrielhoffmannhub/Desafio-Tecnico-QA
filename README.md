## Projeto de Testes Automatizados da API Books

Este projeto contém testes automatizados para validar a API Books, utilizando Java 17, Maven, JUnit 5 e REST Assured.
O foco é demonstrar boas práticas de automação, organização por camadas e criação de cenários positivos e negativos.

## Tecnologias Utilizadas

- Java 17

- REST Assured (requisições HTTP e validação de respostas)

- JUnit 5 (framework de testes)

- Maven (gerenciamento de dependências)

- Jackson (serialização JSON)

- IntelliJ IDEA (IDE utilizada no desenvolvimento)

## Estrutura do Projeto
```
src
 └── test
     └── java
         ├── base
         │    └── BaseTest.java
         ├── factory
         │    └── BookFactory.java
         ├── model
         │    └── Book.java
         └── tests
              └── BooksCrudTests.java

```

## Descrição das Pastas

### base/BaseTest.java
Contém:

- Configuração da URL base

- Configuração do REST Assured

- Headers comuns

- BeforeAll/AfterAll

### factory/BookFactory.java
Responsável por criar objetos de livro prontos para uso em testes.
Evita duplicação de código e deixa os cenários mais limpos.

### model/Book.java
Representa o payload do livro.

### tests/BooksCrudTests.java
Contém todos os testes automatizados (CRUD e cenários negativos).

## Como Rodar os Testes Localmente
- Pré-requisitos

- Java 17 instalado

- Maven instalado

- IntelliJ IDEA (opcional, mas recomendado)

### Clonar o projeto
git clone https://github.com/seu-repo-aqui.git

### Instalar dependências
mvn clean install

### Rodar os testes
mvn test


Ou dentro do IntelliJ, clicando no botão verde ao lado da classe BooksCrudTests.

## Estratégia de Testes

Os testes foram criados com foco nas seguintes validações:

### Testes Positivos (Happy Path)

- Criar um livro válido

- Buscar livro pelo ID

- Atualizar um livro existente

- Deletar um livro existente

Fluxo completo: criar → buscar → excluir

## Testes Negativos (Validações e Erros)

Atualizar livro inexistente (esperado: 404)

Deletar livro inexistente (esperado: 404)

Criar livro sem título (esperado: 400)

Criar livro com JSON vazio (esperado: 400)

Criar livro com caracteres especiais inválidos (esperado: 400)

Criar livro com payload muito grande (esperado: 400)

Esses testes garantem que a API responde corretamente tanto em fluxos válidos quanto em fluxos inadequados, cobrindo usabilidade e robustez.

## Relatório de Bugs / Melhorias Encontradas

Durante a automação foram identificadas inconsistências importantes na API:

### Problemas detectados
1. Atualizar livro inexistente retorna 200, mas deveria retornar 404

Impacto: a API não diferencia recurso existente de inexistente.

2. Criar livro com caracteres especiais inválidos retorna 200, deveria retornar 400

Impacto: falta de validação no backend.

3. Criar payload gigante retorna 200, deveria retornar 400 ou 413

Impacto: ausência de validação de tamanho.

4. Criar livro sem título retorna 200, deveria retornar 400

Impacto: API aceita campos obrigatórios vazios.

5. Criar livro com JSON vazio retorna 200

Impacto: backend não valida o corpo da requisição.

6. Fluxo completo deu 404 na busca, indicando que o livro criado não está persistindo

Impacto: problema grave de persistência ou endpoint inconsistente.
