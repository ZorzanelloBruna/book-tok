# Pré-requisitos
- Java 17+

- Maven 3.8+

- IDE utilizada para desenvolvimento - STS   | Porém pode ser utilizada tbm (IntelliJ, VS Code, Eclipse) 

- Postman ou Insomnia

# Passo a passo
<!--Clone o repositório -->
- git clone https://github.com/ZorzanelloBruna/book-tok.git
<!-- Entre na pasta-->
- cd booktok
<!--Compile e execute os testes -->
- mvn clean test
<!--Execute a aplicação -->
- mvn spring-boot:run

# A aplicação estará disponível em: 
- Url - http://localhost:8080
- Swagger - http://localhost:8080/swagger-ui/index.html#/

# Exemplos de requisições
## USUARIO
### Salvar Usuário
<!---->
**URL da requisição:**
#### POST: http://localhost:8080/api/usuario

**Body da requisição (JSON):**

```json
{
    "nome": "Pedro",
    "email": "teste@gmail.com",
    "verSpoilers": true
}
```
**Response (JSON):**
*201 Created*
```json
{
    "id": 1,
    "nome": "Pedro",
    "email": "teste@gmail.com",
    "verSpoilers": true
}
```
### Atualizar Usuário
<!---->
**URL da requisição:**
#### PUT: http://localhost:8080/api/usuario/{usuarioId}

**Body da requisição (JSON):**

```json
{
    "nome": "Bruna",
    "email": "teste@gmail.com",
    "verSpoilers": false
}
```
**Response (JSON):** 
*200 OK*
```json
{
    "id": 1,
    "nome": "Bruna",
    "email": "teste@gmail.com",
    "verSpoilers": false
}
```
### Buscar usuário por ID
<!---->
**URL da requisição:**
#### GET: http://localhost:8080/api/usuario/{usuarioId}

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*200 OK*
```json
{
    "id": 1,
    "nome": "Bruna",
    "email": "teste@gmail.com",
    "verSpoilers": false
}
```
### Buscar todos Usuários
<!---->
**URL da requisição:**
#### GET: http://localhost:8080/api/usuario

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*200 OK*
```json
{
        "id": 1,
        "nome": "Bruna",
        "email": "teste@gmail.com",
        "verSpoilers": false
    },
    {
        "id": 2,
        "nome": "Pedro",
        "email": "teste@yahoo.com",
        "verSpoilers": true
    }
```
### Remover Usuário
<!---->
**URL da requisição:**
#### DELETE: http://localhost:8080/api/usuario/{usuarioId}

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*204 No Content*

## LIVRO
### Salvar Livro
<!---->
**URL da requisição:**
#### POST: http://localhost:8080/api/livro

**Body da requisição (JSON):**

```json
{
    "titulo": "Analise sobre a inteligencia de Jesus Cristo",
    "autor": "Augusto Curry",
    "resumo": "autor que era ateu, resolveu estudar sobre a mente de Jesus e se converteu, no livro tras evidencias do porque o mestre dos mestres nao pode ser um personagem.",
    "anoPublicacao": 2000,
    "isbn": "1122380"
}
```
**Response (JSON):**
*201 Created*
```json
{
    "id": 1,
    "titulo": "Analise sobre a inteligencia de Jesus Cristo",
    "autor": "Augusto Curry",
    "resumo": "autor que era ateu, resolveu estudar sobre a mente de Jesus e se converteu, no livro tras evidencias do porque o mestre dos mestres nao pode ser um personagem.",
    "anoPublicacao": 2000,
    "isbn": "1122380"
}
```
### Buscar Livro por ID
<!---->
**URL da requisição:**
#### GET: http://localhost:8080/api/livro/{livroId}

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*200 OK*
```json
{
    "id": 1,
    "titulo": "Analise sobre a inteligencia de Jesus Cristo",
    "autor": "Augusto Curry",
    "resumo": "autor que era ateu, resolveu estudar sobre a mente de Jesus e se converteu, no livro tras evidencias do porque o mestre dos mestres nao pode ser um personagem.",
    "anoPublicacao": 2000,
    "isbn": "1122380"
}
```
### Buscar todos Livros
<!---->
**URL da requisição:**
#### GET: http://localhost:8080/api/livro

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*200 Ok*
```json
[
    {
        "id": 1,
        "titulo": "Analise sobre a inteligencia de Jesus Cristo",
        "autor": "Augusto Curry",
        "resumo": "autor que era ateu, resolveu estudar sobre a mente de Jesus e se converteu, no livro tras evidencias do porque o mestre dos mestres nao pode ser um personagem.",
        "anoPublicacao": 2000,
        "isbn": "1122380"
    },
    {
        "id": 2,
        "titulo": "Vespera",
        "autor": "Carla Madeira",
        "resumo": "Novo romance da autora do fenômeno Tudo é rio, Véspera retoma a escrita brilhante e contagiante de Carla Madeira, que desperta todo tipo de emoção no leitor.",
        "anoPublicacao": 2021,
        "isbn": "6555872985"
    }
]
```

### Atualizar Livro
<!---->
**URL da requisição:**
#### PUT: http://localhost:8080/api/livro/{livroId}

**Body da requisição (JSON):**

```json
{
     "titulo": "Analise sobre a inteligencia de Jesus Cristo",
    "autor": "Augusto Curry",
    "resumo": "autor que era ateu, resolveu estudar sobre a mente de Jesus e se converteu, no livro tras evidencias do porque o mestre dos mestres nao pode ser um personagem.",
    "anoPublicacao": 2015,
    "isbn": "1122380"
}
```
**Response (JSON):**
*200 OK*
```json
{
    "id": 1,
    "titulo": "Analise sobre a inteligencia de Jesus Cristo",
    "autor": "Augusto Curry",
    "resumo": "autor que era ateu, resolveu estudar sobre a mente de Jesus e se converteu, no livro tras evidencias do porque o mestre dos mestres nao pode ser um personagem.",
    "anoPublicacao": 2015,
    "isbn": "1122380"
}
```

### Deletar Livro
<!---->
**URL da requisição:**
#### DELETE: http://localhost:8080/api/livro/{livroId}

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*204 No Content*

## PROGRESSO LEITURA
### Salvar Progresso de Leitura
<!---->
**URL da requisição:**
#### POST: http://localhost:8080/api/progresso-leitura

**Body da requisição (JSON):**

```json
{
   "usuarioId" : 1,
   "livroId" : 1,
   "paginaAtual" : 5
}
```
**Response (JSON):**
*201 Created*
```json
{
    "id": 1,
    "usuarioId": 1,
    "livroId": 1,
    "paginaAtual": 5,
    "dataRegistro": "02/09/2025"
}
```
### Atualizar Progresso de Leitura
<!---->
**URL da requisição:**
#### PUT: http://localhost:8080/api/progresso-leitura/{id}

**Body da requisição (JSON):**

```json
{
   "usuarioId" : 1,
    "livroId" : 1,
    "paginaAtual" : 100
}
```
**Response (JSON):**
*200 OK*
```json
{
    "id": 1,
    "usuarioId": 1,
    "livroId": 1,
    "paginaAtual": 100,
    "dataRegistro": "02/09/2025"
}
```
### Buscar Progresso por usuario
<!---->
**URL da requisição:**
#### GET: http://localhost:8080/api/progresso-leitura/usuario/{usuarioId}

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*200 OK*
```json
{    
    "id": 1,
    "usuarioId": 1,
    "livroId": 1,
    "paginaAtual": 100,
    "dataRegistro": "02/09/2025"
}
```
### Buscar Progresso por livro
<!---->
**URL da requisição:**
#### GET: http://localhost:8080/api/progresso-leitura/usuario/{livroId}

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*200 OK*
```json
{    
    "id": 1,
    "usuarioId": 1,
    "livroId": 1,
    "paginaAtual": 100,
    "dataRegistro": "02/09/2025"
}
```
### Remover Progresso 
<!---->
**URL da requisição:**
#### DELETE: http://localhost:8080/api/progresso-leitura/usuario/{id}

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*204 No Content*

## COMENTARIO
### Salvar Comentario
<!---->
**URL da requisição:**
#### POST: http://localhost:8080/api/comentario

**Body da requisição (JSON):**

```json
{
   "livroId": 1,
   "usuarioId": 1,
   "mensagem": "sem spoiler2!",
   "spoiler": false
}
```
**Response (JSON):**
*201 Created*
```json
{
   "id": 1,
   "livroId": 1,
   "usuarioId": 1,
   "mensagem": "sem spoiler2!",
   "dataComentario": "02/09/2025",
   "spoiler": false
}
```
### Listar Comentarios (o retorno será conforme preferencia do usuario na hora do cadastro - atributo (verSpoiler) se for true listará todos, se for false, vai listar somente sem spoiler)
<!---->
**URL da requisição:**
#### GET: http://localhost:8080/api/comentario/livro/{livroId}?usuarioId={usuarioId}

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*200 OK*
```json
{
   "id": 1,
   "livroId": 1,
   "usuarioId": 1,
   "mensagem": "sem spoiler2!",
   "dataComentario": "02/09/2025",
   "spoiler": false
}
```
### Listar Comentario para usuario não logado( só aparecerá comentario sem spoiler)
<!---->
**URL da requisição:**
#### GET: http://localhost:8080/api/comentario/livro/{livroId}

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*201 Created*
```json
{
   "id": 1,
   "livroId": 1,
   "usuarioId": 1,
   "mensagem": "sem spoiler2!",
   "dataComentario": "02/09/2025",
   "spoiler": false
}
```
### Listar todos Comentarios
<!---->
**URL da requisição:**
#### GET: http://localhost:8080/api/comentario

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*200 OK*
```json
{
   {
        "id": 1,
        "livroId": 1,
        "usuarioId": 1,
        "mensagem": "com spoiler2!",
        "dataComentario": "02/09/2025",
        "spoiler": true
    },
    {
        "id": 2,
        "livroId": 1,
        "usuarioId": 1,
        "mensagem": "sem spoiler2!",
        "dataComentario": "02/09/2025",
        "spoiler": false
    },
    {
        "id": 3,
        "livroId": 1,
        "usuarioId": 1,
        "mensagem": "com spoiler2!",
        "dataComentario": "02/09/2025",
        "spoiler": true
    }
}
```
### Atualizar Comentario
<!---->
**URL da requisição:**
#### PUT: http://localhost:8080/api/comentario/{id}

**Body da requisição (JSON):**

```json
{
  "livroId": 1,
  "usuarioId": 2,
  "mensagem": "spoiler atualizado!",
  "spoiler": true
}
```
**Response (JSON):**
*200 OK*
```json
{
   "id": 1,
   "livroId": 1,
   "usuarioId": 1,
   "mensagem": "spoiler atualizado!",
   "dataComentario": "02/09/2025",
   "spoiler": true
}
```
### Deletar Comentario
<!---->
**URL da requisição:**
#### POST: http://localhost:8080/api/comentario/{id}?usuarioId={usuarioId}

**Body da requisição (JSON):**

```json
{}
```
**Response (JSON):**
*204 No Content*

## COBERTURA DE TESTES - 91%
*Ferramenta Utilizada na cobertura de testes : jacoco*
<!---->
*Localizada em : booktok\target\site\jacoco\index.xml*

