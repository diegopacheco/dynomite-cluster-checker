package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.context.NodeCheckerResponse;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.github.diegopacheco.dynomite.cluster.checker.util.Chronometer;
import com.github.diegopacheco.dynomite.cluster.config.DynomiteConfig;

/**
 * CheckDataReplicationTask verify if all dynomite is doing data replciation properly. 
 * 
 * @author diegopacheco
 *
 */
public class CheckDataReplicationTask implements Task {
	
	@Override
	public void execute(ExecutionContext ec) {
		
		insertKey(ec);
		getKeys(ec);
		
	}

	private void getKeys(ExecutionContext ec) {
		Chronometer stopWatch = new Chronometer();
		stopWatch.start();
		
		NodeCheckerResponse nodeReport = new NodeCheckerResponse();
		
		int count = 0;
		for(DynomiteNodeInfo node : ec.getOnlineNodes()){
			try{
				
				nodeReport.setSeeds(node.toString());
				nodeReport.setServer(node.getServer());
				
				String result = node.getNodeClient().get(DynomiteConfig.TEST_KEY);
				if(result!= null && (!"".equals(result))){
					count++;
					nodeReport.setConsistency(true);
				}else{
					nodeReport.setConsistency(false);
				}
					
			}catch(Throwable t){
				System.out.println("Could not get KEY on node : " + node + " - EX: " + t);
				nodeReport.setGetError(t.getMessage());
			}finally{
				stopWatch.stop();
				nodeReport.setGetTime(stopWatch.getDiffAsString());
				ec.getExecutionReport().getNodesReport().add(nodeReport);
				nodeReport = new NodeCheckerResponse();
			}
		}
		System.out.println("Replication Count == " + count);
		
	}

	private void insertKey(ExecutionContext ec) {
		Chronometer stopWatch = new Chronometer();
		stopWatch.start();

		NodeCheckerResponse nodeReport = new NodeCheckerResponse();
		try{
			nodeReport.setSeeds(ec.getRawSeeds());
			nodeReport.setServer(ec.getOnlineNodes().toString());
			
			ec.getWholeClusterClient().set(DynomiteConfig.TEST_KEY,DynomiteConfig.TEST_VALUE);
			
			nodeReport.setConsistency(true);
			
		}catch(Throwable t){
			System.out.println("Cloud not insert data into the cluster. EX: " + t);
			nodeReport.setConsistency(false);
			nodeReport.setInsertError(t.getMessage());
		}
		stopWatch.stop();
		nodeReport.setInsertTime(stopWatch.getDiffAsString());
		ec.getExecutionReport().getNodesReport().add(nodeReport);
		
	}
	
}	
