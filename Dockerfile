# --- ETAPA 1: Construcción (Build) ---
FROM maven:3.8.6-jdk-8 AS build
WORKDIR /app

# Copiamos archivos de dependencias
COPY pom.xml .
# Copiamos el código fuente
COPY src ./src

# Compilamos y empaquetamos (saltando tests para ir rápido)
RUN mvn clean package -DskipTests

# --- ETAPA 2: Ejecución (Runtime) ---
# Usamos una versión antigua de Alpine con Java 8 donde JNDI es explotable
FROM openjdk:8u111-jdk-alpine

WORKDIR /app

# Copiamos el JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Ejecutamos la aplicación asegurando que confíe en codebases remotos
ENTRYPOINT ["java", "-Dcom.sun.jndi.ldap.object.trustURLCodebase=true", "-jar", "app.jar"]