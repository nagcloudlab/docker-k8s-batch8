




pod-to-pod communication
```bash
kubectl apply -f nginx-pods.yaml
kubectl get pods -o wide
kubectl exec -it nginx-pod-1 -- curl 10.244.2.2

kubectl run curl-pod --image=curlimages/curl --restart=Never -it --rm -- sh

```


