

```bash
kubectl delete -f java-web-service-pod.yaml
kubectl apply -f java-web-service-pod.yaml
kubectl get pods
kubectl exec -it java-web-service-pod -c java-web-service-container  -- /bin/sh
apk update && apk add curl
curl localhost:8080/hello
cat /app/log/java-web-service.log
kubectl exec -it java-web-service-pod -c log-reader-container  -- /bin/sh
cat /var/log/java-web-service.log
```


### persistent volumes

```bash
kubectl apply -f pv.yaml
kubectl get pv
```