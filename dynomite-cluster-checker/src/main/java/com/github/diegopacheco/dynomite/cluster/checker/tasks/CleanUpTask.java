package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import org.apache.log4j.Logger;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;

/**
 * CleanUpTask this task will clean up all previous dynomite tasks side effects
 * 
 * @author diegopacheco
 *
 */
public class CleanUpTask implements Task {

	private static final Logger logger = Logger.getLogger(CleanUpTask.class);
	
	@Override
	public void execute(ExecutionContext ec) {
		cleanUpWholeClusterConnection(ec);
		cleanUpNodesConnections(ec);
	}

	private void cleanUpNodesConnections(ExecutionContext ec) {
		for (DynomiteNodeInfo node : ec.getOnlineNodes()) {
			try {
				node.getNodeClient().del(ec.getReplicationKey());
			} catch (Throwable t) {
				logger.error("Cloud not clean up data on node. EX: " + t + " node: " + node);
			}
		}
	}

	private void cleanUpWholeClusterConnection(ExecutionContext ec) {
		try {
			ec.getWholeClusterClient().del(ec.getFailOverKey());
		} catch (Throwable t) {
			logger.error("Cloud not clean up data on whole cluster. EX: " + t);
		}
	}

}
