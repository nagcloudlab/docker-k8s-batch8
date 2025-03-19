


### host path volume
  
  ```bash
  docker run -d -p 8080:8080 -v /host/path:/app/log java-web-service:v1
  ```
  
### docker managed volume
  
  ```bash
  docker volume create my-vol
  docker run -d -p 8080:8080 -v my-vol:/app/log java-web-service:v1
  ```


### remote storage volume  ( e.g. NFS )
  
  ```bash

# ✅ 1️⃣ Install NFS Server on your host (Ubuntu/Debian):

sudo apt update
sudo apt install -y nfs-kernel-server

# ✅ 2️⃣ Create and configure the NFS export directory:

sudo mkdir -p /npci/nfs/jws/log
sudo chown -R nobody:nogroup /npci/nfs/jws/log
sudo chmod -R 777 /npci/nfs/jws/log

# ✅ 3️⃣ Export the directory in /etc/exports:

sudo nano /etc/exports
# add below line
/npci/nfs/jws/log *(rw,sync,no_subtree_check,no_root_squash)

sudo exportfs -rav
sudo systemctl restart nfs-kernel-server

sudo exportfs -v

# ✅ 4️⃣ Install NFS Client on your host (Ubuntu/Debian):

sudo apt update
sudo apt install -y nfs-common
# create a volume with NFS
docker volume create \
  --driver local \
  --opt type=nfs \
  --opt o=addr=10.0.0.4,rw \
  --opt device=:/npci/nfs/jws/log \
  nfs-vol
  ```




  ### deploy postgresql with anonymous volume
  
  ```bash
  docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres:15
  ```

  ### deploy postgresql with named volume
  
  ```bash
  docker volume create pgdata
  docker run -d -p 5432:5432 -v pgdata:/var/lib/postgresql/data -e POSTGRES_PASSWORD=postgres postgres:15
  ```