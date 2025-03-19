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



### resource limits ( CPU )

```bash
# way-1 : % of CPU
docker run -d -p 8080:8080 --name java-web-service --cpus 0.5 java-web-service:v1
# way-2 : shares
docker run -d -p 8080:8080 --name java-web-service --cpu-shares 512 java-web-service:v1
# way-3 : period & quota
docker run -d -p 8080:8080 --name java-web-service --cpu-period 100000 --cpu-quota 50000 java-web-service:v1
```

### resource limits ( Memory )

```bash

# - way-1 : memory limit
docker run -d -p 8080:8080 --name java-web-service --memory 512m java-web-service:v1
# - way-2 : memory reservation
docker run -d -p 8080:8080 --name java-web-service --memory-reservation 256m java-web-service:v1
# - way-3 : memory swap limit
docker run -d -p 8080:8080 --name java-web-service --memory 512m --memory-swap 1g java-web-service:v1
# - way-4 : disable swap
docker run -d -p 8080:8080 --name java-web-service --memory 512m --memory-swap -1 java-web-service:v1
# - way-5 : memory limit & swap limit
docker run -d -p 8080:8080 --name java-web-service --memory 512m --memory-swap 512m java-web-service:v1

# - way-6 : memory limit & kernel memory limit
docker run -d -p 8080:8080 --name java-web-service --memory 512m --kernel-memory 256m java-web-service:v1

```
### start a container with a 2 cpu cores and 512m memory
```bash
docker run -d -p 8080:8080 --name java-web-service --cpus 2 --memory 512m java-web-service:v1
docker ps
```


### monitoring a container
```bash
docker run -d -p 8080:8080 --name java-web-service --cpus 2 --memory 512m java-web-service:v1
docker stats <container-id>
```