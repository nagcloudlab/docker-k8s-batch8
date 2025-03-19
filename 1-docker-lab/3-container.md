### create a container
```bash
docker run -d -p 8080:8080 --name java-web-service java-web-service:v1
docker ps
docker container ls
```

### Overriding the default command
```bash
docker run ubuntu 
docker run -it ubuntu 
docker history ubuntu
docker run -it ubuntu sh
docker run -it ubuntu sleep 1000
```


### Run additional command(s) in a running container
```bash
docker run -it ubuntu sleep 1000
docker exec -it <container-id> bash
```


### Stop a container
```bash
docker stop <container-id>
docker ps
docker container ls
```

### Remove a container
```bash
docker rm <container-id>
docker rm -f <container-id>
```


### dittach & attach a container 
```bash
docker run -d -p 8080:8080 --name java-web-service java-web-service:v1
docker ps
docker attach <container-id>
```


### stop & re-start a container
```bash
docker run -d -p 8080:8080 --name java-web-service java-web-service:v1
docker ps
docker stop <container-id>
docker ps
docker start <container-id>
docker ps
```

### create container with a --rm flag ( remove container after it stops)
```bash
docker run -d -p 8080:8080 --rm --name java-web-service java-web-service:v1
docker ps
```

### create a container with a specific name
```bash
docker run -d -p 8080:8080 --name java-web-service java-web-service:v1
docker ps
```

### create a container with a specific hostname
```bash
docker run -d -p 8080:8080 --name java-web-service --hostname java-web-service java-web-service:v1
docker ps
```


