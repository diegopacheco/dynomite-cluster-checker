package com.github.diegopacheco.dynomite.dyno.connection.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

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

public class SimpleConnectionTest {

	//@Ignore
	@Test
	/**
	 * Should have the same result(connectivity-like) as: 
 	 *     ./gradlew execute -Dexec.args="127.0.0.1:8102:rack1:local-dc:100"
     *
	 */
	public void testConnection(){
		
		String clusterName = "local-cluster";
		
		DynomiteNodeInfo node = new DynomiteNodeInfo("127.0.0.1","8102","rack1","local-dc","100");
		
		DynoJedisClient dynoClient = new DynoJedisClient.Builder()
				.withApplicationName(DynomiteConfig.CLIENT_NAME)
	            .withDynomiteClusterName(clusterName)
	            //.withPort(8101)
	            .withCPConfig( new ArchaiusConnectionPoolConfiguration(DynomiteConfig.CLIENT_NAME)
	            					.withTokenSupplier(toTokenMapSupplier(Arrays.asList(node)))
	            					.setMaxConnsPerHost(1)
                                    .setConnectTimeout(2000)
                                    .setPoolShutdownDelay(0)
                                    .setFailOnStartupIfNoHosts(true)
                                    .setFailOnStartupIfNoHostsSeconds(2)
                                    .setMaxTimeoutWhenExhausted(2000)
                                    .setSocketTimeout(2000)
                                    .setRetryPolicyFactory(new RetryNTimes.RetryFactory(1))
	            )
	            .withHostSupplier(toHostSupplier(Arrays.asList(node)))
	            .build();

		dynoClient.set("Z", "200");
		System.out.println("Z: " + dynoClient.get("Z"));
		
	}
	
	private static TokenMapSupplier toTokenMapSupplier(List<DynomiteNodeInfo> nodes){
		StringBuilder jsonSB = new StringBuilder("[");
		int count = 0;
		for(DynomiteNodeInfo node: nodes){
			jsonSB.append(" {\"token\":\""+ node.getTokens() 
			                + "\",\"hostname\":\"" + node.getServer() 
							+ "\",\"zone\":\"" +  node.getDc()
							+ "\"} ");
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
			hosts.add(buildHost(node));
		}
		
		final HostSupplier customHostSupplier = new HostSupplier() {
		   @Override
		   public Collection<Host> getHosts() {
			   return hosts;
		   }
		};
		return customHostSupplier;
	}
	
	private static Host buildHost(DynomiteNodeInfo node){
		Host host = new Host(node.getServer(),8102,node.getDc());
		host.setStatus(Status.Up);
		return host;
	}
	
	
}
