
# NaNLABS Api Challenge
Developer Joaquin Gutierrez Carrillo

## Build and run application
Require Maven 3.6 or newer and jdk 19.
In windows you can set jdk manually
```bash
    set JAVA_HOME=C:\NanLABS\jdk-19.0.1
    set Path=%JAVA_HOME%\bin;%Path%
```
Add on Path enviroment variable maven bin folder
```bash
   Path = C:\apache-maven-3.8.6\bin 
```
Finally build and run the application
```bash
    mvn clean install && java -jar target/nanlabs-0.0.1-SNAPSHOT.jar
```
## Swagger
- [http://localhost:3000/swagger-ui/index.html](http://localhost:3000/swagger-ui/index.html)

## Docker
On Dockerfile folder run
```bash
docker build -t springio/gs-spring-boot-docker .
docker run --name NaNLABS_Api_Container -p 3000:3000 springio/gs-spring-boot-docker
```
