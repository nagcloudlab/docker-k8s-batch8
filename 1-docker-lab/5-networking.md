


### Bridge Network Driver

Create a bridge network:

```bash
docker network create my_bridge_network1
docker network ls
```

Create a container with the `bridge` network driver:

```bash
docker rm -f bridge_container1
docker run --network my_bridge_network1 --name bridge_container1 -it nicolaka/netshoot /bin/bash
ifconfig
```

Create another container on the same bridge network:

```bash
docker rm -f bridge_container2
docker run --network my_bridge_network1 --name bridge_container2 -it nicolaka/netshoot /bin/bash
ifconfig
```

Create Bridge Network with a subnet and gateway:

```bash
docker network create --driver bridge --subnet 192.168.100.0/24 --gateway 192.168.100.1 my_bridge_network2
docker network inspect my_bridge_network2
```

Create a container on the bridge network with a specific IP address:

```bash
docker rm -f static_container1
docker run --network my_bridge_network2 --name static_container1 --ip 192.168.100.10 -it nicolaka/netshoot /bin/bash
ifconfig
```

```bash
docker rm -f static_container2
docker run --network my_bridge_network2 --name static_container2 --ip 192.168.100.11 -it nicolaka/netshoot /bin/bash
ifconfig
```

### Host Network Driver

Create a container with the `host` network driver:

```bash
docker rm -f host_container
docker run --network host --name host_container -it nicolaka/netshoot /bin/bash
ifconfig
```

Create a container with the `host` network driver , and run a web server - nginx

```bash
docker rm -f host_container
docker run --network host --name host_container -d nginx
curl localhost
```


### None Network Driver

Create a container with the `none` network driver:

```bash
docker rm -f isolated_container
docker run --network none --name isolated_container -it nicolaka/netshoot /bin/bash
ifconfig
```


### Macvlan Network Driver

Create a Macvlan network:

```bash
docker network create -d macvlan \
  --subnet=192.168.1.0/24 \
  --gateway=192.168.1.1 \
  -o parent=eth0 \
  my_macvlan_network


docker rm -f macvlan_container1
docker run --network my_macvlan_network --name macvlan_container1 -it nicolaka/netshoot /bin/bash
ifconfig

docker rm -f macvlan_container2
docker run --network my_macvlan_network --name macvlan_container2 -it nicolaka/netshoot /bin/bash
ifconfig
docker network inspect my_macvlan_network

```



### Ipvlan Network Driver

Create a Ipvlan network:

```bash
docker network create -d ipvlan \
  --subnet=192.168.2.0/24 \
  --gateway=192.168.2.1 \
  -o parent=eth0 \
  my_ipvlan_network

docker rm -f ipvlan_container1
docker run --network my_ipvlan_network --name ipvlan_container1 -it nicolaka/netshoot /bin/bash

docker rm -f ipvlan_container2
docker run --network my_ipvlan_network --name ipvlan_container2 -it nicolaka/netshoot /bin/bash

docker network inspect my_ipvlan_network
```

