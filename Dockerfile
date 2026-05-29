FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /build

# Copiar os ficheiros de configuração do Maven
COPY pom.xml .

# Copiar o código-fonte do projeto
COPY src ./src

# Compilar e gerar o JAR saltando os testes para acelerar o deploy
RUN mvn -B -DskipTests clean package

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Copia qualquer JAR gerado na pasta target e renomeia para app.jar
COPY --from=builder /build/target/*.jar /app/app.jar

# Configurar a variável de ambiente para corrigir o crash de reflexão do Log4j no Docker
ENV JAVA_TOOL_OPTIONS="-Dlog4j.source.skipPage=true -DLog4jContextSelector=org.apache.logging.log4j.core.selector.ClassLoaderContextSelector"

# Boas práticas de segurança: Criar um utilizador sem privilégios de root para correr o jogo
RUN useradd -m appuser && chown -R appuser:appuser /app
USER appuser

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]