package com.github.diegopacheco.dynomite.cluster.checker.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.github.diegopacheco.dynomite.cluster.checker.DynomiteConfig;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.netflix.dyno.connectionpool.Host;
import com.netflix.dyno.connectionpool.Host.Status;
import com.netflix.dyno.connectionpool.HostSupplier;
import com.netflix.dyno.connectionpool.TokenMapSupplier;
import com.netflix.dyno.connectionpool.impl.RetryNTimes;
import com.netflix.dyno.connectionpool.impl.lb.AbstractTokenMapSupplier;
import com.netflix.dyno.contrib.ArchaiusConnectionPoolConfiguration;
import com.netflix.dyno.jedis.DynoJedisClient;

/**
 * This class manages connection with Dynomite Cluster. Connects to a single node in the cluster by design.
 * 
 * @author diegopachco
 * @since 01/07/2016
 * @version 1.0
 *
 */
public class DynomiteClusterConnectionManager {
	
	public static DynoJedisClient createSingleNodeCluster(String clusterName,DynomiteNodeInfo node){
		String ip = node.getServer();
		
		final HostSupplier customHostSupplier = new HostSupplier() {
			final List<Host> hosts = new ArrayList<Host>();
			   @Override
			   public Collection<Host> getHosts() {
			    hosts.add(new Host(ip, 22222, Status.Up).setRack(node.getDc()));
			    return hosts;
			   }
		};
		
		final String json = "["
								+ " {\"token\":\""+ node.getTokens() + "\",\"hostname\":\"" + ip + "\",\"zone\":\"" +  node.getDc() + "\"}, "
						+ " ]\"";
		TokenMapSupplier testTokenMapSupplier = new AbstractTokenMapSupplier() {
		    @Override
		    public String getTopologyJsonPayload(String hostname) {
		        return json;
		    }
			@Override
			public String getTopologyJsonPayload(Set<Host> activeHosts) {
				return json;
			}
		};
		
		DynoJedisClient dynoClient = new DynoJedisClient.Builder()
					.withApplicationName(DynomiteConfig.CLIENT_NAME)
		            .withDynomiteClusterName(clusterName)
		            .withCPConfig( new ArchaiusConnectionPoolConfiguration(DynomiteConfig.CLIENT_NAME)
		            					.setPort(8101)
		            					.setLocalRack(node.getRack())
		            					.setLocalDataCenter(node.getDc())
		            					.withTokenSupplier(testTokenMapSupplier)
		            					.setMaxConnsPerHost(1)
                                        .setConnectTimeout(2000)
                                        //.setPoolShutdownDelay(0)
                                        //.setFailOnStartupIfNoHosts(true)
                                        //.setFailOnStartupIfNoHostsSeconds(4)
                                        //.setMaxTimeoutWhenExhausted(4)
                                        //.setSocketTimeout(4)
                                        .setRetryPolicyFactory(new RetryNTimes.RetryFactory(1))
		            )
		            .withHostSupplier(customHostSupplier)
		            .build();
		return dynoClient;
	}
	
	public static DynoJedisClient createCluster(String clusterName,final List<DynomiteNodeInfo> nodes){
		DynoJedisClient dynoClient = new DynoJedisClient.Builder()
					.withApplicationName(DynomiteConfig.CLIENT_NAME)
		            .withDynomiteClusterName(clusterName)
		            // ConnectionPoolConfigurationImpl
		            .withCPConfig( new ArchaiusConnectionPoolConfiguration(DynomiteConfig.CLIENT_NAME)
		            					.setPort(8101)
		            					//.setLocalRack(nodes.get(0).getRack())
		            					//.setLocalDataCenter(nodes.get(0).getDc())
		            					.withTokenSupplier(toTokenMapSupplier(nodes))
		            					.setMaxConnsPerHost(1)
                                        .setConnectTimeout(2000)
                                        .setPoolShutdownDelay(0)
                                        .setFailOnStartupIfNoHosts(true)
                                        .setFailOnStartupIfNoHostsSeconds(2)
                                        .setMaxTimeoutWhenExhausted(2000)
                                        .setSocketTimeout(2000)
                                        .setRetryPolicyFactory(new RetryNTimes.RetryFactory(1))
		            )
		            .withHostSupplier(toHostSupplier(nodes))
		            .build();
		return dynoClient;
	}
	
	private static TokenMapSupplier toTokenMapSupplier(List<DynomiteNodeInfo> nodes){
		StringBuilder jsonSB = new StringBuilder("[");
		int count = 0;
		for(DynomiteNodeInfo node: nodes){
			jsonSB.append(" {\"token\":\""+ node.getTokens() + "\",\"hostname\":\"" + node.getServer() + "\",\"zone\":\"" +  node.getDc() + "\"} ");
			count++;
			if (count < nodes.size())
				jsonSB.append(" , ");
		}
		jsonSB.append(" ]\"");
		
	   final String json = jsonSB.toString();
	   TokenMapSupplier testTokenMapSupplier = new AbstractTokenMapSupplier() {
			    @Override
			    public String getTopologyJsonPayload(String hostname) {
			        return json;
			    }
				@Override
				public String getTopologyJsonPayload(Set<Host> activeHosts) {
					return json;
				}
		};
		return testTokenMapSupplier;
	}
	
	private static HostSupplier toHostSupplier(List<DynomiteNodeInfo> nodes){
		final List<Host> hosts = new ArrayList<Host>();
		
		for(DynomiteNodeInfo node: nodes){
			hosts.add(new Host(node.getServer(), 22222, Status.Up).setRack(node.getDc()));
		}
		
		final HostSupplier customHostSupplier = new HostSupplier() {
		   @Override
		   public Collection<Host> getHosts() {
			   return hosts;
		   }
		};
		return customHostSupplier;
	}
	
	
}
