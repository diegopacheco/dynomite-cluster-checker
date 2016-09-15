# dynomite-cluster-checker
Dynomite Cluster Checker checks if a Dynomite cluster is working properly via Dyno. 

## Why ?

* Validated Dynomite Cluster Instalations
* Telemetry, cheks if seeds and nodes are responsive

## Features

* Cheks and Validate Dynomite Cluster Seeds Config
* Checks if there are bad nodes (SG issues, ports, etc..)
* Perform GET/SET to check latency between nodes in the cluster
* Check Data consistency
* Check Data Replication across all cluster
* Check Node Fail over 
* Check Whole Cluster connectivity and Seeds
* REST exposure for the report: http://localhost:7766/dynomite-cluster-checker/check?seeds="sd1|sd2|sd3..."

## TODO

* Latency historical comparison

## Building
```bash
./gradlew clean build
```

## Checking Dynomite Cluster

```bash
./gradlew execute -Dexec.args="127.0.0.1:8102:rack1:localdc:1383429731"
```
SAMPLES RESULTS 
```bash
#
# 1. ALL BAD NODES
#

**** BEGIN DYNOMITE CLUSTER CHECKER ****
1. Checking cluster connection... 
BAD NODES:
    172.28.198.18:8102:rack1:default_dc:100
    172.28.198.236:8102:rack2:default_dc:100
    172.28.198.118:8102:rack3:default_dc:100
2. Cannot check data replication since there are no valid nodes
4. Shwoing Results as JSON... 
[
]

**** END DYNOMITE CLUSTER CHECKER ****

#
# 2. One BAD NODES
#

**** BEGIN DYNOMITE CLUSTER CHECKER ****
1. Checking cluster connection... 
BAD NODES:
    172.28.198.118:8102:rack3:default_dc:100
2. Checking cluster data replication... 
SEEDS: [172.28.198.18:8102:rack1:default_dc:100, 172.28.198.236:8102:rack2:default_dc:100]
Checking Node: 172.28.198.18
  TIME to   Insert DCC_dynomite_123_kt - Value: DCC_replication_works: 2.0 ms - 0 s
  TIME to   Get: DCC_dynomite_123_kt : 2.0 ms - 0 s
  200 OK - set/get working fine!
Checking Node: 172.28.198.236
  TIME to   Get: DCC_dynomite_123_kt : 2.0 ms - 0 s
  200 OK - set/get working fine!
3. Checking cluster failover... 
All Seeds Cluster Failover test: OK
4. Shwoing Results as JSON... 
[
  {
    "server":"172.28.198.18",
    "seeds":"[172.28.198.18:8102:rack1:default_dc:100, 172.28.198.236:8102:rack2:default_dc:100]",
    "insertTime":"2.0 ms",
    "getTime":"2.0 ms",
    "consistency":"true"
  },
  {
    "server":"172.28.198.236",
    "getTime":"2.0 ms",
    "consistency":"true"
  }
]

**** END DYNOMITE CLUSTER CHECKER ****

#
# 3. all working good.
# 

**** BEGIN DYNOMITE CLUSTER CHECKER ****
1. Checking cluster connection... 
    OK - All nodes are accessible! 
2. Checking cluster data replication... 
SEEDS: [172.28.198.18:8102:rack1:default_dc:100, 172.28.198.236:8102:rack2:default_dc:100, 172.28.198.118:8102:rack3:default_dc:100]
Checking Node: 172.28.198.18
  TIME to   Insert DCC_dynomite_123_kt - Value: DCC_replication_works: 2.0 ms - 0 s
  TIME to   Get: DCC_dynomite_123_kt : 1.0 ms - 0 s
  200 OK - set/get working fine!
Checking Node: 172.28.198.236
  TIME to   Get: DCC_dynomite_123_kt : 1.0 ms - 0 s
  200 OK - set/get working fine!
Checking Node: 172.28.198.118
  TIME to   Get: DCC_dynomite_123_kt : 4.0 ms - 0 s
  200 OK - set/get working fine!
3. Checking cluster failover... 
All Seeds Cluster Failover test: OK
4. Shwoing Results as JSON... 
[
  {
    "server":"172.28.198.18",
    "seeds":"[172.28.198.18:8102:rack1:default_dc:100, 172.28.198.236:8102:rack2:default_dc:100, 172.28.198.118:8102:rack3:default_dc:100]",
    "insertTime":"2.0 ms",
    "getTime":"1.0 ms",
    "consistency":"true"
  },
  {
    "server":"172.28.198.236",
    "getTime":"1.0 ms",
    "consistency":"true"
  },
  {
    "server":"172.28.198.118",
    "getTime":"4.0 ms",
    "consistency":"true"
  }
]

**** END DYNOMITE CLUSTER CHECKER ****

#
# 4. Inconsistency 
#

**** BEGIN DYNOMITE CLUSTER CHECKER ****
1. Checking cluster connection... 
    OK - All nodes are accessible! 
2. Checking cluster data replication... 
SEEDS: [172.28.198.18:8102:rack1:default_dc:100, 172.28.198.236:8102:rack2:default_dc:100, 172.28.198.118:8102:rack3:default_dc:100]
Checking Node: 172.28.198.18
  TIME to   Insert DCC_dynomite_123_kt - Value: DCC_replication_works: 2.0 ms - 0 s
  TIME to   Get: DCC_dynomite_123_kt : 2.0 ms - 0 s
  200 OK - set/get working fine!
Checking Node: 172.28.198.236
  TIME to   Get: DCC_dynomite_123_kt : 2.0 ms - 0 s
  200 OK - set/get working fine!
Checking Node: 172.28.198.118
  TIME to   Get: DCC_dynomite_123_kt : 1.0 ms - 0 s
  ERROR - Inconsistency set/get! Set: DCC_dynomite_123_ktGet: null
3. Checking cluster failover... 
All Seeds Cluster Failover test: OK
4. Shwoing Results as JSON... 
[
  {
    "server":"172.28.198.18",
    "seeds":"[172.28.198.18:8102:rack1:default_dc:100, 172.28.198.236:8102:rack2:default_dc:100, 172.28.198.118:8102:rack3:default_dc:100]",
    "insertTime":"2.0 ms",
    "getTime":"2.0 ms",
    "consistency":"true"
  },
  {
    "server":"172.28.198.236",
    "getTime":"2.0 ms",
    "consistency":"true"
  },
  {
    "server":"172.28.198.118",
    "getTime":"1.0 ms",
    "consistency":"false"
  }
]

**** END DYNOMITE CLUSTER CHECKER ****

#
# 5. bad cluster - failover issue
# 

**** BEGIN DYNOMITE CLUSTER CHECKER ****
1. Checking cluster connection... 
BAD NODES:
    172.28.198.18:8102:rack1:default_dc:100
2. Checking cluster data replication... 
SEEDS: [172.28.198.236:8102:rack2:default_dc:200, 172.28.198.118:8102:rack3:default_dc:300]
Checking Node: 172.28.198.236
  TIME to   Insert DCC_dynomite_123_kt - Value: DCC_replication_works: 2.0 ms - 0 s
  TIME to   Get: DCC_dynomite_123_kt : 2.0 ms - 0 s
  200 OK - set/get working fine!
Checking Node: 172.28.198.118
  TIME to   Get: DCC_dynomite_123_kt : 2.0 ms - 0 s
  200 OK - set/get working fine!
3. Checking cluster failover... 
All Seeds Cluster Failover test: FAIL: PoolOfflineException: [host=Host [hostname=UNKNOWN, ipAddress=UNKNOWN, port=0, rack: null, datacenter: null, status: Down], latency=0(0), attempts=0]host pool is offline and no Racks available for fallback
4. Shwoing Results as JSON... 
[
  {
    "server":"172.28.198.236",
    "seeds":"[172.28.198.236:8102:rack2:default_dc:200, 172.28.198.118:8102:rack3:default_dc:300]",
    "insertTime":"2.0 ms",
    "getTime":"2.0 ms",
    "consistency":"true"
  },
  {
    "server":"172.28.198.118",
    "getTime":"2.0 ms",
    "consistency":"true"
  }
]

**** END DYNOMITE CLUSTER CHECKER ****

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

Them you can call:  curl http://localhost:7766/dynomite-cluster-checker/check?seeds=127.0.0.1:8101:rack1:local-dc:437425602
```bash
  {
    "server":"127.0.0.1",
    "seeds":"[127.0.0.1:8101:rack1:local-dc:437425602]",
    "insertTime":"1.0 ms",
    "getTime":"2.0 ms",
    "consistency":"true"
  }
```

Cheers, <BR>
Diego Pacheco (@diego_pacheco)
