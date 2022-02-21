# docket-desafio-back
Desafio técnico para Docket, suja descrição se encontra [aqui](DESCRICAO.md).

## Requisitos

- Java 11
- Maven 3.8 +
- Docker (Apenas em profile prod)

## Executando o projeto

O projeto possui duas configurações para **Spring Profiles**, e a escolha de uma delas é obrigatória para executar o projeto.

##### Dev

Executando em perfil de desenvolvimento (*dev*), os dados produzidos pela aplicação serão perdidos sempre que a execução for interrompida, uma vez que esse profile utiliza uma instância H2 em memória. Esse profile também é utilizado na execução dos testes de integração, que utilizam a base de dados demporária.

Para executar em profile *dev*, abra um terminal na raiz do projeto e execute: 

`mvn clean install -Dspring.profiles.active=dev`

`mvn spring-boot:run -Dspring-boot.run.profiles=dev`

Uma vez em execução, o estado do banco de dados pode ser consultado no endereço [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/) utilizando URL e credenciais definidas no arquivo *app-dev.properties*.

##### Prod

Diferentemente do anterior, o perfil de produção (*prod*) faz persistência de dados via conexão com um banco Postgres, cujas configurações do datasource estão no arquivo *app-prod.properties*.

Para facilitar a construção do ambiente, é possível subir uma instância de banco via Docker, utilizando o arquivo *docker-compose.yml* na raiz desse projeto. Para isso, siga os seguintes passos: 

1. Abra o arquivo *docker-compose.yml* que se encontra na raiz do projeto;
2. Altere o caminho do volume Docker para um diretório válido, onde serão armazenados os dados da aplicação (Ex: /home/mustella/dockerData/PostgreSQL/);
3. Garanta que a porta 5433 está disponível; caso não esteja, insira uma porta livre em *docker-compose.yml* e *app-prod.properties*;
4. Garanta que o serviço Docker está disponível através do comando: `docker info`
5. Abra o terminal no diretório onde está o *docker-compose.yml* e execute o comando: `docker-compose up -d`

Após essa sequência, a instância de banco estará pronta para ser utilizada.

Por fim, abra um terminal na raiz do projeto e execute:

`mvn clean install -Dspring.profiles.active=dev`

`mvn spring-boot:run -Dspring-boot.run.profiles=prod`

#### Como utilizar a API

Uma vez executado com sucesso, uma interface do Swagger-Ui estará disponível no endereço [localhost:8080/swagger-ui.html](localhost:8080/swagger-ui.html).

Essa interface pode ser utilizada tanto para visualização da descrição dos endpoints da API, quanto para execução dos mesmos.

#### Como navegar pela Interface de Usuário

Uma vez finalizados os serviços, foi proposta a construção de telas para navegação entre as funcionalidade utilizando a engine Thymeleaf. A página inicial prevista essa interface deveria ser renderizada no endereço [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/), exibindo os cartórios cadastrados e disponibilizando-os para consulta e edição.

Essa proposta não foi cumprida, entretanto, devido a dificuldades na configuração do Templates Resolver. Em evoluções futuras, espera-se que esse problema seja corrigido e navegação do sistema não se limite aos endpoints.

Algumas tentativas de resolver o problema foram baseadas [nesse](https://www.yawintutor.com/error-1564-error-resolving-template/), [nesse](https://stackoverflow.com/questions/59919934/spring-boot-thymeleaf-server-error-status-500) e [nesse](https://www.youtube.com/watch?v=KTBWCJPKiqk&t=189s) links, ainda que sem sucesso.

## Linguagem, Frameworks e Ferramentas

- Java 11
- Maven
- Spring e Spring Boot
- H2 e PostgreSQL
- Mapstruct
- Docker para hospedagem do banco de dados (Opcional)
- SpringFox (Swagger)
- Slf4j para Logs
- Thymeleaf (Sem sucesso)