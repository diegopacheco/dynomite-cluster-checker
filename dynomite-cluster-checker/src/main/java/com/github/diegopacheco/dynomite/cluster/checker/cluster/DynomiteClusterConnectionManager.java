package com.github.diegopacheco.dynomite.cluster.checker.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.netflix.dyno.connectionpool.Host;
import com.netflix.dyno.connectionpool.Host.Status;
import com.netflix.dyno.connectionpool.HostSupplier;
import com.netflix.dyno.connectionpool.TokenMapSupplier;
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
	
	public static DynoJedisClient createCluster(String clusterName,DynomiteNodeInfo node){
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
					.withApplicationName("DynomiteClusterChecker")
		            .withDynomiteClusterName(clusterName)
		            .withCPConfig( new ArchaiusConnectionPoolConfiguration("DynomiteClusterChecker")
		            					.setPort(8101)
		            					.setLocalDC(node.getDc())
		            					.withTokenSupplier(testTokenMapSupplier)
		            					.setMaxConnsPerHost(100) )
		            .withHostSupplier(customHostSupplier)
		            .build();
		return dynoClient;
	}
	
}
