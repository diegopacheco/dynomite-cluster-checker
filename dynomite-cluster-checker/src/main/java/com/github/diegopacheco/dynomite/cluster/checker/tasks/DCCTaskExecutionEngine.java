package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import java.util.LinkedList;
import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
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
		tasks.add(new CleanUpTask());
		
		for(Task t : tasks){
			t.execute(ec);
		}
		
		stopWatch.stop();
		ec.setTimeToRunDCC(stopWatch.getDiffAsString());
		new GetJsonReportResultTask().execute(ec);

		return ec.getJsonResult();
	}
	
	public static void main(String[] args) {
		System.out.println(new DCCTaskExecutionEngine().run("172.18.0.201:8101:rack1:dc:100|172.18.0.202:8101:rack2:dc:100|172.18.0.203:8101:rack3:dc:100", false));
	}
	
}
