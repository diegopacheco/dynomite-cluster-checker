# dynomite-cluster-checker
Dynomite Cluster Checker checks if a Dynomite cluster is working properly via Dyno. 

## Why ?

* Deployment Validation: Dynomite Cluster checks dynomite clusters
* Telemetry: DCC cheks if seeds and nodes are responsive
* Troubleshooting: Is my Dynomite cluster okay?

## Features

* Cheks and Validate Dynomite Cluster Seeds and Cluster Configs
* Checks if there are bad nodes (SG issues, ports, etc..)
* Perform GET/SET to check latency between nodes in the cluster
* Check Data consistency
* Check Data Replication across all cluster
* Check Node Fail over 
* Check Whole Cluster connectivity and Seeds
* REST exposure for the report: http://localhost:7766/dynomite-cluster-checker/check?seeds="sd1|sd2|sd3..."
* Telemetry Mode: Most of people use solutions like Magios, Sensu, Zabbix, Prometheus, SignalFx and many other - This returns 0 for TRUE and 1 for FALSe or errros. So you can build lerts on top of the result json.

## TODO

* Latency historical comparison

## Building
```bash
./gradlew clean build
```

## Checking Dynomite Cluster

```bash
./gradlew execute -Dexec.args="172.18.0.101:8101:rack1:dc:100|172.18.0.102:8101:rack2:dc:100|172.18.0.103:8101:rack3:dc:100"
```
DCC will output 
```bash
{

 "timeToRun": "2 seconds",

 "failoverStatus": "OK",

 "replicationCount": "3",

 "badNodes": [], 

 "nodesReport":

[

  {
    "server":"[172.18.0.101:8101:rack1:dc:100, 172.18.0.102:8101:rack2:dc:100, 172.18.0.103:8101:rack3:dc:100]",
    "seeds":"172.18.0.101:8101:rack1:dc:100|172.18.0.102:8101:rack2:dc:100|172.18.0.103:8101:rack3:dc:100",
    "insertTime":"0",
    "consistency":"true"
,  },

  {
    "server":"172.18.0.101",
    "seeds":"172.18.0.101:8101:rack1:dc:100",
    "getTime":"0",
    "consistency":"true"
,  },

  {
    "server":"172.18.0.102",
    "seeds":"172.18.0.102:8101:rack2:dc:100",
    "getTime":"0",
    "consistency":"true"
,  },

  {
    "server":"172.18.0.103",
    "seeds":"172.18.0.103:8101:rack3:dc:100",
    "getTime":"0",
    "consistency":"true"
,  }

]

}
```

You Can pass Muliples nodes. If you send mroe than One node we will check for the nodes data replication consistency.<BR>
So we will connect in one node and the time and see if data is there.

```bash
-Dexec.args="127.0.0.1:8102:rack1:localdc:1383429731|127.0.0.1:8102:rack1:localdc:1383429731|127.1.1.1:8102:rack1:localdc:1383429731|127.1.1.1:8102:rack1:localdc:1383429731"
```

#### REST API

Run the embeded Jetty server with.
```
 dynomite-cluster-checker$ ./gradlew jettyRun
:compileJava UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
> Building 75% > :jettyRun > Running at http://localhost:7766/dynomite-cluster-checker

```

Them you can call. 
```bash
curl http://localhost:7766/dynomite-cluster-checker/check?seeds=172.18.0.101:8101:rack1:dc:100|172.18.0.102:8101:rack2:dc:100|172.18.0.103:8101:rack3:dc:100
```
```bash
{

 "timeToRun": "2 seconds",

 "failoverStatus": "OK",

 "replicationCount": "3",

 "badNodes": [], 

 "nodesReport":

[

  {
    "server":"[172.18.0.101:8101:rack1:dc:100, 172.18.0.102:8101:rack2:dc:100, 172.18.0.103:8101:rack3:dc:100]",
    "seeds":"172.18.0.101:8101:rack1:dc:100|172.18.0.102:8101:rack2:dc:100|172.18.0.103:8101:rack3:dc:100",
    "insertTime":"0",
    "consistency":"true"
,  },

  {
    "server":"172.18.0.101",
    "seeds":"172.18.0.101:8101:rack1:dc:100",
    "getTime":"0",
    "consistency":"true"
,  },

  {
    "server":"172.18.0.102",
    "seeds":"172.18.0.102:8101:rack2:dc:100",
    "getTime":"0",
    "consistency":"true"
,  },

  {
    "server":"172.18.0.103",
    "seeds":"172.18.0.103:8101:rack3:dc:100",
    "getTime":"0",
    "consistency":"true"
,  }

]

}

```

## Dynomite-docker

There is another project I create to make easier to create dynomite clusters. This project is called dynomite-docker and thid project create 2 dynomite clusters of 3 nodes each. Dynomite-docker uses DCC toi test clusters. You can find more info here https://github.com/diegopacheco/dynomite-docker. 


Cheers, <BR>
Diego Pacheco (@diego_pacheco)
