package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import org.apache.log4j.Logger;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.context.NodeCheckerResponse;
import com.github.diegopacheco.dynomite.cluster.checker.util.Chronometer;
import com.github.diegopacheco.dynomite.cluster.checker.util.KeyValueGnerator;

/**
 * CheckClusterFailoverTask test if the cluster failover os working fine.
 * 
 * @author diegopacheco
 *
 */
public class CheckClusterFailoverTask implements Task{
	
	private static final Logger logger = Logger.getLogger(CheckClusterFailoverTask.class);
	
	@Override
	public void execute(ExecutionContext ec) {
		
		Chronometer stopWatch = new Chronometer();
		stopWatch.start();

		NodeCheckerResponse nodeReport = new NodeCheckerResponse();
		try{
			nodeReport.setSeeds(ec.getRawSeeds());
			nodeReport.setServer(ec.getOnlineNodes().toString());
			
			try{
				
				String failOverKey   = KeyValueGnerator.generateKey();
				String failOverValue = KeyValueGnerator.generateValue(); 
				
				ec.setFailOverKey(failOverKey);
				ec.setFailOverValue(failOverValue);
				ec.getWholeClusterClient().set(failOverKey , failOverValue);
			}catch(Throwable t){
				nodeReport.setInsertError(t.getMessage());
			}
			
			String result = ec.getWholeClusterClient().get(ec.getFailOverKey());
			
			stopWatch.stop();
			nodeReport.setGetTime(stopWatch.getDiffAsString());
			
			if(result!= null && (!"".equals(result)) && ec.getFailOverValue().equals(result) ){
				nodeReport.setConsistency(true);
				ec.getExecutionReport().setFailoverStatus("OK");
			}else{
				nodeReport.setConsistency(false);
				ec.getExecutionReport().setFailoverStatus("Inconsistent. Expected: " + ec.getFailOverValue() + ", Got: " + result + " - info: " + nodeReport.toString());
			}
			
			
		}catch(Throwable t){
			logger.error("Cloud not insert data into the cluster. EX: " + t);
			nodeReport.setConsistency(false);
			nodeReport.setGetError(t.getMessage());
			ec.getExecutionReport().setFailoverStatus("Error! EX: " + t.getMessage() + " - info: " + nodeReport.toString() );
		}
		
	}

}
