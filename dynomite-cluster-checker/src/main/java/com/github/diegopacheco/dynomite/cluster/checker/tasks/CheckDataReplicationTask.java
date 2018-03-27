package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import org.apache.log4j.Logger;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.context.NodeCheckerResponse;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.github.diegopacheco.dynomite.cluster.checker.util.Chronometer;
import com.github.diegopacheco.dynomite.cluster.checker.util.KeyValueGnerator;

/**
 * CheckDataReplicationTask verify if all dynomite is doing data replciation properly. 
 * 
 * @author diegopacheco
 *
 */
public class CheckDataReplicationTask implements Task {
	
	private static final Logger logger = Logger.getLogger(CheckDataReplicationTask.class);
	
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
				
				String result = node.getNodeClient().get(ec.getReplicationKey());
				if(result!= null && (!"".equals(result))){
					count++;
					nodeReport.setConsistency(true);
				}else{
					nodeReport.setConsistency(false);
				}
					
			}catch(Throwable t){
				logger.error("Could not get KEY on node : " + node + " - EX: " + t);
				nodeReport.setGetError(t.getMessage());
			}finally{
				stopWatch.stop();
				nodeReport.setGetTime(stopWatch.getDiffAsString());
				ec.getExecutionReport().getNodesReport().add(nodeReport);
				nodeReport = new NodeCheckerResponse();
			}
		}
		ec.getExecutionReport().setReplicationCount(count);
		
	}

	private void insertKey(ExecutionContext ec) {
		Chronometer stopWatch = new Chronometer();
		stopWatch.start();

		NodeCheckerResponse nodeReport = new NodeCheckerResponse();
		try{
			nodeReport.setSeeds(ec.getRawSeeds());
			nodeReport.setServer(ec.getOnlineNodes().toString());
			
			String replicationKey   = KeyValueGnerator.generateKey();
			String replicationValue = KeyValueGnerator.generateValue();
			
			ec.getWholeClusterClient().set(replicationKey,replicationValue);
			
			nodeReport.setConsistency(true);
			nodeReport.setConsistencyRedis(true);
			ec.setReplicationKey(replicationKey);
			ec.setReplicationValue(replicationValue);
			
		}catch(Throwable t){
			logger.error("Cloud not insert data into the cluster. EX: " + t);
			nodeReport.setConsistency(false);
			nodeReport.setInsertError(t.getMessage());
		}
		stopWatch.stop();
		nodeReport.setInsertTime(stopWatch.getDiffAsString());
		ec.getExecutionReport().getNodesReport().add(nodeReport);
		
	}
	
}	
