

### 1️⃣ Default Scheduling (Kube-Scheduler)

```bash
kubectl apply -f default-scheduled-pod.yaml
kubectl get pods -o wide
kubectl describe pod default-scheduled-pod
kubectl delete pod default-scheduled-pod
```

### 8️⃣ Schedule based on Node Name

```bash
kubectl apply -f manual-scheduled-pod.yaml
kubectl get pods -o wide
kubectl describe pod manual-scheduled-pod
kubectl delete -f manual-scheduled-pod.yaml
```

### 2️⃣ Node Selector ( using labels )
```bash
kubectl get nodes --show-labels
kubectl label nodes kind-cluster-worker size=large # add label to node
kubectl label nodes kind-cluster-worker2 disktype=ssd # add label to node
kubectl apply -f node-selector-pod.yaml
kubectl get pods -o wide
kubectl describe pod node-selector-pod
kubectl delete -f node-selector-pod.yaml
kubectl label nodes kind-cluster-worker2 disktype- # remove label from node
```


### 3️⃣ Node Affinity

```bash
kubectl get nodes my-k8s-cluster-worker  --show-labels
kubectl get nodes -l size
kubectl get nodes -L size
kubectl label nodes my-k8s-cluster-worker size=large
kubectl apply -f node-affinity-pod.yaml
kubectl get pods -o wide
kubectl describe pod node-affinity-pod
kubectl delete -f node-affinity-pod.yaml
```

### 4️⃣ Pod Affinity & Anti-Affinity


```bash
kubectl get nodes --show-labels
kubectl apply -f pod-affinity-pod.yaml
kubectl get pods -o wide
kubectl describe pod pod-affinity-pod
kubectl delete -f pod-affinity-pod.yaml
```

### 5️⃣ Taints & Tolerations

```bash
kubectl describe node my-k8s-cluster-worker
kubectl taint nodes my-k8s-cluster-worker trainee=true:NoSchedule
kubectl apply -f taint-toleration-pod.yaml
kubectl get pods -o wide
kubectl describe pod taint-toleration-pod
kubectl delete -f taint-toleration-pod.yaml
```





### 6️⃣ Resource Requests & Limits

```bash
kubectl apply -f resource-limits.yaml
kubectl get pods -o wide
kubectl top pods
kubectl describe pod resource-limits
kubectl delete -f resource-limits.yaml

```


### 7️⃣ Priority Classes

```bash
kubectl apply -f priority-class.yaml
kubectl apply -f priority-pod.yaml
kubectl get pods -o wide
kubectl describe pod priority-pod
kubectl delete -f priority-pod.yaml
kubectl delete -f priority-class.yaml
```


### remove all the labels and taints

```bash
kubectl label nodes my-cluster-worker3 disktype-
kubectl taint nodes my-cluster-worker3 ssd-
```