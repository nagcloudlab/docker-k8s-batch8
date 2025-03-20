

### Bridge Network Driver
```bash
docker network create --driver bridge bridge1
docker network ls

docker run --network bridge1 --name c1 -it nicolaka/netshoot /bin/bash 

docker run --network bridge1 --name c2 -it nicolaka/netshoot /bin/bash 
```


### Exercice-1: deploy voting-app containers on the bridge network
```bash
# Create a bridge network
docker network create --driver bridge voteapp
# Create a volume for the database
docker volume create pg_data
# Create a db container
docker run -d --name db --network voteapp -e POSTGRES_HOST_AUTH_METHOD=trust -e POSTGRES_PASSWORD=postgres -v pg_data:/var/lib/postgresql/data postgres:15
# Create a redis container
docker run -d --name redis --network voteapp redis:alpine
# Create a worker container
docker run -d --name worker --network voteapp docker/example-voting-app-worker
# Create a vote container
docker run -d --name vote --network voteapp -p 5000:80 docker/example-voting-app-vote
# Create a result container
docker run -d --name result --network voteapp -p 5001:80 docker/example-voting-app-result
```



### Exercice-2: deploy voting-app containers on the bridge network
```bash
# Create a bridge network
docker network create --driver bridge voteapp
# Create a volume for the database
docker volume create pg_data
# Create a db container
docker run -d --name db --network voteapp -e POSTGRES_HOST_AUTH_METHOD=trust -e POSTGRES_PASSWORD=postgres -v pg_data:/var/lib/postgresql/data postgres:15
# Create a redis container
docker run -d --name redis --network voteapp redis:alpine
# Create a worker container
docker run -d --name worker --network voteapp docker/example-voting-app-worker
# Create a vote container
docker run -d --name vote --network voteapp docker/example-voting-app-vote
# Create a result container
docker run -d --name result --network voteapp docker/example-voting-app-result
# Create a nginx container with volumes
docker run -d --name nginx --network voteapp -p 80:80 -v $(pwd)/nginx.conf:/etc/nginx/nginx.conf:ro nginx:alpine
```