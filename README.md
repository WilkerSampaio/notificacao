# 📢 Notificação API

API responsável por enviar notificações por **e-mail** aos usuários sobre as tarefas pendentes, conforme disparos periódicos.  
O cron que controla esse disparo roda a cada **30 segundos** e está no **BFF**, que invoca essa API para efetuar as notificações.

---

## 🛠 Tecnologias Utilizadas

- ☕ **Java 17**  
- 🌱 **Spring Boot**  
- 📦 **Gradle**  
- 📧 **Spring Mail / JavaMail**  
- 🐳 **Docker**  
- 📄 **Swagger (OpenAPI)**  
- 🖼️ **Thymeleaf + HTML + CSS inline** 

---

## 🧭 Funcionalidades Principais

- Enviar notificações por **e-mail** para usuários sobre tarefas  
- Receber a requisição de disparo de notificação (via endpoint)  
- Configuração de envio via SMTP (Gmail, TLS)  
- Documentação dos endpoints via Swagger  

---

## 📋 Endpoint(s) Expostos

| Método  | Rota                  | Descrição                                  | Autenticação |
|---------|-----------------------|--------------------------------------------| -------------|
| POST    | `/notificacoes/email` | Dispara notificação por e-mail para usuário|  ❌          |

---

## 📄 application.yml de Configuração de E-mail & Servidor

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${EMAIL}
    password: ${SENHA}
    protocol: smtp
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
          connectiontimeout: 5000
          timeout: 3000
          writetimeout: 5000

  thymeleaf:
    enabled: true

envio:
  email:
    remetente: ${EMAIL}
    nomeRemetente: 'Agendador de Tarefa'

server:
  port: 8082
```

📌 Explicações rápidas:

- `spring.mail.*` → parâmetros de conexão com o servidor SMTP do Gmail  
- `envio.email.remetente` / `envio.email.nomeRemetente` → usados no “From” do e-mail  
- `server.port: 8082` → porta da API  

---

## 🚀 Docker

### Usando o JAR já gerado  

1. Certifique-se de que o JAR está em `build/libs/notificacao-0.0.1-SNAPSHOT.jar`  

2. Construa a imagem Docker:  
   ```bash
   docker build -t notificacao-api .
   ```

3. Execute o container:  
   ```bash
   docker run -e EMAIL=seu@gmail.com -e SENHA=suaSenha -p 8082:8082 notificacao-api
   ```

👉 A API estará disponível em:  
[http://localhost:8082/swagger-ui/index.html#/](http://localhost:8082/swagger-ui/index.html#/)

---

## 🔗 Integrações & Dependências Externas

- Comunicação com **Agendador de Tarefas API** para obter dados das tarefas que precisam de notificação  
- Recebe invocação do **cron do BFF** a cada 30 segundos  
- Envia e-mails via servidor SMTP configurado (Gmail no caso)  

Repositórios:

- Agendador de Tarefas API:  
  https://github.com/WilkerSampaio/agendador-tarefas-api.git  
- Usuário API:  
  https://github.com/WilkerSampaio/usuario-api
