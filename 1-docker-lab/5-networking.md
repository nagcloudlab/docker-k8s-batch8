

### Bridge Network Driver
```bash
docker network create --driver bridge bridge1
docker network ls

docker run --network bridge1 --name c1 -it nicolaka/netshoot /bin/bash 

docker run --network bridge1 --name c2 -it nicolaka/netshoot /bin/bash 
```

