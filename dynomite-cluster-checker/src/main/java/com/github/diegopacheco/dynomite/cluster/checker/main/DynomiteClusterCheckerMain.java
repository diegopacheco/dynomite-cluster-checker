package com.github.diegopacheco.dynomite.cluster.checker.main;

import com.github.diegopacheco.dynomite.cluster.checker.tasks.engine.DCCTaskExecutionEngine;
import com.github.diegopacheco.dynomite.cluster.checker.util.Chronometer;

/**
 * This utility lass connects to a dynomite cluster and sends data to check if
 * the replication is working.
 * 
 * @author diegopacheco
 * @since 01/07/2016
 * @version 1.0
 *
 */
public class DynomiteClusterCheckerMain {

	public static void main(String[] args) {
		Chronometer stopWatch = new Chronometer();
		try {
			stopWatch.start();
			Boolean isTelemetryMode = isTelemtryModeSet(args) ? true : false;
			
			DCCTaskExecutionEngine dcc = new DCCTaskExecutionEngine();
			String jsonResult = dcc.run(args[0], isTelemetryMode);
			
			System.out.println(jsonResult);
		} finally {
			stopWatch.stop();
			
			System.out.println("--");
			System.out.println(stopWatch.getDiffAsSecondsString());
			
			System.exit(0);
		}
	}

	private static boolean isTelemtryModeSet(String[] args) {
		if (args.length < 2)
			return false;
		String parameter = args[1];
		return (parameter.equalsIgnoreCase("false") || parameter.equalsIgnoreCase("true"));
	}

}
