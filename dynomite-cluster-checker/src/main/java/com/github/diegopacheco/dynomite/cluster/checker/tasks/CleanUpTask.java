package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.github.diegopacheco.dynomite.cluster.checker.util.QuietThread;
import com.github.diegopacheco.dynomite.cluster.config.DynomiteConfig;

/**
 * CleanUpTask this task will clean up all previous dynomite tasks side effects 
 * 
 * @author diegopacheco
 *
 */
public class CleanUpTask implements Task {
	
	@Override
	public void execute(ExecutionContext ec) {
		cleanUpWholeClusterConnection(ec);
		cleanUpNodesConnections(ec);
	}

	private void cleanUpNodesConnections(ExecutionContext ec) {
		for(DynomiteNodeInfo node : ec.getOnlineNodes()){
			node.getNodeClient().del(DynomiteConfig.TEST_KEY);
			//node.getNodeClient().stopClient();
		}
	}

	private void cleanUpWholeClusterConnection(ExecutionContext ec) {
		try{
			ec.getWholeClusterClient().del(DynomiteConfig.TEST_KEY);
			//QuietThread.sleep(2000L);
		}catch(Throwable t){
			System.out.println("Cloud not clean up data on whole cluster. EX: " + t);
		}
		
		try{
			//ec.getWholeClusterClient().stopClient();
		}catch(Throwable t){
			System.out.println("Cloud not STOP whole cluster. EX: " + t);
		}
	}
	
}	
