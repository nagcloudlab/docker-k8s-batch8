
## Creating and Managing Volumes

```bash
docker volume
docker volume create my-vol1
docker volume ls
docker volume inspect my-vol1
docker volume create my-vol2
docker volume ls
docker volume rm my-vol1 my-vol2
```


## Working with Volumes

```bash
docker volume ls
docker run -dit --name alpine1 --mount source=my-vol1,target=/data alpine
docker volume ls
docker exec -it alpine1 sh
docker volume inspect data-vol
docker rm -f alpine1


docker run -dit --name nginx1 --mount source=data-vol,target=/data nginx
docker volume ls
docker exec -it nginx1 sh
ls /data
docker rm -f nginx1
docker volume rm data-vol
```

---

Ex1: create nginx container with host directory as volume for server-logs

```bash
sudo mkdir -p /var/log/nginx
sudo chown -R $USER:$(id -gn) /var/log/nginx
sudo chmod -R 755 /var/log/nginx

docker run -d \
  --name my-nginx \
  -p 80:80 \
  -v /var/log/nginx:/var/log/nginx \
  nginx

docker exec -it my-nginx bash
exit

ls -l /var/log/nginx

tail -f /var/log/nginx/access.log
tail -f /var/log/nginx/error.log



```
