

### copy the ca.crt and ca.key from the control plane node to the local machine

```bash
docker ps
docker cp my-k8s-cluster-control-plane:/etc/kubernetes/pki/ca.crt .
docker cp my-k8s-cluster-control-plane:/etc/kubernetes/pki/ca.key .

```

### generate user1 certificate

```bash
openssl genrsa -out user1.key 2048
openssl req -new -key user1.key -out user1.csr -subj "/CN=user1/O=npci-team"
openssl x509 -req -in user1.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out user1.crt -days 365
```


### configure kubectl with user1 certificate

```bash
kubectl config set-credentials user1 --client-certificate=user1.crt --client-key=user1.key
```

### configure kubectl with user1 context

```bash
kubectl config set-context user1-context --cluster=kind-my-k8s-cluster --user=user1
```

### switch to user1 context

```bash
kubectl config get-contexts
kubectl config use-context user1-context
kubectl config use-context kind-my-k8s-cluster
```


using http-client to access the k8s api server

```bash
curl --cacert ./ca.crt \
     --cert ./user1.crt \
     --key ./user1.key \
     https://172.18.0.5:6443/api/v1/pods
```


create 'developer' role for 'default' namespace

```bash
kubectl apply -f developer-role.yaml
kubectl get roles
kubectl apply -f developer-role-binding.yaml
```


### create 'cluster-role'

```bash
kubectl apply -f cluster-read-role.yaml
kubectl get clusterroles
kubectl apply -f cluster-read-role-binding.yaml



### create service-account 'app-service-account'

```bash
kubectl create serviceaccount app-service-account
kubectl get serviceaccounts

kubectl apply -f serviceaccount-pod.yaml
kubectl exec -it sa-pod -- apk --update add curl
kubectl exec -it sa-pod -- sh
TOKEN=$(cat /var/run/secrets/kubernetes.io/serviceaccount/token)
curl -s --header "Authorization: Bearer $TOKEN" --cacert /var/run/secrets/kubernetes.io/serviceaccount/ca.crt https://kubernetes.default.svc/api/v1/namespaces/default/pods

kubectl apply -f serviceaccount-role.yaml
kubectl apply -f serviceaccount-rolebinding.yaml

```


