package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import java.util.ArrayList;
import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.DynomiteConfig;
import com.github.diegopacheco.dynomite.cluster.checker.cluster.DCCConnectionManager;
import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.google.common.collect.Lists;
import com.netflix.dyno.jedis.DynoJedisClient;

/**
 * CheckNodesConnectivityTask verify if all dynomite nodes are accessible. 
 * 
 * @author diegopacheco
 *
 */
public class CheckNodesConnectivityTask implements Task {
	
	@Override
	public void execute(ExecutionContext ec) {
		
		List<DynomiteNodeInfo> onlineNodes = checkNodeConnectivity(ec);
		getOfflineNodes(ec, onlineNodes);
		connectWholeCluster(ec);

	}

	private void connectWholeCluster(ExecutionContext ec) {
		try {
			
			DynoJedisClient client = DCCConnectionManager.createCluster(DynomiteConfig.CLUSTER_NAME,ec.getOnlineNodes());
			String prefix = "awesomeSbrubles_";
			client.get(prefix);
			ec.setWholeClusterClient(client);
			
		} catch (Throwable t) {
			System.out.println("Could not Connet on Whole cluster : " + ec.getOnlineNodes() + " EX: " + t);
		} finally {
		}
	}

	private List<DynomiteNodeInfo> checkNodeConnectivity(ExecutionContext ec) {
		List<DynomiteNodeInfo> onlineNodes = new ArrayList<>();
		for (DynomiteNodeInfo node : ec.getOriginalNodes()) {
			try {
				
				DynoJedisClient client = DCCConnectionManager.createSingleNodeCluster(DynomiteConfig.CLUSTER_NAME,node);
				String prefix = "awesomeSbrubles_";
				client.get(prefix);
				
				node.setNodeClient(client);
				onlineNodes.add(node);
				
			} catch (Throwable t) {
				System.out.println("Could not Connet on Node: " + node + " EX: " + t);
			} finally {
			}
		}
		ec.setOnlineNodes(onlineNodes);
		return onlineNodes;
	}
	
	private void getOfflineNodes(ExecutionContext ec, List<DynomiteNodeInfo> onlineNodes) {
		List<DynomiteNodeInfo> offlineNodes = Lists.newArrayList(ec.getOriginalNodes());
		offlineNodes.removeAll(onlineNodes);
		ec.setOfflineNodes(offlineNodes);
	}
	
}	
