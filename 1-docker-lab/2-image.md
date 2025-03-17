### pull & inspect image:

```bash
docker images
docker image ls

# image name format: [registry-hostname[:port]/]repository[:tag]
docker pull redis
docker pull docker.io/redis:latest


docker inspect redis
docker inspect node-web-service:v10
docker history node-web-service:v10
```


A tool for exploring each layer in a docker image:
https://github.com/wagoodman/dive

```bash
DIVE_VERSION=$(curl -sL "https://api.github.com/repos/wagoodman/dive/releases/latest" | grep '"tag_name":' | sed -E 's/.*"v([^"]+)".*/\1/')
curl -OL https://github.com/wagoodman/dive/releases/download/v${DIVE_VERSION}/dive_${DIVE_VERSION}_linux_amd64.deb
sudo apt install ./dive_${DIVE_VERSION}_linux_amd64.deb
```

```bash
dive node-web-service:v10
```


### pull an image using a specific tag:

```bash
docker pull redis:6.0.9
docker image ls
```

### pull an image using a specific digest:

```bash
docker pull redis@sha256:48c1431bed43fb2645314e4a22d6ca03cf36c5541d034de6a4f3330e7174915b
docker image ls
```

### pull un-official image:

```bash
docker pull docker.io/nagabhushanamn/greeting-service:v1
docker image ls
```

### tag an image:

```bash
docker tag nagabhushanamn/greeting-service:v1 nagabhushanamn/greeting-service:tng
docker image ls
```


### demo-1: build a java-web-service image

```bash
cd services/java-web-service
docker build -t java-web-service:v1 .
docker image ls
docker run -d -p 8080:8080 -e SPRING_PROFILES_ACTIVE=stage java-web-service:v1
curl http://localhost:8080/api/info
```

### demo-2: build a python-web-service image

```bash
cd services/python-web-service
docker build --build-arg FILE_NAME=app.py -t python-web-service:v1 .
docker image ls
docker run -d -p 8080:8080  python-web-service:v1
curl http://localhost:8080/api/info
```

### build an image for multi-architecture:

```bash
docker buildx build --platform linux/amd64,linux/arm64 -t java-web-service:v11 .
docker image ls
docker inspect greeting-service:v1
```

### pull an imgae with a specific architecture:

```bash
docker pull --platform linux/arm64 redis
docker image ls
```

### setup a private registry:

```bash
docker run -d -p 5000:5000 --name registry registry:2
docker ps
curl http://localhost:5000/v2/_catalog
```

### tag an image and push it to the private registry:

```bash
docker tag java-web-service:v1 localhost:5000/java-web-service:v1
docker image ls
docker push localhost:5000/java-web-service:v1
```

### pull an image from the private registry:

```bash
docker pull localhost:5000/java-web-service:v1
docker image ls
```

### Dockerfile Best Practices

Some best practices for writing Dockerfiles include:

- Use official base images.
- Minimize the number of layers by combining commands.
- Use multi-stage builds to reduce image size.
- Leverage .dockerignore to exclude unnecessary files.
- Specify exact versions of dependencies.
- Use COPY instead of ADD.
- Use ARG for dynamic values.
- Use labels for metadata.
- Use health checks to monitor container health.
- Use .dockerignore to exclude unnecessary files.
