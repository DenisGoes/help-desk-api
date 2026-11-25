# Help Desk API — Backend, Docker e AWS

API REST desenvolvida em Java com Spring Boot para gerenciamento de clientes e chamados de suporte técnico.

Este projeto faz parte de um laboratório prático com o objetivo de aplicar conceitos de desenvolvimento Backend, testes automatizados, conteinerização e automação de deploy utilizando ferramentas de DevOps e Cloud Computing.

## Tecnologias utilizadas

- **Java 21** — linguagem de programação.
- **Spring Boot** — desenvolvimento da API REST.
- **Spring Data JPA** — persistência e acesso aos dados.
- **Maven** — gerenciamento de dependências e build.
- **JUnit 5** — testes automatizados.
- **Mockito** — criação de mocks para testes unitários.
- **Docker** — conteinerização da aplicação.
- **Git e GitHub** — versionamento e gerenciamento do código.
- **GitHub Actions** — automação do pipeline CI/CD.
- **AWS EC2** — ambiente de hospedagem da aplicação.
- **Docker Hub** — publicação e distribuição das imagens Docker.
- **Postman** — testes e validação dos endpoints.

## Funcionalidades

### Clientes

- Cadastrar clientes.
- Listar clientes.
- Buscar cliente por ID.
- Atualizar dados de clientes.
- Remover clientes.
- Validar dados de entrada.
- Impedir o cadastro de e-mails duplicados.

### Chamados

- Abrir chamados vinculados a clientes.
- Listar chamados.
- Buscar chamado por ID.
- Atualizar chamados.
- Remover chamados.
- Gerenciar prioridade e status dos chamados.

### Tratamento de erros

- Exceções personalizadas.
- Tratamento centralizado de exceções.
- Validação de dados com Bean Validation.
- Respostas HTTP adequadas para diferentes situações.

## Endpoints

Base URL local: `http://localhost:8080`

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/clientes` | Cadastrar cliente |
| GET | `/api/clientes` | Listar clientes |
| GET | `/api/clientes/{id}` | Buscar cliente |
| PUT | `/api/clientes/{id}` | Atualizar cliente |
| DELETE | `/api/clientes/{id}` | Remover cliente |
| POST | `/api/chamados?clienteId={id}` | Abrir chamado |
| GET | `/api/chamados` | Listar chamados |
| GET | `/api/chamados/{id}` | Buscar chamado |
| PUT | `/api/chamados/{id}` | Atualizar chamado |
| DELETE | `/api/chamados/{id}` | Remover chamado |

## Executando localmente

### Pré-requisitos

- Java 21 ou superior.
- Git.

### Clonar o repositório

```bash
git clone https://github.com/DenisGoes/help-desk-api.git

cd help-desk-api
```

### Executar os testes

```bash
./mvnw test
```

### Gerar o build

```bash
./mvnw clean package
```

### Executar a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em:

`http://localhost:8080`

## Docker

A aplicação utiliza um Dockerfile multi-stage para separar a etapa de compilação do ambiente de execução, reduzindo o tamanho da imagem final.

### Construir a imagem

```bash
docker build -t help-desk-api:latest .
```

### Executar o container

```bash
docker run -d \
  --name helpdesk-api \
  -p 8080:8080 \
  help-desk-api:latest
```

### Verificar os containers

```bash
docker ps
```

### Visualizar os logs

```bash
docker logs helpdesk-api
```

## CI/CD — GitHub Actions

O projeto utiliza GitHub Actions para automatizar o processo de integração e entrega contínua.

O pipeline é organizado em três etapas:

**1. CI — Integração Contínua**
- Checkout do código.
- Configuração do Java 21.
- Execução dos testes automatizados.
- Build da aplicação com Maven.

**2. Publicação da imagem Docker**
- Autenticação no Docker Hub.
- Construção da imagem.
- Publicação da imagem no repositório Docker Hub.

**3. Deploy — AWS EC2**
- Conexão com a instância EC2.
- Download da nova imagem Docker.
- Substituição do container anterior.
- Inicialização da versão atualizada da API.

O objetivo é automatizar o fluxo de atualização da aplicação a partir de alterações enviadas para a branch `main`.

## AWS EC2

A aplicação é preparada para execução em uma instância Amazon EC2 utilizando Docker.

O ambiente contempla:
- Instância EC2.
- Elastic IP para manter um endereço IPv4 público estável.
- Security Group para controle de acesso.
- Docker para execução da aplicação.

## Objetivos de aprendizagem

Este laboratório foi desenvolvido para praticar:

- Desenvolvimento de APIs REST com Java e Spring Boot.
- Organização em camadas e separação de responsabilidades.
- Persistência de dados com JPA.
- Testes unitários e uso de mocks.
- Criação e execução de containers Docker.
- Automação de testes e builds com GitHub Actions.
- Publicação de imagens Docker.
- Hospedagem de aplicações na AWS EC2.
- Implementação de um fluxo de CI/CD.

---

**Projeto desenvolvido para fins educacionais e de aprendizado prático em Backend, DevOps e Cloud Computing.**