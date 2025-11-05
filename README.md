# 🪚 SightCut - Sistema de Gestão para Marcenarias

Sistema web para gerenciamento de projetos, clientes e cálculo automático de custos para marcenarias.

## 🚀 Tecnologias

- **Backend:** Java 17, Spring Boot, PostgreSQL
- **Frontend:** HTML5, CSS3, JavaScript
- **Segurança:** BCrypt para criptografia de senhas

## ✨ Funcionalidades

- ✅ Sistema de login e cadastro com autenticação
- ✅ Gestão de perfil com upload de foto (Base64)
- ✅ Dashboard personalizado com saudação
- ✅ Gerenciamento de clientes
- ✅ Gerenciamento de materiais
- ✅ Controle de projetos
- ✅ Cálculo automático de custos e margem de lucro

## 📦 Pré-requisitos

- Java 17+
- PostgreSQL 12+
- Maven 3.8+

## 🔧 Instalação e Configuração

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/sightcut.git
cd sightcut
```

### 2. Configure o banco de dados
Execute o script SQL disponível em `/sql/sightcut-schema.sql`
```sql
CREATE DATABASE sightcut;
```

### 3. Configure application.properties
Edite `src/main/resources/application.properties` com suas credenciais:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sightcut
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 4. Execute a aplicação
```bash
mvn spring-boot:run
```

### 5. Acesse no navegador
```
http://localhost:8080
```

## 🔐 Segurança

- Senhas criptografadas com BCrypt
