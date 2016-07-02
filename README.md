# dynomite-cluster-checker
Dynomite Cluster Checker checks if a Dynomite cluster is working properly via Dyno. 

## Why ?

* Validated Dynomite Cluster Instalations
* Telemetry, cheks if seeds and nodes are responsive

## Features

* Cheks and Validate Dynomite Cluster Seeds Config
* Checks if there are bad nodes
* Perform GET/SET to check latency between nodes in the cluster
* Check Data consistency
* Check Data Replication across all cluster

## TODO

* REST exposure
* Latency historical comparison
* Pre-saved seeds

## Building
```bash
./gradlew clean build
```

## Checking Dynomite Cluster

```bash
./gradlew execute -Dexec.args="127.0.0.1:8102:rack1:localdc:1383429731"
```
Result
```bash
diego@4winds:~/github/diegopacheco/dynomite-cluster-checker/dynomite-cluster-checker$ ./gradlew execute -Dexec.args="127.0.0.1:8102:rack1:localdc:1383429731"
:compileJava UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:execute
2016-07-01 18:13:36 WARN  URLConfigurationSource:122 - No URLs will be polled as dynamic configuration sources.
2016-07-01 18:13:36 INFO  URLConfigurationSource:123 - To enable URLs as dynamic configuration sources, define System property archaius.configurationSource.additionalUrls or make config.properties available on classpath.
2016-07-01 18:13:36 INFO  DynamicPropertyFactory:271 - DynamicPropertyFactory is initialized with configuration sources: com.netflix.config.ConcurrentCompositeConfiguration@7cef4e59
2016-07-01 18:13:36 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:13:36 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:36 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:13:36 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:36 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:13:36 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:36 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:36 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:36 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:36 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:13:36 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:13:36 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:13:37 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:13:37 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:13:37 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:13:37 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:13:37 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole

**** BEGIN DYNOMITE CLUSTER CHECKER ****
1. Checking cluster connection... 
    OK - All nodes are accessible! 
2. Checking cluster data replication... 
SEEDS: [127.0.0.1:8102:rack1:localdc:1383429731]
Checking Node: 127.0.0.1
  TIME to   Insert dynomote - Value: works: 3.0 ms - 0 s
  TIME to   Get: dynomote : 2.0 ms - 0 s
  200 OK - set/get working fine!
3. Shwoing Results as JSON... 
  {
    "server":"127.0.0.1",
    "seeds":"[127.0.0.1:8102:rack1:localdc:1383429731]",
    "insertTime":"3.0 ms",
    "getTime":"2.0 ms",
    "consistency":"true"
  }
**** END DYNOMITE CLUSTER CHECKER ****

2016-07-01 18:13:37 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:13:37 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:13:37 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole

BUILD SUCCESSFUL

Total time: 5.324 secs

This build could be faster, please consider using the Gradle Daemon: http://gradle.org/docs/2.4/userguide/gradle_daemon.html
diego@4winds:~/github/diegopacheco/dynomite-cluster-checker/dynomite-cluster-checker$ 

```

You Can pass Muliples nodes. If you send mroe than One node we will check for the nodes data replication consistency.<BR>
So we will connect in one node and the time and see if data is there.

```bash
diego@4winds:~/github/diegopacheco/dynomite-cluster-checker/dynomite-cluster-checker$ ./gradlew execute -Dexec.args="127.0.0.1:8102:rack1:localdc:1383429731|127.0.0.1:8102:rack1:localdc:1383429731|127.1.1.1:8102:rack1:localdc:1383429731|127.1.1.1:8102:rack1:localdc:1383429731"
:compileJava UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:execute
2016-07-01 18:15:35 WARN  URLConfigurationSource:122 - No URLs will be polled as dynamic configuration sources.
2016-07-01 18:15:35 INFO  URLConfigurationSource:123 - To enable URLs as dynamic configuration sources, define System property archaius.configurationSource.additionalUrls or make config.properties available on classpath.
2016-07-01 18:15:35 INFO  DynamicPropertyFactory:271 - DynamicPropertyFactory is initialized with configuration sources: com.netflix.config.ConcurrentCompositeConfiguration@7cef4e59
2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.1.1.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.1.1.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:171 - Unable to make any successful connections to host Host [name=127.1.1.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:195 - Failed to init host pool for host: Host [name=127.1.1.1, port=8102, dc: localdc, status: Up]
com.netflix.dyno.connectionpool.exception.DynoConnectException: DynoConnectException: [host=Host [name=UNKNOWN, port=0, dc: null, status: Down], latency=0(0), attempts=0]Unable to make ANY successful connections to host Host [name=127.1.1.1, port=8102, dc: localdc, status: Up]
	at com.netflix.dyno.connectionpool.impl.HostConnectionPoolImpl.primeConnections(HostConnectionPoolImpl.java:173)
	at com.netflix.dyno.connectionpool.impl.ConnectionPoolImpl.addHost(ConnectionPoolImpl.java:176)
	at com.netflix.dyno.connectionpool.impl.ConnectionPoolImpl$3.call(ConnectionPoolImpl.java:489)
	at com.netflix.dyno.connectionpool.impl.ConnectionPoolImpl$3.call(ConnectionPoolImpl.java:485)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
Could not Connet on Node: 127.1.1.1:8102:rack1:localdc:1383429731 EX: java.lang.RuntimeException: java.lang.ArrayIndexOutOfBoundsException: 0
2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.1.1.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.1.1.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:171 - Unable to make any successful connections to host Host [name=127.1.1.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:195 - Failed to init host pool for host: Host [name=127.1.1.1, port=8102, dc: localdc, status: Up]
com.netflix.dyno.connectionpool.exception.DynoConnectException: DynoConnectException: [host=Host [name=UNKNOWN, port=0, dc: null, status: Down], latency=0(0), attempts=0]Unable to make ANY successful connections to host Host [name=127.1.1.1, port=8102, dc: localdc, status: Up]
	at com.netflix.dyno.connectionpool.impl.HostConnectionPoolImpl.primeConnections(HostConnectionPoolImpl.java:173)
	at com.netflix.dyno.connectionpool.impl.ConnectionPoolImpl.addHost(ConnectionPoolImpl.java:176)
	at com.netflix.dyno.connectionpool.impl.ConnectionPoolImpl$3.call(ConnectionPoolImpl.java:489)
	at com.netflix.dyno.connectionpool.impl.ConnectionPoolImpl$3.call(ConnectionPoolImpl.java:485)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
Could not Connet on Node: 127.1.1.1:8102:rack1:localdc:1383429731 EX: java.lang.RuntimeException: java.lang.ArrayIndexOutOfBoundsException: 0
2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole

**** BEGIN DYNOMITE CLUSTER CHECKER ****
1. Checking cluster connection... 
BAD NODES:
    127.1.1.1:8102:rack1:localdc:1383429731
    127.1.1.1:8102:rack1:localdc:1383429731
2. Checking cluster data replication... 
SEEDS: [127.0.0.1:8102:rack1:localdc:1383429731, 127.0.0.1:8102:rack1:localdc:1383429731]
Checking Node: 127.0.0.1
  TIME to   Insert dynomote - Value: works: 2.0 ms - 0 s
  TIME to   Get: dynomote : 2.0 ms - 0 s
  200 OK - set/get working fine!
Checking Node: 127.0.0.1
  TIME to   Get: dynomote : 2.0 ms - 0 s
  200 OK - set/get working fine!
3. Shwoing Results as JSON... 
[
  {
    "server":"127.0.0.1",
    "seeds":"[127.0.0.1:8102:rack1:localdc:1383429731, 127.0.0.1:8102:rack1:localdc:1383429731]",
    "insertTime":"2.0 ms",
    "getTime":"2.0 ms",
    "consistency":"true"
  },
  {
    "server":"127.0.0.1",
    "getTime":"2.0 ms",
    "consistency":"true"
  }
]

**** END DYNOMITE CLUSTER CHECKER ****

2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  ArchaiusConnectionPoolConfiguration:194 - Dyno configuration: CompressionStrategy = NONE
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:173 - Adding host connection pool for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:162 - Priming connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up], with conns:3
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:177 - Successfully primed 3 of 3 to Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:597 - registered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole
2016-07-01 18:15:35 INFO  HostConnectionPoolImpl:148 - Shutting down connection pool for host:Host [name=127.0.0.1, port=8102, dc: localdc, status: Up]
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 WARN  HostConnectionPoolImpl:419 - Failed to close connection for host: Host [name=127.0.0.1, port=8102, dc: localdc, status: Up] Unexpected end of stream.
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:212 - Remove host: Successfully removed host 127.0.0.1 from connection pool
2016-07-01 18:15:35 INFO  ConnectionPoolImpl:612 - unregistered mbean com.netflix.dyno.connectionpool.impl:type=MonitorConsole

BUILD SUCCESSFUL

Total time: 5.769 secs

This build could be faster, please consider using the Gradle Daemon: http://gradle.org/docs/2.4/userguide/gradle_daemon.html
diego@4winds:~/github/diegopacheco/dynomite-cluster-checker/dynomite-cluster-checker$ 
```

Cheers,
Diego Pacheco (@diego_pacheco)
