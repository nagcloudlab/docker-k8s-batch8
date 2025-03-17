### create a container

```bash
docker run ubuntu:20.04
docker container ls
docker container ls -a
docker run -it ubuntu:20.04
docker container ls
```

### Overriding the default command

```bash
docker history ubuntu:20.04
docker run -it ubuntu:20.04 /bin/sh
```

### Running a container in the background & naming a container

```bash
docker rm my-ubuntu
docker run --name my-ubuntu -d ubuntu:20.04 sleep 1000
docker attach my-ubuntu
```

### Run additional command in a running container

```bash
docker exec -it my-ubuntu /bin/bash
ps aux
```

### Stop a container

```bash
docker stop my-ubuntu
docker container ls
docker container ls -a
```

### Stop all running containers

```bash
docker container ls
docker container ls -q
docker stop $(docker container ls -q)
docker container ls
```

### Start a container

```bash
docker start my-ubuntu
docker container ls
```

### Remove a container

```bash
docker rm my-ubuntu
docker stop $(docker container ls -q)
docker container ls
docker container ls -a
```

### Remove all stopped containers

```bash
docker container prune
docker container ls -a
```

### Create & Start a container in two steps

```bash
docker create --name my-ubuntu -it ubuntu:20.04
docker container ls -a
docker start my-ubuntu
```

### attach to a running container

```bash
docker attach my-ubuntu
```

### Inspect a container

```bash
docker inspect my-ubuntu
```

### Create a container with a environment variable

```bash
docker rm my-ubuntu
docker run --name my-ubuntu -e MY_VAR=Hello -it ubuntu:20.04
echo $MY_VAR
exit
docker rm my-ubuntu
```

### Remove container if stopped

```bash
docker run --rm -it ubuntu:20.04
exit
docker container ls -a
```



### Create a container resource limits

```bash

# Memory limits - 200 MB of memory
docker run --name my-limited-memory -m 200m -it ubuntu:20.04
# Memory reservation - 100 MB of memory is guaranteed
docker run --name my-memory-reservation --memory-reservation=100m -it ubuntu:20.04



# CPU limits - 0.5 CPU cores (50% of one CPU core)
docker run --name my-limited-cpu --cpus="0.5" -it ubuntu:20.04
# CPU shares - 256 shares (default is 1024)
docker run --name my-cpu-shares -c 256 -it ubuntu:20.04
# CPU quota - 10000 microseconds every 50000 microseconds
docker run --name my-cpu-quota --cpu-quota=10000 --cpu-period=50000 -it ubuntu:20.04


# Block IO limits - 1 MB/s read limit on /dev/sda
docker run --name my-limited-io --device-read-bps /dev/sda:1mb -it ubuntu:20.04


# Limit the number of processes - 100 processes
docker run --name my-limited-pids --pids-limit=100 -it ubuntu:20.04


# Limit the swap memory - 300 MB of swap memory
docker run --name my-limited-swap --memory-swap=300m -it ubuntu:20.04


# Limit the kernel memory - 100 MB of kernel memory
docker run --name my-limited-kernel --kernel-memory=100m -it ubuntu:20.04


docker run --name my-limited-container \
  -m 200m \
  --memory-reservation=100m \
  --cpus="0.5" \
  -c 256 \
  --cpu-quota=10000 \
  --cpu-period=50000 \
  --device-read-bps /dev/sda:1mb \
  --device-write-bps /dev/sda:1mb \
  --pids-limit=100 \
  --memory-swap=300m \
  --kernel-memory=50m \
  -it ubuntu:20.04


docker stats my-limited-container

```

### Docker Security

Security best practices include:

- Running containers with the least privilege.
- Using trusted base images.
- Regularly scanning images for vulnerabilities.
- Limiting container resources.
- Using Docker Content Trust (DCT) to sign and verify images.
