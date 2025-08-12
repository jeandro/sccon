# SCCON – API Pessoas (Spring Boot 3 / Java 21)

Este projeto implementa uma API REST para gerenciamento de pessoas, com funcionalidades de CRUD, cálculo de idade e cálculo de salário relativo ao salário mínimo. É um exemplo funcional utilizando **Spring Boot 3**, **Java 21** e armazenamento em memória.

---

## 🚀 Tecnologias Utilizadas

- **Java 21** – Linguagem principal.
- **Spring Boot 3** – Framework para criação de aplicações Java.
- **Spring Web** – Criação de APIs REST.
- **Spring Validation (Jakarta Validation)** – Validação de dados de entrada.
- **Jackson** – Serialização e desserialização de JSON.
- **JUnit 5** – Testes unitários e de integração.
- **MockMvc** – Testes de controladores HTTP.
- **Maven** – Gerenciador de dependências e build.
- **Map em memória** (`ConcurrentHashMap`) – Armazenamento simples para o teste.

---

## 📦 Estrutura do Projeto

```
src/
 ├─ main/
 │   ├─ java/br/com/jeandro/sccon/
 │   │   ├─ controller/       # Endpoints REST
 │   │   ├─ dto/              # Objetos de transferência de dados
 │   │   ├─ exception/        # Tratamento de erros
 │   │   ├─ model/            # Entidades de domínio
 │   │   ├─ repository/       # Armazenamento em memória
 │   │   ├─ service/          # Lógica de negócio
 │   │   └─ util/             # Utilitários
 │   └─ resources/
 │       └─ application.yaml  # Configurações (formato de datas)
 └─ test/                     # Testes unitários e de integração
```

---

## ⚙️ Como Executar

### Pré-requisitos
- **Java 21** instalado
- **Maven 3.9+** instalado

### Passos para rodar
```bash
# Clonar o repositório
git clone https://github.com/jeandro/sccon.git
cd sccon-people

# Executar a aplicação
mvn spring-boot:run
```

A aplicação subirá por padrão em:  
📍 **http://localhost:8080**

---

## 📚 Endpoints

### **1. Listar pessoas**
```
GET /persons
```
Retorna todas as pessoas cadastradas.

---

### **2. Obter pessoa por ID**
```
GET /persons/{id}
```
Retorna os dados da pessoa pelo ID.

---

### **3. Criar nova pessoa**
```
POST /persons
Content-Type: application/json

{
  "nome": "Carlos Silva",
  "dataNascimento": "05/04/2000",
  "dataAdmissao": "04/06/2020",
  "salarioAtual": 3510.00
}
```
Retorna **201 Created** com a pessoa criada.

---

### **4. Remover pessoa**
```
DELETE /persons/{id}
```
Remove a pessoa pelo ID.

---

### **5. Atualizar pessoa (completa)**
```
PUT /persons/{id}
Content-Type: application/json

{
  "nome": "Carlos Atualizado",
  "dataNascimento": "05/04/2000",
  "dataAdmissao": "04/06/2020",
  "salarioAtual": 4000.00
}
```
Substitui todos os dados da pessoa.

---

### **6. Atualizar pessoa (parcial)**
```
PATCH /persons/{id}
Content-Type: application/json

{
  "nome": "Nome Alterado"
}
```
Atualiza apenas os campos enviados.

---

### **7. Calcular idade**
```
GET /persons/{id}/idade?output=dias|meses|anos
```
Calcula a idade da pessoa em **dias**, **meses** ou **anos**.

Exemplo:
```
GET /persons/1/idade?output=anos
```
Resposta:
```json
{
  "id": 1,
  "output": "anos",
  "valor": 25.75
}
```

---

### **8. Calcular múltiplos de salário mínimo**
```
GET /persons/{id}/salario?valorMinimo=1320
```
Retorna o número de salários mínimos correspondente ao salário atual.

Exemplo resposta:
```json
{
  "id": 1,
  "salarioAtualMultiplo": 2.5,
  "salarioMinimo": 1320.0
}
```

---

## 🧪 Testes
Para executar os testes:
```bash
mvn test
```

---


---

## 📖 Documentação da API (Swagger / OpenAPI)

Este projeto inclui configuração do **Swagger** através do `springdoc-openapi` (classe `OpenApiConfig`), permitindo a visualização e teste interativo dos endpoints.

Após iniciar a aplicação, acesse:
- **UI Interativa (Swagger UI)**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Especificação OpenAPI (JSON)**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

O Swagger oferece:
- Visualização de todos os endpoints com descrições e exemplos.
- Execução de chamadas diretamente pela interface.
- Geração automática da documentação com base nas anotações do código.

---

## 📄 Licença
Este projeto é apenas para fins educacionais e de demonstração.
