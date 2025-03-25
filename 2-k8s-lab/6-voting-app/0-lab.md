

--------------------------------------------------------------------------
### Pod with basic-networking ( using ClusterIP, NodePort)
--------------------------------------------------------------------------

```bash
kubectl apply -f voting-app-v1.yaml
kubectl get pods
kubectl get svc

kubectl port-forward --address 0.0.0.0 svc/vote 30002:80
kubectl port-forward --address 0.0.0.0 svc/result 30004:80

kubectl delete -f voting-app-v1.yaml
```

--------------------------------------------------------------------------
### Pod scheduling with, nodeAffinity, nodeAntiAffinity, podAffinity, podAntiAffinity, & resource limits
--------------------------------------------------------------------------

```bash
kubectl get nodes
kubectl label node my-k8s-cluster-worker role=db
kubectl apply -f voting-app-v2.yaml
kubectl get pods -o wide
kubectl delete -f voting-app-v2.yaml
```



--------------------------------------------------------------------------
### Pod with storage (emptyDir, hostPath, nfs, ceph, minio)
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

# ✅ 4️⃣ Verify NFS access from Kind (optional check):

docker ps  # Get Kind node container ID
docker exec -it 08022b1ee957 bash
apt update && apt install -y nfs-common
showmount -e 192.168.0.107

# ✅ 5️⃣ Apply Kubernetes PV and PVC:
kubectl apply -f db-pv.yaml
kubectl apply -f db-pvc.yaml


# ✅ 6️⃣ Update and apply your Postgres Deployment with the PVC:
kubectl apply -f voting-app-v4.yaml

# ✅ 7️⃣ Verify everything is working:

kubectl get pv
kubectl get pvc
kubectl get pods
kubectl describe pvc postgres-pvc
kubectl describe pod <postgres-pod-name>

# ✅ 8️⃣ Check data persistence:
sudo ls /data/nfs/postgres

# ✅ 9️⃣ Cleanup:

kubectl delete -f voting-app-v4.yaml
kubectl delete -f db-pvc.yaml
kubectl delete -f db-pv.yaml

```

--------------------------------------------------------------------------
### dynamic PV provisioning with StorageClass
--------------------------------------------------------------------------


```bash

# -----------------------------------------
# 2️⃣ Verify NFS server export on the host (already configured)
# -----------------------------------------
sudo exportfs -rav
sudo exportfs -v
# Ensure output shows:
/data/nfs/postgres *(rw,sync,no_subtree_check,no_root_squash)

# -----------------------------------------
# 3️⃣ Ensure permissions on NFS export directory
# -----------------------------------------
sudo chmod -R 777 /data/nfs/postgres
sudo chown -R nobody:nogroup /data/nfs/postgres
sudo systemctl restart nfs-kernel-server

# -----------------------------------------
# 4️⃣ Reinstall the NFS Subdir External Provisioner with correct settings
# -----------------------------------------
helm repo add nfs-subdir-external-provisioner https://kubernetes-sigs.github.io/nfs-subdir-external-provisioner/
helm repo update

helm install nfs-client nfs-subdir-external-provisioner/nfs-subdir-external-provisioner \
  --set nfs.server=192.168.0.107 \
  --set nfs.path=/data/nfs/postgres \
  --set storageClass.name=nfs-storage \
  --set storageClass.defaultClass=true

# -----------------------------------------
# 5️⃣ Verify the provisioner pod is running
# -----------------------------------------
kubectl get pods -l app=nfs-subdir-external-provisioner
kubectl logs -l app=nfs-subdir-external-provisioner
# -----------------------------------------
# 6️⃣ Recreate the PVC for Postgres with the dynamic StorageClass
# -----------------------------------------
cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: postgres-dynamic-pvc
spec:
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 10Gi
  storageClassName: nfs-storage
EOF


kubectl apply -f db-pvc-dynamic.yaml

# -----------------------------------------
# 7️⃣ Verify the PVC and PV are Bound
# -----------------------------------------
kubectl get pvc
kubectl get pv
kubectl describe pvc postgres-dynamic-pvc

# -----------------------------------------
# 8️⃣ Update your Postgres Deployment (if not already) to use the dynamic PVC
# -----------------------------------------
# In the volumes section:
# volumes:
#   - name: postgres-storage
#     persistentVolumeClaim:
#       claimName: postgres-dynamic-pvc
# And mount:
# volumeMounts:
#   - name: postgres-storage
#     mountPath: /var/lib/postgresql/data

kubectl apply -f voting-app-v5.yaml

# -----------------------------------------
# 9️⃣ Final check that Postgres pod is running and writing to the NFS volume
# -----------------------------------------
kubectl get pods


# ✅ 2️⃣ Check the StorageClass:
kubectl get sc

# ✅ 6️⃣ Check data persistence:
sudo ls /data/nfs/postgres

# ✅ 7️⃣ Cleanup:
kubectl delete -f voting-app-v5.yaml
kubectl delete -f db-pvc-dynamic.yaml
helm uninstall nfs-client
```


--------------------------------------------------------------------------
### ConfigMap and Secret
--------------------------------------------------------------------------


```bash
kubectl apply -f voting-app-v6.yaml
kubectl get pods
kubectl delete -f voting-app-v6.yaml
```

--------------------------------------------------------------------------
### Pod with Liveness and Readiness Probe
--------------------------------------------------------------------------
```bash
kubectl apply -f voting-app-v7.yaml
kubectl get pods
kubectl delete -f voting-app-v7.yaml
```


--------------------------------------------------------------------------
### Loadbalancer Service ( cloud provider specific)
--------------------------------------------------------------------------


```bash
kubectl apply -f voting-app-v8.yaml
kubectl get pods -w
kubectl delete -f voting-app-v8.yaml
```


--------------------------------------------------------------------------
### Ingress Service  ( ingress-nginx) / ( ingrress-traefik)
--------------------------------------------------------------------------


on kind k8s cluster,

```bash
kubectl apply -f https://kind.sigs.k8s.io/examples/ingress/deploy-ingress-nginx.yaml
kubectl wait --namespace ingress-nginx \
  --for=condition=ready pod \
  --selector=app.kubernetes.io/component=controller \
  --timeout=90s

kubectl apply -f voting-app-v9.yaml
kubectl get pods

curl -H "Host: vote.local" http://172.18.0.5:32301
curl -H "Host: result.local" http://172.18.0.5:32301

sudo nano /etc/hosts
# add below line
172.1.0.3 vote.local result.local

http://vote.local:32301
http://result.local:32301

#  ( for Mac users)
kubectl port-forward --address 0.0.0.0 -n ingress-nginx svc/ingress-nginx-controller 8080:80


kubectl delete -f voting-app-v9.yaml

```


--------------------------------------------------------------------------
### Network policies with calico-CNI / cilium-CNI
--------------------------------------------------------------------------


### Install Calico CNI on Kind

```bash
kubectl delete daemonsets -n kube-system kindnet
kubectl apply -f https://docs.projectcalico.org/manifests/calico.yaml
kubectl get pods -n kube-system
```

### Apply Network Policy

```bash
kubectl apply -f voting-app-v10.yaml
kubectl get pods
kubectl get svc
kubectl get networkpolicies

kubectl exec -it $(kubectl get pod -l app=vote -o jsonpath="{.items[0].metadata.name}") -- sh
nc -zv redis 6379
nc -zv db 5432


kubectl delete -f voting-app-v10.yaml
```


--------------------------------------------------------------------------
###  Networking with Service Mesh (Istio)
--------------------------------------------------------------------------


🚀 Why Use Istio with Calico?

Istio provides traffic management, security, and observability at the application layer (L7).
Calico provides network security policies and network routing at the network layer (L3/L4).
Together, they enable fine-grained security, service-to-service encryption, and observability.



Install Istio on Kind
```bash
# Download and install Istio
curl -L https://istio.io/downloadIstio | sh -
cd istio-*
export PATH=$PWD/bin:$PATH

# Install Istio with default profile
istioctl install --set profile=demo -y

# Verify Istio installation
kubectl get pods -n istio-system

# Enable automatic sidecar injection
kubectl label namespace default istio-injection=enabled --overwrite

# Deploy Voting App
cd ..
kubectl apply -f voting-app-v11.yaml

# Verify Voting App
kubectl get pods
kubectl get svc
kubectl get gateway
kubectl get virtualservice
kubectl get svc istio-ingressgateway -n istio-system
kubectl get nodes -o wide

echo "172.18.0.5 vote.local result.local" | sudo tee -a /etc/hosts
curl -v -H "Host: vote.local" http://172.18.0.5:32497
curl -v -H "Host: result.local" http://172.18.0.5:32497

# 🔥 Final Checks
kubectl logs -l istio=ingressgateway -n istio-system
kubectl get pods -o jsonpath='{.items[*].spec.containers[*].name}' | grep istio-proxy || echo "Sidecar not injected"


# istio add-ons
kubectl apply -f istio-1.25.0/samples/addons/kiali.yaml
kubectl apply -f istio-1.25.0/samples/addons/prometheus.yaml
kubectl apply -f istio-1.25.0/samples/addons/grafana.yaml

# Verify Add-ons
kubectl get pods -n istio-system

# Access Kiali Dashboard
istioctl dashboard kiali

kubectl apply -f voting-app-v11.yaml
kubectl get pods -w


kubectl get svc -n istio-system
for i in {1..100000}; do curl -H "Host: vote.local" http://172.18.0.5:32497; done


# Access Prometheus Dashboard
istioctl dashboard prometheus

# Access Grafana Dashboard
istioctl dashboard grafana

# Cleanup
kubectl delete -f voting-app-v11.yaml


```


--------------------------------------------------------------------------
###  Traffic Splitting with Istio
--------------------------------------------------------------------------


```bash
kubectl apply -f voting-app-v12.yaml
kubectl get pods
kubectl get svc
kubectl get gateway 


# Access Kiali Dashboard
istioctl dashboard kiali

kubectl get svc -n istio-system
for i in {1..10000}; do curl -H "Host: vote.local" http://172.18.0.5:32497; done

kubectl delete -f voting-app-v12.yaml
```


--------------------------------------------------------------------------