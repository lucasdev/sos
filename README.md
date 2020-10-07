# Frontend JSF

#### Setup desenvolvimento
1. [jdk-11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
1. [apache-maven-3.6.3](https://maven.apache.org/download.cgi)
2. [apache-tomcat-9.0.38](https://tomcat.apache.org/download-90.cgi)


### Como instalar e executar o projeto

1. Clone o repositório.
2. Acesse o diretório do projeto.
3. Instale as dependências.
4. Compile o projeto.
5. Rodar em uma instância do Tomcat-9, o contexto deve ser confirado para `/sos`

```console
git clone git@github.com:lucasdev/sos.git
cd sos
mvn clean install
mvn package
```

## Url de acesso

http://localhost:8080/sos/index.xhtml