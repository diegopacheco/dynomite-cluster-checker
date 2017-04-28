package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.context.NodeCheckerResponse;
import com.github.diegopacheco.dynomite.cluster.checker.util.Chronometer;
import com.github.diegopacheco.dynomite.cluster.config.DynomiteConfig;

/**
 * CheckClusterFailoverTask test if the cluster failover os working fine.
 * 
 * @author diegopacheco
 *
 */
public class CheckClusterFailoverTask implements Task{
	
	@Override
	public void execute(ExecutionContext ec) {
		
		Chronometer stopWatch = new Chronometer();
		stopWatch.start();

		NodeCheckerResponse nodeReport = new NodeCheckerResponse();
		try{
			nodeReport.setSeeds(ec.getRawSeeds());
			nodeReport.setServer(ec.getOnlineNodes().toString());
			
			try{
				ec.getWholeClusterClient().set(DynomiteConfig.TEST_FAILOVER_KEY,DynomiteConfig.TEST_FAILOVER_VALUE);
			}catch(Throwable t){
				nodeReport.setInsertError(t.getMessage());
			}
			
			String result = ec.getWholeClusterClient().get(DynomiteConfig.TEST_FAILOVER_KEY);
			
			stopWatch.stop();
			nodeReport.setGetTime(stopWatch.getDiffAsString());
			
			if(result!= null && (!"".equals(result))){
				nodeReport.setConsistency(true);
				ec.getExecutionReport().setFailoverStatus("OK");
			}else{
				nodeReport.setConsistency(false);
				ec.getExecutionReport().setFailoverStatus("Inconsistent. Expected: " + DynomiteConfig.TEST_FAILOVER_VALUE + ", Got: " + result + " - info: " + nodeReport.toString());
			}
			
			
		}catch(Throwable t){
			System.out.println("Cloud not insert data into the cluster. EX: " + t);
			nodeReport.setConsistency(false);
			nodeReport.setGetError(t.getMessage());
			ec.getExecutionReport().setFailoverStatus("Error! EX: " + t.getMessage() + " - info: " + nodeReport.toString() );
		}
		
	}

}
