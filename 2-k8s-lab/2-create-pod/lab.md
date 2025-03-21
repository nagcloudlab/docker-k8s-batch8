

### Lab: Create a Pod (manually)

```bash
kubectl get pods
kubectl run nginx --image=nginx
kubectl get pods -o wide
kubectl describe pod nginx
kubectl delete pod nginx
```

### Lab: Create a Pod with a YAML file (kubectl create -f)

```bash
kubectl create -f pod.yaml
kubectl get pods
kubectl create -f pod.yaml
kubectl delete -f pod.yaml
kubectl create -f pod.yaml
kubectl get pods
kubectl delete pod nginx-pod
```

### Lab: Create a Pod with a YAML file (kubectl apply -f)( recommended)

```bash
kubectl apply -f pod.yaml 
kubectl get pods
```

### Lab: Edit a Pod
```bash
kubectl edit pod nginx-pod
kubectl get pods
```

### Lab: Get the logs of a Pod
```bash
kubectl logs nginx-pod 
kubectl logs nginx-pod -f
```

### Lab: Exec into a Pod
```bash
kubectl exec -it nginx-pod -- /bin/bash
```

### Lab: Add a label to a Pod
```bash
kubectl label pod nginx-pod type=reverse-proxy
kubectl get pods --show-labels
kubectl get pods -l environment=dev
```

### Lab: Delete a Pod 
```bash
kubectl delete pod nginx-pod
kubectl delete -f pod.yaml
```





## ReplicationController, ReplicaSet, Deployment

### Create a Pod using ReplicationController ( deprecated )

```bash
kubectl apply -f rc.yaml
kubectl get pods
kubectl get rc
```

### Scale a ReplicationController
```bash
kubectl scale --replicas=4 rc nginx-rc
kubectl get pods
```

### Create Pod manually
```bash
kubectl apply -f pod.yaml
kubectl get pods
```

### Delete a ReplicationController
```bash
kubectl delete rc nginx-rc
kubectl get pods
```


### Create a Pod using ReplicaSet
```bash
kubectl apply -f rs.yaml
kubectl get rs
kubectl get pods
```

### Scale a ReplicaSet
```bash
kubectl scale --replicas=4 rs nginx-rs
kubectl get pods
```

### Create a Pod manually
```bash
kubectl apply -f pod.yaml
kubectl get pods
```

### Delete a ReplicaSet
```bash
kubectl delete rs nginx-rs
kubectl get pods
```





### Create a Pod using Deployment
```bash
kubectl apply -f deploy.yaml
kubectl get deploy
kubectl get rs
kubectl get pods
```

### Scale a Deployment
```bash
kubectl scale --replicas=4 deploy nginx-deploy
kubectl get pods
```

### Rollout a Deployment
```bash
kubectl set image deploy nginx-deploy nginx-container=nginx:1.27.4
kubectl get pods
kubectl rollout status deploy nginx-deploy
kubectl rollout history deploy nginx-deploy
kubectl rollout undo deploy nginx-deploy --to-revision=1
kubectl get pods
```

### Delete a Deployment
```bash
kubectl delete deploy nginx-deploy
kubectl get pods
```


## StatefulSet, DaemonSet, Job, CronJob


### Create a Pod using StatefulSet
```bash
kubectl apply -f sts.yaml
kubectl get sts
kubectl get pods
```

### Scale up a StatefulSet
```bash
kubectl scale --replicas=3 sts nginx-sts
kubectl get pods
```

### Scale down a StatefulSet
```bash
kubectl scale --replicas=3 sts nginx-sts
kubectl get pods
```

### Delete a StatefulSet
```bash
kubectl delete sts nginx-sts
kubectl get pods
```


### Create a Pod using DaemonSet
```bash
kubectl apply -f ds.yaml
kubectl get ds
kubectl get pods -o wide
```

### Delete a DaemonSet
```bash
kubectl delete -f ds.yaml
kubectl get pods
```

### Create a Job
```bash
kubectl apply -f job.yaml
kubectl get jobs
kubectl describe job hello-job
kubectl get pods
kubectl logs hello-job-xxxxx

kubectl delete job hello-job

kubectl apply -f multi-run-job.yaml
kubectl get jobs
kubectl describe job multi-run-job
kubectl get pods

kubectl delete job multi-run-job


kubectl apply -f parallel-job.yaml
kubectl get jobs
kubectl describe job parallel-job
kubectl get pods

kubectl delete job parallel-job

```

### Create a CronJob
```bash
kubectl apply -f cronjob.yaml
kubectl get cronjob
kubectl get jobs
kubectl get pods
kubectl logs hello-cronjob-28993458-mm42t
kubectl delete cronjob hello-cronjob
```

Pod (Manual) ->	Best for testing/debugging. No auto-restart.
ReplicationController ->	Ensures a set number of pods. Deprecated.
ReplicaSet ->	Ensures a set number of pods. No rolling updates.
Deployment ->	Best for stateless apps. Supports rolling updates.
StatefulSet ->	Best for stateful apps (databases, Kafka). Stable pod names.
DaemonSet ->	Ensures one pod per node (used for system-wide services like monitoring/logging).


Approach	Purpose
Job	Runs a task once and exits.
Job with Completions	Ensures a specific number of runs.
Parallel Job	Runs multiple pods at the same time for faster completion.
CronJob	Runs a job on a schedule (like a cron job in Linux).
