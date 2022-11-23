
# NaNLABS Api Challenge
Developer: Joaquin Gutierrez Carrillo - [@joacocjs](https://www.github.com/joacocjs)

## Build application
Require Maven 3.6 or earlier and jdk 19
```bash
    mvn clean install
```
## Docker
On Dockerfile folder run
```bash
docker build -t springio/gs-spring-boot-docker .
docker run --name NaNLABS_Api_Container -p 3000:3000 springio/gs-spring-boot-docker
```


