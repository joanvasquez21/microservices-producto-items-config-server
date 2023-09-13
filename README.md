# Centralizando la configuración con Sping cloud config server
Lectura de configuracion (@value), configuración git, utilizando git en  github remotamente, implementando @refreshscope y @actuator
El servidor de configuracion central(config server) facilita la centralizacion para sistemas distribuidos

![image](https://github.com/joanvasquez21/microservices-producto-items-config-server/assets/70104624/8c4da0a5-54a1-44b2-94a3-f50c97f73631)

- 1.- Agregamos la dependencia en el pom.xml
  ``` 
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-config-server</artifactId>
</dependency>
````
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
