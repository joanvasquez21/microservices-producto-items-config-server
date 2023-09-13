# Centralizando la configuración con Sping cloud config server
Lectura de configuracion (@value), configuración git, utilizando git en  github remotamente, implementando @refreshscope y @actuator
El servidor de configuracion central(config server) facilita la centralizacion para sistemas distribuidos
Antes de que el microservicio se registre en eureka consultara en el servidor de configuracion todos sus propiedades y luego de obtener arrancara en eureka
![image](https://github.com/joanvasquez21/microservices-producto-items-config-server/assets/70104624/8c4da0a5-54a1-44b2-94a3-f50c97f73631)

- 1.- Agregamos la dependencia en el pom.xml
```
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```
- 2.- Agregamos la anotacion @EnableConfigServer en la clase principal 
![image](https://github.com/joanvasquez21/microservices-producto-items-config-server/assets/70104624/02092228-d1ae-4cf9-b6a1-a92b0a8df9f1)
- 3.- En el archivo application.properties, agregamos estos parametros, utilizamos localmente y luego utilizamos un servidor git
![image](https://github.com/joanvasquez21/microservices-producto-items-config-server/assets/70104624/15ec1b78-1a8d-4569-9cef-f291d21d90c8)
- 4.- En nuestro cliente agregamos esta dependencia
```
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

- 5.- Creamos un archivo donde guardaremos nuestras configuraciones
![image](https://github.com/joanvasquez21/microservices-producto-items-config-server/assets/70104624/2bbc87f5-f912-4d36-a5ad-88dbbcd9bfe1)
- 6.- Vemos las configuraciones
- ![image](https://github.com/joanvasquez21/microservices-producto-items-config-server/assets/70104624/408843df-6ad3-47c9-9713-cf53fab68543)

- 7.- En el cliente añadimos el archivo boostrap.properties y agregamos estas configuracion, primero correra boostrap sobreescribiendo sobre application.properties
- ![image](https://github.com/joanvasquez21/microservices-producto-items-config-server/assets/70104624/952cb459-4c66-4fc2-b0b7-f35246535f03)
- 8.- Creamos el metodo en el cliente para leer en postman la configuracion
 ![image](https://github.com/joanvasquez21/microservices-producto-items-config-server/assets/70104624/f549bfdd-5bc7-42f7-950c-7edaaa39cc93)

- 9.- Usamos @refreshscope para actualizar los componentes que le estamos inyectando con las configuracion sin necesidad de reiniciar la aplicacion agregamos para eso la dependencia de actuator
- 10 Creamos repositorio github para agregar las configs
- ![image](https://github.com/joanvasquez21/microservices-producto-items-config-server/assets/70104624/335d8579-32f6-4f42-9626-91968a465fdf)
