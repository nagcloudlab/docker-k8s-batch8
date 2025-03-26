


--------------------------------------------------------------------------
### v1 - Pods with basic-networking ( using ClusterIP, NodePort)
--------------------------------------------------------------------------

```bash
kubectl apply -f voting-app-v1.yaml
kubectl get pods
kubectl get svc

kubectl port-forward --address 0.0.0.0 svc/vote 30001:80
kubectl port-forward --address 0.0.0.0 svc/result 30002:80

kubectl delete -f voting-app-v1.yaml
```

--------------------------------------------------------------------------
### v2 - scheduling pods on specific nodes
--------------------------------------------------------------------------

```bash
kubectl label node kind-cluster-worker role=db
kubectl apply -f voting-app-v2.yaml
kubectl get pods -o wide
kubectl get svc

kubectl delete -f voting-app-v2.yaml
```

--------------------------------------------------------------------------
### v3 - Pods with persistent storage
--------------------------------------------------------------------------

```bash

# ✅ 1️⃣ Install NFS Server on your host (Ubuntu/Debian):

sudo apt update
sudo apt install -y nfs-kernel-server

# ✅ 2️⃣ Create and configure the NFS export directory:

sudo mkdir -p /data/nfs/postgres
sudo chown -R nobody:nogroup /data/nfs/postgres
sudo chmod -R 777 /data/nfs/postgres

# ✅ 3️⃣ Export the directory in /etc/exports:

sudo nano /etc/exports
# add below line
/data/nfs/postgres *(rw,sync,no_subtree_check,no_root_squash)

sudo exportfs -rav
sudo systemctl restart nfs-kernel-server

sudo exportfs -v

# ✅ 4️⃣ Install NFS client on your k8s nodes where db pods will be scheduled:

docker ps
docker exec -it 56231fa31f28 /bin/bash
apt update
apt install -y nfs-common
apt update && apt install -y nfs-common
showmount -e 10.0.0.4


# ✅ 5️⃣ Create a PersistentVolume and PersistentVolumeClaim:

kubectl apply -f voting-app-v3.yaml

kubectl get pv
kubectl get pvc

kubectl get pods -o wide
kubectl get svc

kubectl delete -f voting-app-v3.yaml
```

--------------------------------------------------------------------------
### v4 - Pods with ConfigMap and Secrets
--------------------------------------------------------------------------



