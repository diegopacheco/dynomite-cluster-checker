package com.github.diegopacheco.dynomite.cluster.checker;

import java.util.ArrayList;
import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.cluster.DynomiteClusterConnectionManager;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteSeedsParser;
import com.github.diegopacheco.dynomite.cluster.checker.util.ListJsonPrinter;
import com.google.common.collect.Lists;
import com.netflix.dyno.jedis.DynoJedisClient;

/**
 * This utility lass connects to a dynomite cluster and sends data to check if the replication is working.
 * 
 * @author diegopacheco
 * @since 01/07/2016
 * @version 1.0
 *
 */
public class DynomiteClusterCheckerMain {
	
		private static final StringBuffer bufferdLogger = new StringBuffer();
		private static final List<CheckerResponse> clusterResponses = new ArrayList<>();
	
		public static void main(String[] args) throws Throwable {
			
			CheckerResponse checkerResponse = new CheckerResponse();
			
			bufferedLogInfo(""); 
			bufferedLogInfo("**** BEGIN DYNOMITE CLUSTER CHECKER ****"); 
			bufferedLogInfo("1. Checking cluster connection... ");
				
			List<DynomiteNodeInfo> nodes      =  DynomiteSeedsParser.parse(args[0]);
			List<DynomiteNodeInfo> validNodes =  checkAllNodes(DynomiteConfig.CLUSTER_NAME, nodes);
			List<DynomiteNodeInfo> badNodes   =  Lists.newArrayList(nodes);
			badNodes.removeAll(validNodes);
			
			if(badNodes!=null && badNodes.size() >= 1 ){
				bufferedLogInfo("BAD NODES:");
				for(DynomiteNodeInfo node: badNodes){
					bufferedLogInfo("    " + node.toString());
				}
			}else{
				bufferedLogInfo("    OK - All nodes are accessible! ");
			}
			
			if (validNodes==null || validNodes.size()<=0){
				bufferedLogInfo("2. Cannot check data replication since there are no valid nodes");
			}else{
				cleanUp(DynomiteConfig.CLUSTER_NAME,validNodes);
				
				bufferedLogInfo("2. Checking cluster data replication... ");
				bufferedLogInfo("SEEDS: " + validNodes.toString());
				checkerResponse.setSeeds(validNodes.toString());
				
				checkNode(true,validNodes.get(0),validNodes.get(0).getServer(),checkerResponse);
				clusterResponses.add(checkerResponse);
				
				List<DynomiteNodeInfo> otherSeeds = Lists.newArrayList(validNodes);
				otherSeeds.remove(0);
				if (otherSeeds.size() >= 1){
					for(DynomiteNodeInfo node : otherSeeds){
						checkerResponse = new CheckerResponse();
						checkNode(false,node,node.getServer(),checkerResponse);
						clusterResponses.add(checkerResponse);
					}
				}
				
				bufferedLogInfo("3. Checking cluster failover... ");
				String failoverStatus = checkClusterFailOver(DynomiteConfig.CLUSTER_NAME,nodes);
				checkerResponse.setFailoverStatus(failoverStatus);
				bufferedLogInfo("All Seeds Cluster Failover test: " + failoverStatus);
				
			}
			
			bufferedLogInfo("4. Shwoing Results as JSON... ");
			bufferedLogInfo(ListJsonPrinter.print(clusterResponses));
			bufferedLogInfo("**** END DYNOMITE CLUSTER CHECKER ****");
			bufferedLogPrint();
			
			cleanUp(DynomiteConfig.CLUSTER_NAME,validNodes);
			System.exit(0);
		}
		
		private static void checkNode(boolean primary,DynomiteNodeInfo node ,String server,CheckerResponse checkerResponse) {
			checkerResponse.setServer(node.getServer());
			bufferedLogInfo("Checking Node: " + checkerResponse.getServer());
			
			if(primary)
				insert(DynomiteConfig.TEST_KEY, DynomiteConfig.TEST_VALUE, DynomiteConfig.CLUSTER_NAME,node,checkerResponse);
			
			String result = get(DynomiteConfig.TEST_KEY, DynomiteConfig.CLUSTER_NAME,node,checkerResponse);
			if(DynomiteConfig.TEST_VALUE.equals(result)){
				checkerResponse.setConsistency(true);
				bufferedLogInfo("  200 OK - set/get working fine!");
			}else{
				checkerResponse.setConsistency(false);
				bufferedLogInfo("  ERROR - Inconsistency set/get! Set: " + DynomiteConfig.TEST_KEY +  "Get: " + result);
			}
		}
		
		private static void bufferedLogInfo(String msg){
			bufferdLogger.append(msg + "\r\n");
		}
		
		private static void bufferedLogPrint(){
			System.out.println(bufferdLogger.toString());
		}
		
		private static List<DynomiteNodeInfo> checkAllNodes(String clusterName,List<DynomiteNodeInfo> nodes){
			List<DynomiteNodeInfo> validNodes = new ArrayList<>();
			for(DynomiteNodeInfo node : nodes){
				try{
					DynoJedisClient cluster = DynomiteClusterConnectionManager.createSingleNodeCluster(clusterName,node);
					cluster.get("awesomeSbrubles");
					cluster.stopClient();
					validNodes.add(node);
				}catch(Exception e){
					System.out.println("Could not Connet on Node: " + node + " EX: " + e);
				}
			}
			return validNodes;
		}
		
		private static void cleanUp(String clusterName,List<DynomiteNodeInfo> seeds) throws Throwable {
			for(DynomiteNodeInfo node : seeds){
				try{
					DynoJedisClient cluster = DynomiteClusterConnectionManager.createSingleNodeCluster(clusterName,node);
					cluster.del(DynomiteConfig.TEST_KEY);
					cluster.stopClient();
				}catch(Exception e){
					System.out.println("Could not clean up cluster: " + clusterName + " Node: " + node);
				}
			}
		}
		
		private static String checkClusterFailOver(String clusterName,List<DynomiteNodeInfo> nodes){
			try{
				DynoJedisClient cluster = DynomiteClusterConnectionManager.createCluster(clusterName,nodes);
				cluster.set(DynomiteConfig.TEST_FAILOVER_KEY, DynomiteConfig.TEST_FAILOVER_VALUE);
				Thread.sleep(2000);
				String result = cluster.get(DynomiteConfig.TEST_FAILOVER_KEY);
				cluster.del(DynomiteConfig.TEST_FAILOVER_KEY);
				cluster.stopClient();
				if (result == null || ("".equals(result)) || (!DynomiteConfig.TEST_FAILOVER_VALUE.equals(result)) )
					return "FAIL: get value missmatch! Expected: " + DynomiteConfig.TEST_FAILOVER_VALUE + " GOT: " + result;
			}catch(Exception e){
				System.out.println("Could not Connet on Cluster: " + nodes + " EX: " + e);
				return "FAIL: " + e.getMessage();
			}
			return "OK";
		}
		
		private static void insert(String key,String value,String clusterName,DynomiteNodeInfo node,CheckerResponse checkerResponse) {
			try{
				DynoJedisClient cluster = DynomiteClusterConnectionManager.createSingleNodeCluster(clusterName,node);
				double init = System.currentTimeMillis();
				cluster.set(key, value);
				double end = System.currentTimeMillis();
				checkerResponse.setInsertTime((end-init) + " ms");
				printBench("  Insert " + key + " - Value: " + value,init,end);
				cluster.stopClient();
			}catch(Exception e){
				checkerResponse.cleanUp();
				checkerResponse.setInsertError(e.getMessage());
			}
		}
		
		private static String get(String key,String clusterName,DynomiteNodeInfo node,CheckerResponse checkerResponse) {
			try{	
				DynoJedisClient cluster = DynomiteClusterConnectionManager.createSingleNodeCluster(clusterName,node);
				double init = System.currentTimeMillis();
				String result = cluster.get(key);
				double end = System.currentTimeMillis();
				checkerResponse.setGetTime((end-init) + " ms");
				printBench("  Get: " + key + " ",init,end);
				cluster.stopClient();
				return result;
			}catch(Exception e){
				checkerResponse.cleanUp();
				checkerResponse.setGetError((e.getCause().getMessage()));
				return "ERROR " + e.getMessage();
			}
		}
		
		private static void printBench(String msg,double init, double end){
			int seconds = (int) ((end - init) / 1000) % 60 ;
			bufferedLogInfo("  TIME to " + msg + ": " + (end - init) + " ms - " + seconds + " s" );
		}
}
