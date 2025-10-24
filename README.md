# redis-multitenancy

Exemplo simples de multitenancy com **Spring Boot + Redis**, onde cada tenant possui seu próprio  Redis.

## Tecnologias
- Spring Boot 3.x
- Spring Data Redis
- Docker + Redis
- Maven

### Como executar
```bash
docker-compose up -d
mvn spring-boot:run
```

### Requisições
```
curl --location 'localhost:8080/v1/persons' \
--header 'X-Tenant: one'
```

```
curl --location 'localhost:8080/v1/persons' \
--header 'X-Tenant: one' \
--header 'Content-Type: application/json' \
--data '{
  "id": "1d7188bc-1765-46d2-a0be-bcce4745c259",
  "name": "Maria",
  "birthdate": "2000-04-01"
}'
```

```
curl --location 'localhost:8080/v1/persons' \
--header 'X-Tenant: two'
```

```
curl --location 'localhost:8080/v1/persons' \
--header 'X-Tenant: two' \
--header 'Content-Type: application/json' \
--data '{
  "id": "7c9f4e31-1a63-48f0-80e0-d2ebb2a22506",
  "name": "Joao",
  "birthdate": "1992-04-01"
}'
```
