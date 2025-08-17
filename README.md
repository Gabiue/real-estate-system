# 🏠 Real Estate System

Sistema de gestão imobiliária desenvolvido em **Spring Boot** com arquitetura limpa (Clean Architecture) e Domain-Driven Design (DDD).

## 📋 Sobre o Projeto

O **Real Estate System** é uma API REST para gerenciamento de imobiliárias, permitindo o controle completo de:
- **Clientes** (cadastro, consulta, atualização)
- **Imóveis** (gestão de propriedades e status)
- **Propostas** (negociações e simulações financeiras)

## 🏗️ Arquitetura

O projeto segue os princípios de **Clean Architecture** com separação clara em camadas:

```
📁 src/main/java/com/kaue/realestatesystem/
├── 🌐 api/                    # Camada de Apresentação
│   ├── controllers/           # REST Controllers
│   ├── dto/                   # Data Transfer Objects
│   └── mappers/               # Conversores DTO ↔ Domain
├── 🔧 application/            # Camada de Aplicação
│   └── services/              # Casos de uso e orquestração
├── 🏛️ domain/                 # Camada de Domínio
│   ├── entities/              # Entidades de negócio
│   ├── enums/                 # Enumerações
│   └── repositories/          # Interfaces dos repositórios
├── 🗄️ infrastructure/         # Camada de Infraestrutura
│   ├── entitiesDB/            # Entidades JPA
│   ├── mappersDB/             # Conversores JPA ↔ Domain
│   └── repositoriesDB/        # Implementações JPA
└── ⚙️ config/                 # Configurações
```

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Spring Security**
- **MySQL 8**
- **Bean Validation**
- **Lombok**
- **Maven**

## 📊 Modelo de Dados

### Cliente
```java
- id: Long
- nome: String (2-100 chars)
- cpf: String (formato: 000.000.000-00)
- email: String (formato válido)
- telefone: String (formato: (11) 99999-9999)
- dataNascimento: LocalDate
- endereco: String
- observacoes: String (max 500 chars)
- criadoEm: LocalDateTime
- atualizadoEm: LocalDateTime
```

### Imóvel
```java
- id: Long
- codigo: String (auto-gerado: IMV-XXXXXXXX)
- tipo: String (CASA|APARTAMENTO|TERRENO|COMERCIAL)
- endereco: String (10-300 chars)
- preco: BigDecimal (min R$ 1.000)
- quartos: Integer (0-50)
- areaConstruida: Double (1-100.000 m²)
- descricao: String (max 1000 chars)
- status: String (DISPONIVEL|RESERVADO|VENDIDO|INATIVO)
- criadoEm: LocalDateTime
- atualizadoEm: LocalDateTime
```

### Proposta
```java
- id: Long
- numero: String (auto-gerado: PROP-XXXXXXXX)
- clienteId: Long
- imovelId: Long
- valorProposta: BigDecimal
- valorEntrada: BigDecimal
- quantidadeParcelas: Integer (1-480)
- status: StatusProposta (PENDENTE|APROVADA|REJEITADA|CANCELADA|EXPIRADA)
- observacoes: String
- motivoRejeicao: String
- validaAte: LocalDateTime
- criadaEm: LocalDateTime
- atualizadaEm: LocalDateTime
```

## 🔌 Principais Endpoints

### 👥 Clientes
```http
POST   /api/clientes              # Criar cliente
GET    /api/clientes              # Listar todos
GET    /api/clientes/{id}         # Buscar por ID
PUT    /api/clientes/{id}         # Atualizar cliente
DELETE /api/clientes/{id}         # Deletar cliente
GET    /api/clientes/cpf/{cpf}    # Buscar por CPF
```

### 🏠 Imóveis
```http
POST   /api/imoveis                    # Cadastrar imóvel
GET    /api/imoveis                    # Listar com filtros
GET    /api/imoveis/{id}               # Buscar por ID
PUT    /api/imoveis/{id}               # Atualizar imóvel
DELETE /api/imoveis/{id}               # Deletar imóvel
GET    /api/imoveis/codigo/{codigo}    # Buscar por código
GET    /api/imoveis/disponiveis        # Listar disponíveis
PUT    /api/imoveis/{id}/status        # Alterar status
```

### 📋 Propostas
```http
POST   /api/propostas                     # Criar proposta
GET    /api/propostas                     # Listar com filtros
GET    /api/propostas/{id}                # Buscar por ID
PUT    /api/propostas/{id}/aprovar        # Aprovar proposta
PUT    /api/propostas/{id}/rejeitar       # Rejeitar proposta
PUT    /api/propostas/{id}/cancelar       # Cancelar proposta
POST   /api/propostas/simular             # Simular financiamento
```

## ⚙️ Como Executar o Projeto

### 📋 Pré-requisitos
Certifique-se de ter instalado:
- **Java 17+** ([Download aqui](https://www.oracle.com/java/technologies/downloads/))
- **MySQL 8+** ([Download aqui](https://dev.mysql.com/downloads/mysql/))
- **Git** ([Download aqui](https://git-scm.com/downloads))

### 🚀 Passo a Passo Completo

#### **Passo 1: Clonar o Repositório**
```bash
# Clone o projeto
git clone https://github.com/seu-usuario/realestatesystem.git

# Entre na pasta do projeto
cd realestatesystem
```

#### **Passo 2: Configurar o Banco de Dados MySQL**

**2.1** Conecte-se ao MySQL como **root** (usuário administrador):
```bash
# No terminal/cmd, conecte ao MySQL
mysql -u root -p
# Digite sua senha de root quando solicitado
```

**2.2** Dentro do MySQL, execute os comandos para preparar o banco:
```sql
-- Criar o banco de dados
CREATE DATABASE realestatesystem;

-- Criar usuário específico para a aplicação
CREATE USER 'realestate_user'@'localhost' IDENTIFIED BY 'minhasenha123';

-- Conceder todas as permissões do banco para o usuário
GRANT ALL PRIVILEGES ON realestatesystem.* TO 'realestate_user'@'localhost';

-- Aplicar as mudanças
FLUSH PRIVILEGES;

-- Verificar se o banco foi criado (opcional)
SHOW DATABASES;

-- Sair do MySQL
EXIT;
```

#### **Passo 3: Verificar Configurações**
O arquivo `src/main/resources/application.properties` já está configurado:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/realestatesystem
spring.datasource.username=realestate_user
spring.datasource.password=minhasenha123
```

> ⚠️ **Importante**: Se você alterou a senha do usuário no Passo 2, ajuste também no arquivo `application.properties`

#### **Passo 4: Executar a Aplicação**

**Opção A - Executar Direto (Recomendado):**
```bash
# Execute o projeto (vai baixar dependências automaticamente)
./mvnw spring-boot:run

# No Windows, use:
# mvnw.cmd spring-boot:run
```

**Opção B - Compilar e Executar:**
```bash
# Compilar o projeto
./mvnw clean package -DskipTests

# Executar o JAR gerado
java -jar target/realestatesystem-0.0.1-SNAPSHOT.jar
```

#### **Passo 5: Verificar se Funcionou**
Se tudo deu certo, você verá algo assim no terminal:
```
Started RealEstateSystemApplication in X.XXX seconds
```

🎉 **Pronto!** A aplicação está rodando em: `http://localhost:8080`

### 🧪 Testar a API

**Verificar se está funcionando:**
```bash
curl http://localhost:8080/api/clientes
# Deve retornar: []
```

**Criar seu primeiro cliente:**
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "cpf": "123.456.789-00",
    "email": "joao@email.com",
    "telefone": "(11) 99999-9999",
    "dataNascimento": "1990-05-15"
  }'
```

### 🔧 Solução de Problemas

**❌ Erro de conexão com banco:**
- Verifique se o MySQL está rodando
- Confirme usuário e senha no `application.properties`
- Teste a conexão: `mysql -u realestate_user -p realestatesystem`

**❌ Porta 8080 já está em uso:**
- Adicione no `application.properties`: `server.port=8081`
- Ou pare o processo que está usando a porta 8080

**❌ Erro de Java Version:**
- Verifique sua versão: `java -version`
- Deve ser Java 17 ou superior

### 🛑 Para Parar a Aplicação
No terminal onde está rodando, pressione: `Ctrl + C`

## 📝 Exemplos de Uso

### Criar Cliente
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "cpf": "123.456.789-00",
    "email": "joao@email.com",
    "telefone": "(11) 99999-9999",
    "dataNascimento": "1990-05-15"
  }'
```

### Cadastrar Imóvel
```bash
curl -X POST http://localhost:8080/api/imoveis \
  -H "Content-Type: application/json" \
  -d '{
    "tipo": "APARTAMENTO",
    "endereco": "Rua das Flores, 123 - São Paulo/SP",
    "area": 85.50,
    "preco": 450000.00,
    "quartos": 3,
    "descricao": "Apartamento moderno com vista para o parque"
  }'
```

### Criar Proposta
```bash
curl -X POST http://localhost:8080/api/propostas \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "imovelId": 1,
    "valorProposta": 420000.00,
    "valorEntrada": 84000.00,
    "quantidadeParcelas": 360,
    "observacoes": "Cliente interessado, aguardando aprovação do financiamento"
  }'
```

## 🔐 Regras de Negócio

### Cliente
- CPF deve ser único e válido
- Email deve ter formato válido
- Telefone no formato (XX) XXXXX-XXXX
- Data de nascimento deve ser no passado

### Imóvel
- Código é gerado automaticamente (IMV-XXXXXXXX)
- Preço mínimo: R$ 1.000
- Status padrão: DISPONIVEL
- Transições de status validadas

### Proposta
- Número é gerado automaticamente (PROP-XXXXXXXX)
- Validade padrão: 15 dias
- Cálculos automáticos: valor financiado, percentual entrada, valor parcela
- Status controlado por máquina de estados

## 🏆 Funcionalidades Avançadas

### Simulação de Financiamento
- Cálculo com ou sem juros
- Tabela Price para financiamentos
- Validações de entrada mínima e prazo máximo

### Filtros Avançados
- Imóveis por faixa de preço
- Propostas por status, cliente ou imóvel
- Clientes por CPF

### Gestão de Status
- Imóveis: Disponível → Reservado → Vendido
- Propostas: Pendente → Aprovada/Rejeitada/Cancelada/Expirada

## 🧪 Validações

### Automáticas
- **Bean Validation** em todos os DTOs
- **Validações de negócio** nas entidades de domínio
- **Consistência** entre camadas

### Customizadas
- CPF com dígito verificador
- Telefone com máscara brasileira
- Faixas de valores para imóveis
- Prazos máximos para financiamento

## 📈 Status do Projeto

### ✅ Implementado
- [x] CRUD completo de Clientes
- [x] CRUD completo de Imóveis
- [x] CRUD completo de Propostas
- [x] Simulação de financiamento
- [x] Filtros e buscas avançadas
- [x] Validações de negócio
- [x] Arquitetura limpa
- [x] Configuração MySQL

### 🔄 Em Desenvolvimento
- [ ] Tratamento global de exceções
- [ ] Documentação OpenAPI/Swagger
- [ ] Migração com Flyway
- [ ] Paginação nos endpoints
- [ ] Logs estruturados
- [ ] Testes unitários e integração

### 🎯 Roadmap
- [ ] Autenticação JWT
- [ ] Cache com Redis
- [ ] Métricas com Micrometer
- [ ] Deploy em containers
- [ ] CI/CD Pipeline

## 👨‍💻 Desenvolvedor

**Gabriel Kauê Rodrigues Tavares** - Desenvolvedor Backend

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---
