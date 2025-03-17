### delete all containers:

```bash
docker ps -a
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker ps -a
```

### delete all images:

```bash
docker image ls
docker image rm -f $(docker image ls -q)
docker image ls
```

### delete all volumes:

```bash
docker volume ls
docker volume rm $(docker volume ls -q)
docker volume ls
```

### delete all networks:

```bash
docker network ls
docker network rm $(docker network ls -q)
docker network ls
```
