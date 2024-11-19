# Literalura - Catálogo de Libros
Este proyecto consiste en un catálogo de libros, **Literalura** utilizando la API “Gutendex” para realizar solicitudes de libros, manipular datos JSON, guardarlos en una base de datos, filtrar y mostrar los libros y autores de interés.

### Menú de la Aplicación
![menu](https://github.com/user-attachments/assets/b6764cf9-8975-4745-8b8e-86c21d4f9f8e)

## Manejo de Excepciones
El programa incluye un robusto manejo de excepciones para garantizar que las entradas inválidas no detengan su funcionamiento:
1. **InputMismatchException en el método muestraElMenu**
    - Capturada en el menú principal cuando el usuario ingresa un valor no numérico.
    - El programa informa al usuario sobre el error y continúa operando.
2. **Validación de Idiomas:**
    - En el método `buscarLibroPorIdioma`, se valida que el usuario ingrese solo códigos de idioma permitidos (`es`, `en`, `fr`, `pt`).
    - Si el código es incorrecto, se le solicita intentarlo nuevamente.
3. **Verificación de Duplicados:**
    - En el método `buscarLibro`, se verifica si el libro ya existe en la base de datos antes de guardarlo.
    - Si el libro está registrado, el programa notifica al usuario y evita duplicados.

### Ejemplo de Ejecución con Entradas Inválidas
- Si un usuario intenta ingresar texto en lugar de un número en el menú principal o un número distinto a los que indica el menú.
Se lo notificará que elija una opción del 1 al 5 o 0 para salir.

## Configuración del Entorno

### Requisitos
Asegúrate de contar con los siguientes programas y versiones:

- **Java JDK**: versión 17 en adelante - [Descargar JDK](https://jdk.java.net/)
- **Maven**: versión 4 en adelante - [Descargar Maven](https://maven.apache.org/download.cgi)
- **Spring Boot**: versión 3.2.3 - [Spring Initializr](https://start.spring.io/)
- **PostgreSQL**: versión 16 en adelante - [Descargar PostgreSQL](https://www.postgresql.org/download/)
- **IDE**: IntelliJ IDEA (opcional)
- **Dependencias**:
    - Spring Data JPA
    - PostgreSQL Driver
    - Jackson (sugerido: versión 2.16)

### Configuración de Base de Datos
Para configurar la conexión a PostgreSQL, asegúrate de realizar las siguientes configuraciones en el archivo `application.properties` y establecer las variables de entorno de Windows necesarias.
1. Abre el archivo `src/main/resources/application.properties`.
2. Configura las siguientes propiedades:
    ```properties
    spring.application.name=literalura
    spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}
    spring.datasource.username=${DB_USER}
    spring.datasource.password=${DB_PASSWORD}
    spring.datasource.driver-class-name=org.postgresql.Driver
    hibernate.dialect=org.hibernate.dialect.HSQLDialect
    ```
3. Configura las siguientes variables de entorno en tu sistema:
    - **DB_HOST**: dirección de la base de datos (por ejemplo, `localhost`)
    - **DB_NAME**: nombre de la base de datos
    - **DB_USER**: usuario de la base de datos
    - **DB_PASSWORD**: tu contraseña de usuario

### Instalación
Sigue estos pasos para descargar y ejecutar el proyecto:
1. Clona el repositorio: `git clone https://github.com/NoeliaOrsini/literalura.git`
2. Instala las dependencias: Asegúrate de tener Maven configurado y ejecuta:
   ```bash
   mvn install
3. Configura la base de datos PostgreSQL: Crea una base de datos en PostgreSQL (alura_literalura) y ajusta las variables de entorno mencionadas anteriormente.
4. Ejecuta la aplicación: Desde LiteraluraApplication, podrás correr el programa usando el comando run.

## Funcionalidades del Proyecto

- **Buscar libro por título**: Permite buscar un libro específico mediante su título en la API.
- **Listado de libros registrados**: Muestra todos los libros almacenados en la base de datos.
- **Listado de autores registrados**: Muestra todos los autores guardados en la base de datos.
- **Buscar autores vivos en un año específico**: Filtra autores que estaban vivos en el año especificado.
- **Buscar libro por idioma**: Permite buscar libros según el idioma (inglés, portugués, español y francés).
- **Salir**: Finaliza la aplicación.
  
![listado libros](https://github.com/user-attachments/assets/d941cdcd-a027-4436-9303-89f63d2917f2)

![listado autor](https://github.com/user-attachments/assets/38822e2b-ff8f-47b7-a7b6-2d8a60a7da69)

![autore vivos](https://github.com/user-attachments/assets/a08aee9e-a435-4f45-9d50-54a176b9cee2)

![libros en ingles](https://github.com/user-attachments/assets/d8122d3a-bc49-4824-b720-45b17eb1806a)


## Te invito a mirar su funcionamiento en el siguiente video:
https://www.youtube.com/watch?v=EM2YmJTNYgA

Realizado por Noelia Orsini para Alura Latam
