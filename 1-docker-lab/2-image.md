


### build an image from a Dockerfile ( e.g java-web-service)

```bash
cd services/java-web-service
touch Dockerfile
docker build -t java-web-service:v1 .
docker images
docker run -d -p 8080:8080 java-web-service:v1
docker ps
curl http://localhost:8080/hello
docker stop <container-id>
docker rm <container-id>
```


### inspect an image

```bash
docker inspect java-web-service:v1
docker history java-web-service:v1
```


### A tool for exploring each layer in a docker image

```bash
# https://github.com/wagoodman/dive
DIVE_VERSION=$(curl -sL "https://api.github.com/repos/wagoodman/dive/releases/latest" | grep '"tag_name":' | sed -E 's/.*"v([^"]+)".*/\1/')
curl -OL https://github.com/wagoodman/dive/releases/download/v${DIVE_VERSION}/dive_${DIVE_VERSION}_linux_amd64.deb
sudo apt install ./dive_${DIVE_VERSION}_linux_amd64.deb
dive java-web-service:v1
```

### scan an image for vulnerabilities
```bash
# later
```

### create private registry
```bash
docker run -d -p 5000:5000 registry:2
curl http://localhost:5000/v2/_catalog
```

### push an image to a private registry
```bash
# image_name=registry-url:port/repository:tag
docker tag java-web-service:v1 localhost:5000/java-web-service:v1
docker push localhost:5000/java-web-service:v1
docker rmi localhost:5000/java-web-service:v1
docker rmi java-web-service:v1
```

### pull an image from a private registry

```bash
docker pull localhost:5000/java-web-service:v1
```


### pull an image with a specific tag
```bash
docker pull localhost:5000/java-web-service:v1
```


### pull an image with a specific digest
```bash
docker pull localhost:5000/java-web-service@sha256:95d1511885c0eb5a18492b6c1fef122aa0ad60a6bc59f5746c5263d1a59b6a4b
```

### platform specific image
```bash
docker pull --platform linux/arm/v5 redis
```


### build an image with a specific platform
```bash
docker buildx create --use
docker buildx ls
docker buildx build --platform linux/amd64,linux/arm64 -t java-web-service:v1 .
```


### offcial images vs unofficial images
```bash
docker pull redis # official image
docker pull nagabhusanamn/java-web-service # unofficial image   
```


### Dockerfile Best Practices
Some best practices for writing Dockerfiles include:
Use official base images.
Minimize the number of layers by combining commands.
Use multi-stage builds to reduce image size.
Leverage .dockerignore to exclude unnecessary files.
Specify exact versions of dependencies.
Use ARG for dynamic values.
Use labels for metadata.
Use health checks to monitor container health.