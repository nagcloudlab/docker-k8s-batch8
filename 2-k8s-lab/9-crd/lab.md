

# Custom Resource Definition (CRD)


```bash
kubectl apply -f widget-crd.yaml
kubectl apply -f widget-instance-1.yaml
kubectl apply -f widget-instance-2.yaml
kubectl get widgets
kubectl get widgets -o yaml
kubectl describe widget my-widget-1
kubectl delete widget my-widget-1
kubectl delete widget my-widget-2
```

### Build and push the controller image

```bash
docker build -t nagabhushanamn/widget-controller:0.1 .
docker push nagabhushanamn/widget-controller:0.1
```


### Deploy the controller

```bash
kubectl apply -f widget-controller-rbac.yaml
kubectl delete -f widget-controller-deployment.yaml
kubectl apply -f widget-controller-deployment.yaml
kubectl get pods -w
kubectl logs widget-controller-5f48f7c9fb-zqt7k
```


kubectl exec -it $(kubectl get pod -l app=widget-controller -o jsonpath='{.items[0].metadata.name}') -- /bin/bash
python3 -m widget-controller



<!-- e.g -->
<!-- https://strimzi.io/documentation/ -->