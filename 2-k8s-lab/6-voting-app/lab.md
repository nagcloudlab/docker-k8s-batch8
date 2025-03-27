


--------------------------------------------------------------------------
### v1 - Pods creation + basic-networking ( using ClusterIP, NodePort)
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

sudo mkdir -p /nfs/data/postgres
sudo chown -R nobody:nogroup /nfs/data/postgres
sudo chmod -R 777 /nfs/data/postgres

# ✅ 3️⃣ Export the directory in /etc/exports:

sudo nano /etc/exports
# add below line
/nfs/data/postgres *(rw,sync,no_subtree_check,no_root_squash)

sudo exportfs -rav
sudo systemctl restart nfs-kernel-server

sudo exportfs -v

# ✅ 4️⃣ Install NFS client on your k8s nodes where db pods will be scheduled:

docker ps
docker exec -it 66d5ca72f5c3 /bin/bash
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


```bash
kubectl apply -f voting-app-v4.yaml
kubectl get pods
kubectl delete -f voting-app-v4.yaml
```



--------------------------------------------------------------------------
### v7 - Pod with Liveness and Readiness Probe ( health checks)
--------------------------------------------------------------------------
```bash
kubectl apply -f voting-app-v5.yaml
kubectl get pods
kubectl delete -f voting-app-v5.yaml
```



--------------------------------------------------------------------------
### v6 - Loadbalancer Service ( cloud provider specific)
--------------------------------------------------------------------------

### create k8s cluster on Azure
```bash
az group create --name myResourceGroup --location centralindia
az aks create \
    --resource-group nag-rg \
    --name nag-aks \
    --generate-ssh-keys \
    --node-count 3 \
    --zones 1 2 3
kubectl get nodes -o wide
kubectl get nodes --show-labels
```

```bash
kubectl apply -f voting-app-v6.yaml
kubectl get pods -w
kubectl delete -f voting-app-v6.yaml

```



--------------------------------------------------------------------------
### v7 - Ingress Controller ( Nginx)
--------------------------------------------------------------------------

on kind k8s cluster,

```bash
kubectl apply -f https://kind.sigs.k8s.io/examples/ingress/deploy-ingress-nginx.yaml
kubectl wait --namespace ingress-nginx \
  --for=condition=ready pod \
  --selector=app.kubernetes.io/component=controller \
  --timeout=90s

kubectl apply -f voting-app-v7.yaml
kubectl get pods
kubectl get svc -n ingress-nginx 
curl -H "Host: vote.local" http://172.18.0.5:<nodeport>
curl -H "Host: result.local" http://172.18.0.5:<nodeport>

sudo nano /etc/hosts
# add below line
172.1.0.5 vote.local result.local

http://vote.local:<nodeport>
http://result.local:<nodeport>

#  ( for Mac users)
kubectl port-forward --address 0.0.0.0 -n ingress-nginx svc/ingress-nginx-controller 8080:80


kubectl delete -f voting-app-v7.yaml

```



--------------------------------------------------------------------------
### v8 - Network policies with calico-CNI / cilium-CNI
--------------------------------------------------------------------------


### Install Calico CNI on Kind

```bash
kubectl delete daemonsets -n kube-system kindnet
kubectl apply -f https://docs.projectcalico.org/manifests/calico.yaml
kubectl get pods -n kube-system
```

### Apply Network Policy

```bash
kubectl apply -f voting-app-v8.yaml
kubectl get pods
kubectl get svc
kubectl get networkpolicies

kubectl exec -it $(kubectl get pod -l app=vote -o jsonpath="{.items[0].metadata.name}") -- sh
nc -zv redis 6379
nc -zv db 5432


kubectl delete -f voting-app-v8.yaml
```


--------------------------------------------------------------------------
### v9 -  Networking with Service Mesh (Istio)
--------------------------------------------------------------------------


🚀 Why Use Istio with Calico?

### Istio provides traffic management, security, and observability at the application layer (L7).
### Calico provides network security policies and network routing at the network layer (L3/L4).
### Together, they enable fine-grained security, service-to-service encryption, and observability.


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
kubectl apply -f voting-app-v9.yaml

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

kubectl apply -f voting-app-v9.yaml
kubectl get pods -w


kubectl get svc -n istio-system
for i in {1..100000}; do curl -H "Host: vote.local" http://172.18.0.5:32497; done


# Access Prometheus Dashboard
istioctl dashboard prometheus

# Access Grafana Dashboard
istioctl dashboard grafana

# Cleanup
kubectl delete -f voting-app-v9.yaml

```


--------------------------------------------------------------------------
###  Traffic Splitting with Istio
--------------------------------------------------------------------------


```bash
kubectl apply -f voting-app-v10.yaml
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

