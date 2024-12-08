
# LiterAlura Challenge

Este proyecto es parte de un **challenge de programación** dentro de la formación de **Spring Boot** del programa **Oracle One** de **Alura LATAM**.

## Objetivo

El objetivo de este proyecto es **desarrollar un Catálogo de Libros** que ofrezca interacción textual (a través de la consola) con los usuarios. Los libros se buscarán a través de una API pública específica, proporcionando al menos 5 opciones de interacción para el usuario.

### Funcionalidades principales:
1. **Buscar libro por su Título**
2. **Buscar libro por su Autor**
3. **Buscar libros por Idioma**
4. **Buscar autores vivos dentro de un determinado rango de años**
5. **Top 10 libros más descargados**
6. **Ver historial de libros buscados previamente**
7. **Ver historial de autores buscados previamente**

## Descripción de la aplicación

La aplicación está construida utilizando **Spring Boot** y funciona completamente en consola. El flujo principal consiste en que el usuario interactúe con el sistema a través de un menú de opciones, donde puede realizar varias consultas relacionadas con libros y autores, tales como:

- Buscar libros por título, autor, idioma, o por autores vivos en un año específico.
- Consultar los 10 libros más descargados.
- Ver un historial de libros y autores que han sido consultados previamente.

## Dependencias principales

Las dependencias de este proyecto incluyen:

- **Spring Boot**: Framework principal para la aplicación.
- **Spring Data JPA**: Para la persistencia en base de datos.
- **PostgreSQL**: Base de datos usada para almacenar información de libros y autores.
- **Jackson Databind**: Para el manejo de datos JSON al interactuar con la API externa.

El archivo `pom.xml` que se encuentra en el proyecto contiene las siguientes dependencias claves:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.16.0</version>
    </dependency>
</dependencies>
```

## ¿Cómo ejecutar la aplicación?

1. **Clona o descarga este repositorio en tu máquina local.**

   Puedes clonar este repositorio usando el siguiente comando:

   ```bash
   git clone https://github.com/tu_usuario/LiterAlura-Challenge.git
   ```

2. **Asegúrate de tener Java 17 instalado.**

   Para comprobar si tienes Java 17 instalado, ejecuta el siguiente comando en tu terminal:

   ```bash
   java -version
   ```

   Si no tienes Java 17, puedes descargarlo e instalarlo desde el [sitio oficial de Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

3. **Configura la base de datos PostgreSQL según sea necesario.**

   Debes configurar la conexión a tu base de datos PostgreSQL en el archivo `src/main/resources/application.properties`. Abre este archivo y modifica las siguientes líneas con tus credenciales de base de datos:

   ```properties
   spring.application.name=LiterAlura-Challenge
   spring.datasource.url=jdbc:postgresql://${DB_HOST}/LiterAlura-Challenge
   spring.datasource.username=${DB_USER}
   spring.datasource.password=${DB_PASSWORD}
   spring.datasource.driver-class-name=org.postgresql.Driver
   hibernate.dialect=org.hibernate.dialect.HSQLDialect
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.format-sql=true
   ```

   - **${DB_HOST}**: Reemplázalo por el host de tu base de datos PostgreSQL. Si estás trabajando en tu máquina local, usa `localhost`.
   - **${DB_USER}**: Reemplázalo por el nombre de usuario de tu base de datos PostgreSQL.
   - **${DB_PASSWORD}**: Reemplázalo por la contraseña correspondiente al nombre de usuario de la base de datos.

   **Ejemplo**:

   ```properties
   spring.application.name=LiterAlura-Challenge
   spring.datasource.url=jdbc:postgresql://localhost/LiterAlura-Challenge
   spring.datasource.username=postgres
   spring.datasource.password=mi_contraseña
   spring.datasource.driver-class-name=org.postgresql.Driver
   hibernate.dialect=org.hibernate.dialect.HSQLDialect
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.format-sql=true
   ```

   **Pasos para configurar la base de datos PostgreSQL**:
   1. Instala PostgreSQL si aún no lo tienes. Puedes descargarlo desde [aquí](https://www.postgresql.org/download/).
   2. Crea una base de datos llamada `LiterAlura-Challenge` en tu servidor PostgreSQL:
      ```sql
      CREATE DATABASE "LiterAlura-Challenge";
      ```
   3. Asegúrate de que el usuario y la contraseña configurados en el archivo `application.properties` coincidan con los de tu base de datos.

4. **Ejecuta el proyecto con el siguiente comando:**

   Una vez que hayas configurado todo, puedes ejecutar la aplicación utilizando el siguiente comando de Maven:

   ```bash
   mvn spring-boot:run
   ```

   Esto iniciará la aplicación. Al ejecutar el programa, se mostrará un menú de opciones en la consola donde el usuario podrá interactuar y obtener información sobre libros y autores. Algunas de las opciones incluyen la búsqueda de libros por título, autor, idioma y la consulta sobre autores vivos.

## Estructura del Proyecto

- **`src/main/java/com/aluracursos/literaluraChallenge`**: Contiene las clases principales de la aplicación.
- **`src/main/resources/application.properties`**: Configuración de la base de datos y otras propiedades de la aplicación.
- **`src/test/java/com/aluracursos/literaluraChallenge`**: Pruebas unitarias (si aplicara).

5. Al ejecutar la aplicación, se mostrará un menú de opciones en la consola. El usuario podrá interactuar con las diferentes opciones que proporcionan información sobre libros y autores.

## Función de búsqueda de libros

La búsqueda de libros se realiza interactuando con una **API externa** (Gutendex) que permite obtener información detallada sobre libros disponibles de dominio público. La aplicación obtiene los datos de la API y los guarda en una base de datos local para su posterior consulta.

Para más información de la API Gutendex, visitar su sitio web oficial: https://gutendex.com/

### Ejemplo de interacción:

Al seleccionar la opción "1 - Buscar libro por su Título", el sistema pedirá al usuario que ingrese el título de un libro, buscará el libro a través de la API y mostrará el primer libro encontrado.

## Base de datos

La aplicación utiliza **PostgreSQL** para almacenar la información sobre los libros y autores. Las tablas de la base de datos almacenan los detalles de los libros y los autores, y se mantienen actualizadas con los resultados obtenidos de la API.

## Métodos Implementados

- **buscarLibroPorNombre**: Permite buscar un libro por su título.
- **buscarLibrosPorNombreAutor**: Permite buscar libros por el nombre del autor.
- **buscarLibrosPorIdiomas**: Permite consultar libros disponibles en uno o más idiomas.
- **buscarLibrosPorRangoDeVidaAutor**: Permite encontrar autores vivos en un determinado rango de años.
- **obtenerTop10LibrosDeApi**: Muestra los 10 libros más descargados.
- **obtenerLibrosBuscados**: Muestra los libros que se han buscado previamente.
- **obtenerAutoresBuscados**: Muestra los autores que se han buscado previamente.

## Conclusión

Este proyecto del catálogo de libros fue una excelente oportunidad para aplicar mis conocimientos de Java y Spring Boot, además de trabajar con APIs externas en tiempo real. La integración con la API de libros me permitió profundizar en el manejo de datos JSON y su conversión en objetos Java, a la vez que optimizaba el flujo de interacción con los usuarios a través de una interfaz de consola.

Implementar funcionalidades como la búsqueda de libros por título, autor, idioma y autores vivos en un rango de años fue un reto interesante que fortaleció mis habilidades en programación orientada a objetos y la manipulación de datos complejos. Además, la implementación de una lógica robusta para el manejo de errores y la validación de entradas me permitió ofrecer una experiencia más amigable y fluida al usuario.

El proyecto no solo mejoró mi comprensión de cómo interactuar con APIs y procesar datos en formato JSON, sino que también me permitió mejorar la estructura y modularidad del código en Java, enfocándome en la reutilización de componentes y optimizando la eficiencia del programa para ofrecer respuestas rápidas y útiles a los usuarios.
