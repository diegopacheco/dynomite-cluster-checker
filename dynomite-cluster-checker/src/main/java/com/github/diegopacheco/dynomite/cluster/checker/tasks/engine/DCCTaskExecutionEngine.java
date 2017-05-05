package com.github.diegopacheco.dynomite.cluster.checker.tasks.engine;

import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.GetJsonReportResultTask;
import com.github.diegopacheco.dynomite.cluster.checker.tasks.Task;
import com.github.diegopacheco.dynomite.cluster.checker.util.Chronometer;
import com.github.diegopacheco.dynomite.cluster.config.GuiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

/**
 * DCCTaskExecutionEngine executes all tasks in DCC.
 * 
 * @author diegopacheco
 *
 */
public class DCCTaskExecutionEngine {
	
	@SuppressWarnings("unchecked")
	public String run(String seeds, boolean telemetryMode) {
		
		Injector injector = Guice.createInjector(new GuiceModule());
		
		ExecutionContext ec = injector.getInstance(ExecutionContext.class);
		
		Chronometer stopWatch = injector.getInstance(Chronometer.class);
		stopWatch.start();
		
		ec.setRawSeeds(seeds);
		ec.setIsTelemetryMode(telemetryMode);
		
		List<Task> tasks = injector.getInstance(Key.get(List.class, Names.named("tasks")));
		
		for(Task t : tasks){
			t.execute(ec);
		}
		
		stopWatch.stop();
		ec.getExecutionReport().setTimeToRun(stopWatch.getDiffAsString());
		
		GetJsonReportResultTask jsonFinalReportTask = injector.getInstance(GetJsonReportResultTask.class);
		jsonFinalReportTask.execute(ec);
		
		return ec.getExecutionReport().getJsonResult();
	}

}
