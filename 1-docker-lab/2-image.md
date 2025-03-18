


### build an image from a Dockerfile ( e.g java-web-service)

```bash
cd services/java-web-service
docker build -t java-web-service:v1 .
docker images
docker run -d -p 8080:8080 java-web-service:v1
docker ps
curl http://localhost:8080/hello
```
