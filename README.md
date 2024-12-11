 Proyecto: MS Franquicias

Este proyecto es una aplicación backend desarrollada con Spring Boot que utiliza una base de datos MySQL configurada a través de Docker. A continuación, se detallan los pasos necesarios para clonar, configurar y ejecutar el proyecto.

---

## **Requisitos previos**

### **Software necesario**
- **Git**: Para clonar el repositorio. [Descargar Git](https://git-scm.com/).
- **Java 17**: Para ejecutar la aplicación. [Descargar Java](https://adoptium.net/).
- **Maven**: Para compilar y construir el proyecto. [Descargar Maven](https://maven.apache.org/).
- **Docker**: Para configurar y ejecutar la base de datos. [Descargar Docker](https://www.docker.com/).

### **Configurar variables de entorno**
- Asegúrate de que `java` y `mvn` están disponibles desde la línea de comandos.
- Comprueba la instalación de Docker ejecutando:
  ```bash
  docker --version
  ```

---

## **Configuración del proyecto**

### **1. Clonar el repositorio**
1. Clona este repositorio con el siguiente comando:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   ```
2. Navega al directorio del proyecto:
   ```bash
   cd <nombre_del_proyecto>
   ```



### **3. Configurar la aplicación**
1. Verifica que el archivo `application.yml` esté configurado correctamente en el directorio `src/main/resources`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3307/franquicias
       username: root
       password: root
     jpa:
       hibernate:
         ddl-auto: update
       database-platform: org.hibernate.dialect.MySQL8Dialect
   ```

2. Compila el proyecto con Maven:
   ```bash
   mvn clean install
   ```

3. Ejecuta la aplicación:
   ```bash
   java -jar target/ms-franquicias-0.0.1-SNAPSHOT.jar
   ```

---

## **Uso del proyecto**

### **1. Endpoints principales**
Prueba los endpoints utilizando herramientas como Postman o cURL. Por ejemplo:
```bash
AGREGAR NUEVA FRANQUICIA -> GET http://localhost:8090/api/franquicia/create -> {"nombre": "Café Aroma"}
AGREGAR NUEVA SUCURSAL -> POST http://localhost:8090/api/sucursal/create -> {"nombre": "Sucursal Villarica","franquicia": {"idFranquicia": 3}}
AGREGAR NUEVO PRODUCTO -> POST http://localhost:8090/api/producto/create -> {"nombre": "Cafe 1 libra","stock": 600,"sucursal":{"idSucursal": 13}}
ELIMINAR PRODUCTO DE UNA SUCURSAL -> DELETE http://localhost:8090/api/sucursal/{idSucursal}/productos/{idProducto}
MODIFICAR STOCK DE UN PRODUCTO -> PATCH http://localhost:8090/api/producto/9/stock -> {"stock": 99}
PRODUCTO CON MAS STOCK POR SUCURSAL -> GET http://localhost:8090/api/producto/productos-con-mas-stock/{idFranquicia}

### **1. Endpoints Secundarios (Puntos extras)**
ACTUALIZAR NOMBRE FRANQUICIA -> PUT http://localhost:8090/api/franquicia/{idFranquicia} -> {"nombre": "Nuevo Nombre de la Franquicia"}
ACTUALIZAR NOMBRE SUCURSAL -> PUT http://localhost:8090/api/sucursal/{idSucursal} -> {"nombre": "Nuevo Nombre de la Sucursal"}
ACTUALIZAR NOMBRE PRODUCTO -> PUT http://localhost:8090/api/producto/{idProducto} -> {"nombre": "Nuevo Nombre de la Producto"}
```

---


---

## **Notas adicionales**
- Si necesitas cargar datos iniciales en la base de datos, utiliza un archivo `data.sql` en `src/main/resources`.
- Asegúrate de exponer el puerto 8080 para la aplicación y el puerto 3307 para la base de datos.

---

¡Listo! Ahora deberías poder clonar, configurar y ejecutar el proyecto sin problemas.
