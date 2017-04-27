package com.github.diegopacheco.dynomite.cluster.checker.cluster;

import java.util.Arrays;
import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.netflix.config.ConfigurationManager;
import com.netflix.dyno.connectionpool.impl.RetryNTimes;
import com.netflix.dyno.contrib.ArchaiusConnectionPoolConfiguration;
import com.netflix.dyno.jedis.DynoJedisClient;

/**
 * This class manages connection with Dynomite Cluster. Connects to a single
 * node in the cluster by design.
 * 
 * @author diegopachco
 * @since 01/07/2016
 * @version 1.0
 *
 */
public class DCCConnectionManager {
	
	public static DynoJedisClient createSingleNodeCluster(String clusterName, DynomiteNodeInfo node) {
		return createCluster(clusterName, Arrays.asList(node));
	}

	public static DynoJedisClient createCluster(String clusterName,final List<DynomiteNodeInfo> nodes){
		
		if (nodes.get(0)!=null){
			ConfigurationManager.getConfigInstance().setProperty("EC2_AVAILABILITY_ZONE",nodes.get(0).getRack());
			ConfigurationManager.getConfigInstance().setProperty("EC2_REGION",nodes.get(0).getRack());
		}

		ConfigurationManager.getConfigInstance().setProperty("dyno." + clusterName + ".retryPolicy","RetryNTimes:1:true");
		
		DynoJedisClient dynoClient = new DynoJedisClient.Builder().withApplicationName(clusterName)
		.withDynomiteClusterName(clusterName)
		.withCPConfig(
				new ArchaiusConnectionPoolConfiguration(clusterName)
					.withTokenSupplier(TokenMapSupplierFactory.build(nodes))
					.setMaxConnsPerHost(1)
					//.setConnectTimeout(5000)
				    .setRetryPolicyFactory(new RetryNTimes.RetryFactory(1,true))
		)
		.withHostSupplier(HostSupplierFactory.build(nodes))
		.build();
		
		return dynoClient;
	}

}
