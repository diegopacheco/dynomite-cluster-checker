package com.github.diegopacheco.dynomite.cluster.checker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.diegopacheco.dynomite.cluster.checker.cluster.DynomiteClusterConnectionManager;
import com.github.diegopacheco.dynomite.cluster.checker.hystrix.DynomiteSingleNodeCommand;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteSeedsParser;
import com.github.diegopacheco.dynomite.cluster.checker.util.ListJsonPrinter;
import com.google.common.collect.Lists;
import com.netflix.dyno.jedis.DynoJedisClient;
import com.netflix.hystrix.Hystrix;

/**
 * This utility lass connects to a dynomite cluster and sends data to check if the replication is working.
 * 
 * @author diegopacheco
 * @since 01/07/2016
 * @version 1.0
 *
 */
public class DynomiteClusterCheckerMain {
	
		private StringBuffer bufferdLogger = new StringBuffer();
		private ResultReport resultReport = new ResultReport();
	
		public static void main(String[] args){
			Long init = System.currentTimeMillis();
			try{
				DynomiteClusterCheckerMain dcc = new DynomiteClusterCheckerMain();
				//dcc.run(args[0],false);
				//dcc.run("200.56.0.1:8101:rack1:local-dc:437425602",true);
				dcc.run("227.0.0.1:8101:rack1:local-dc:437425602|127.0.0.1:8101:rack1:local-dc:437425602",false);
				//dcc.run("jack.cats.com:8101:rack1:local-dc:437425602",false);
			}finally {
				Long end = System.currentTimeMillis();
				System.out.println("--");
				System.out.println( "TIME TO RUN: " + TimeUnit.MILLISECONDS.toSeconds((end-init)) + " seconds");
			}
		}
		
		public String run(String seeds,boolean telemetryMode){
			
			Long init = System.currentTimeMillis();
			
			resultReport.setNodesReport(new ArrayList<>());
			CheckerResponse checkerResponse = new CheckerResponse();
			
			bufferedLogInfo(""); 
			bufferedLogInfo("**** BEGIN DYNOMITE CLUSTER CHECKER ****"); 
			bufferedLogInfo("1. Checking cluster connection... ");
				
			List<DynomiteNodeInfo> nodes      =  DynomiteSeedsParser.parse(seeds);
			List<DynomiteNodeInfo> validNodes =  checkAllNodes(DynomiteConfig.CLUSTER_NAME, nodes);
			List<DynomiteNodeInfo> badNodes   =  Lists.newArrayList(nodes);
			badNodes.removeAll(validNodes);
			
			if(badNodes!=null && badNodes.size() >= 1 ){
				resultReport.setBadNodes(badNodes);
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
				resultReport.getNodesReport().add(checkerResponse);
				
				List<DynomiteNodeInfo> otherSeeds = Lists.newArrayList(validNodes);
				otherSeeds.remove(0);
				if (otherSeeds.size() >= 1){
					for(DynomiteNodeInfo node : otherSeeds){
						checkerResponse = new CheckerResponse();
						checkNode(false,node,node.getServer(),checkerResponse);
						resultReport.getNodesReport().add(checkerResponse);
					}
				}
				
				bufferedLogInfo("3. Checking cluster failover... ");
				String failoverStatus = checkClusterFailOver(DynomiteConfig.CLUSTER_NAME,nodes);
				resultReport.setFailoverStatus(failoverStatus);
				bufferedLogInfo("All Seeds Cluster Failover test: " + failoverStatus);
				
			}
			
			Long end = System.currentTimeMillis();
			String timeToRun = TimeUnit.MILLISECONDS.toSeconds((end-init)) + "";
			resultReport.setTimeToRun(timeToRun);
			
			bufferedLogInfo("4. Results as JSON... ");
			String jsonResult = (telemetryMode) ? ListJsonPrinter.printTelemetry(resultReport) : ListJsonPrinter.print(resultReport);
			bufferedLogInfo(jsonResult);
			bufferedLogInfo("**** END DYNOMITE CLUSTER CHECKER ****");
			bufferedLogPrint();
			
			cleanUp(DynomiteConfig.CLUSTER_NAME,validNodes);
			return jsonResult;
		}
		
		private void checkNode(boolean primary,DynomiteNodeInfo node ,String server,CheckerResponse checkerResponse) {
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
		
		private  void bufferedLogInfo(String msg){
			bufferdLogger.append(msg + "\r\n");
		}
		
		private void bufferedLogPrint(){
			System.out.println(bufferdLogger.toString());
		}
		
		private List<DynomiteNodeInfo> checkAllNodes(String clusterName,List<DynomiteNodeInfo> nodes){
			List<DynomiteNodeInfo> validNodes = new ArrayList<>();
			for(DynomiteNodeInfo node : nodes){
				try{
					DynomiteSingleNodeCommand cmd = new DynomiteSingleNodeCommand(clusterName, node); 
					boolean isNodeOkay = cmd.execute();
					if (isNodeOkay)
						validNodes.add(node);
				}catch(Exception e){
					System.out.println("Could not Connet on Node: " + node + " EX: " + e);
				}finally{
					Hystrix.reset();
				}
			}
			return validNodes;
		}
		
		private void cleanUp(String clusterName,List<DynomiteNodeInfo> seeds){
			DynoJedisClient cluster = null;
			for(DynomiteNodeInfo node : seeds){
				try{
					cluster = DynomiteClusterConnectionManager.createSingleNodeCluster(clusterName,node);
					cluster.del(DynomiteConfig.TEST_KEY);
				}catch(Exception e){
					System.out.println("Could not clean up cluster: " + clusterName + " Node: " + node);
				}finally {
					if (cluster!=null)
						cluster.stopClient();
				}
			}
		}
		
		private String checkClusterFailOver(String clusterName,List<DynomiteNodeInfo> nodes){
			DynoJedisClient cluster = null;
			String returnResult = "OK";
			try{
				cluster = DynomiteClusterConnectionManager.createCluster(clusterName,nodes);
				cluster.set(DynomiteConfig.TEST_FAILOVER_KEY, DynomiteConfig.TEST_FAILOVER_VALUE);
				Thread.sleep(2000);
				String result = cluster.get(DynomiteConfig.TEST_FAILOVER_KEY);
				cluster.del(DynomiteConfig.TEST_FAILOVER_KEY);
				if (result == null || ("".equals(result)) || (!DynomiteConfig.TEST_FAILOVER_VALUE.equals(result)) )
					returnResult = "FAIL: get value missmatch! Expected: " + DynomiteConfig.TEST_FAILOVER_VALUE + " GOT: " + result;
				
			}catch(Exception e){
				System.out.println("Could not Connet on Cluster: " + nodes + " EX: " + e);
				returnResult = "FAIL: " + e.getMessage();
			}finally {
				if (cluster!=null)
					cluster.stopClient();
			}
			return returnResult;
		}
		
		private void insert(String key,String value,String clusterName,DynomiteNodeInfo node,CheckerResponse checkerResponse) {
			DynoJedisClient cluster = null;
			try{
				cluster = DynomiteClusterConnectionManager.createSingleNodeCluster(clusterName,node);
				double init = System.currentTimeMillis();
				cluster.set(key, value);
				double end = System.currentTimeMillis();
				checkerResponse.setInsertTime((end-init) + " ms");
				printBench("  Insert " + key + " - Value: " + value,init,end);
			}catch(Exception e){
				checkerResponse.cleanUp();
				checkerResponse.setInsertError(e.getMessage());
			}finally {
				if (cluster!=null)
					cluster.stopClient();
			}
		}
		
		private String get(String key,String clusterName,DynomiteNodeInfo node,CheckerResponse checkerResponse) {
			DynoJedisClient cluster = null;
			try{	
				cluster = DynomiteClusterConnectionManager.createSingleNodeCluster(clusterName,node);
				double init = System.currentTimeMillis();
				String result = cluster.get(key);
				double end = System.currentTimeMillis();
				checkerResponse.setGetTime((end-init) + " ms");
				printBench("  Get: " + key + " ",init,end);
				return result;
			}catch(Exception e){
				checkerResponse.cleanUp();
				checkerResponse.setGetError((e.getCause().getMessage()));
				return "ERROR " + e.getMessage();
			}finally {
				if (cluster!=null)
					cluster.stopClient();
			}
		}
		
		private void printBench(String msg,double init, double end){
			int seconds = (int) ((end - init) / 1000) % 60 ;
			bufferedLogInfo("  TIME to " + msg + ": " + (end - init) + " ms - " + seconds + " s" );
		}
}
