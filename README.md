# Automação de Testes – API Books (FakeRestAPI)

## Sobre o Projeto

Este projeto tem como objetivo validar as operações de **CRUD (Create, Read, Update, Delete)** da API pública **FakeRestAPI**, especificamente o endpoint:




A automação foi desenvolvida com foco em:
- Validação funcional dos endpoints
- Validação de códigos de status HTTP
- Validação de contrato (JSON Schema)
- Identificação de falhas e limitações da API

O projeto foi conduzido com uma abordagem **realista de QA**, considerando o comportamento real da API e evitando testes instáveis (flaky).

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Rest Assured**
- **JUnit 5**
- **Maven**
- **JSON Schema Validator**
- **Git / GitHub**

---

## ▶️ Como Rodar os Testes Localmente

### Pré-requisitos
- Java 17 instalado
- Maven instalado

### Passo a passo

1. Clone o repositório:
```bash
https://github.com/gabrielhoffmannhub/Desafio-Tecnico-QA
```
2. Acesse o projeto 
``` bash
cd Desafio-Tecnico
```
3. Execute os testes:
```bash
mvn test
```
## Estratégia de Testes

### Abordagem

Cada endpoint foi testado de forma isolada, evitando dependência entre operações.

Não foi utilizado fluxo Create → Read → Update → Delete, pois a API não garante persistência consistente.

Foram criados cenários positivos e negativos para cada operação.

Testes de contrato (JSON Schema) foram aplicados nos endpoints que retornam body.

Quando a API apresentou comportamento inconsistente ou inválido, os testes não foram ajustados para “passar”; as falhas foram registradas e documentadas.

### Justificativa

A FakeRestAPI apresenta limitações conhecidas, como:

- Falta de persistência confiável
- Retornos inconsistentes de status HTTP
- Respostas que não seguem padrões de contrato

Por isso, a estratégia priorizou confiabilidade dos testes e transparência dos resultados.

---

## Cenários Testados e Resultados

### CREATE

| Cenário | Resultado |
|------|------|
Criar livro válido | Erro de contrato (JSON Schema não compatível com resposta da API) |
Criar livro com ID já existente | 200 (correto) |
Tipo de dado inválido | 400 (correto) |
Campos vazios | 400 (correto) |
Campos muito longos | 400 esperado, API não valida |
Data inválida | 400 (correto) |
Campo extra não documentado | Erro de contrato (JSON Schema) |

Observação:  
As falhas marcadas como erro de contrato ocorreram porque a API retorna:

- `excerpt` como `null`
- `publishDate` fora do padrão ISO 8601 completo  

Esses problemas não puderam ser resolvidos no teste sem mascarar erros reais da API.

---

### READ

| Cenário | Resultado |
|------|------|
Buscar todos os livros | 200 (correto) |
Buscar ID inexistente | 404 (correto) |
Buscar ID zero | 404 (correto) |

Observação:  
O cenário de buscar ID existente foi removido, pois a API não garante persistência nem existência de IDs.

---

### UPDATE

| Cenário | Resultado |
|------|------|
Atualizar livro válido | Erro de contrato (JSON Schema) |
Atualizar livro inexistente | 200 (incorreto) |
Atualizar com payload inválido | 400 (correto) |
Atualizar com ID diferente no body | 200 (incorreto) |

Observação:  
A API não valida:

- Existência do recurso
- Consistência entre ID do path e ID do body

---

### DELETE

| Cenário | Resultado |
|------|------|
Excluir livro existente | 200 (correto) |
Excluir livro inexistente | 200 (incorreto) |
Excluir livro duas vezes | 200 (incorreto) |

Observação:  
O endpoint DELETE retorna 200 OK para qualquer ID, mesmo quando o recurso não existe, violando a semântica REST esperada.

---

## Relatório de Bugs e Melhorias

### Bugs Identificados

- API retorna 200 OK ao excluir recursos inexistentes
- API retorna 200 OK ao atualizar recursos inexistentes
- API ignora inconsistência entre ID do path e ID do body
- Campo `excerpt` retorna `null` sem estar documentado como opcional
- Campo `publishDate` não segue padrão ISO 8601 completo
- Validação insuficiente para campos muito longos
- POST pode retornar body vazio ou inconsistências de contrato

### Melhorias Sugeridas

- Padronizar respostas de erro (404, 400)
- Garantir persistência ou documentar ausência
- Corrigir contrato dos campos `excerpt` e `publishDate`
- Validar consistência entre path e payload
- Ajustar DELETE para retornar 404 quando o recurso não existir

