package com.github.diegopacheco.dynomite.cluster.checker.tasks.engine;

import java.util.LinkedList;
import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.CheckClusterFailoverTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.CheckDataReplicationTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.CheckNodesConnectivityTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.CleanUpTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.GetJsonReportResultTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.SetupTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.Task;
import com.github.diegopacheco.dynomite.cluster.checker.util.Chronometer;

/**
 * DCCTaskExecutionEngine executes all tasks in DCC.
 * 
 * @author diegopacheco
 *
 */
public class DCCTaskExecutionEngine {
	
	public String run(String seeds, boolean telemetryMode) {
		
		Chronometer stopWatch = new Chronometer();
		stopWatch.start();
		
		ExecutionContext ec = new ExecutionContext();
		ec.setRawSeeds(seeds);
		ec.setIsTelemetryMode(telemetryMode);
		
		List<Task> tasks = new LinkedList<>();
		tasks.add(new SetupTask());
		tasks.add(new CheckNodesConnectivityTask());
		tasks.add(new CheckDataReplicationTask());
		tasks.add(new CheckClusterFailoverTask());
		tasks.add(new CleanUpTask());
		
		for(Task t : tasks){
			t.execute(ec);
		}
		
		stopWatch.stop();
		ec.getExecutionReport().setTimeToRun(stopWatch.getDiffAsString());
		
		new GetJsonReportResultTask().execute(ec);
		return ec.getExecutionReport().getJsonResult();
	}

}
