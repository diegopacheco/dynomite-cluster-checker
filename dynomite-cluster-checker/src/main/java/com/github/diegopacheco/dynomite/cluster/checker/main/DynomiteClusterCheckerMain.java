package com.github.diegopacheco.dynomite.cluster.checker.main;

import org.apache.log4j.Logger;

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
	
	private static final Logger logger = Logger.getLogger(DynomiteClusterCheckerMain.class);
	
	public static void main(String[] args) {
		Chronometer stopWatch = new Chronometer();
		try {

			stopWatch.start();
			Boolean isTelemetryMode = isTelemtryModeSet(args) ? true : false;
			
			DCCTaskExecutionEngine dcc = new DCCTaskExecutionEngine();
			String jsonResult = dcc.run(args[0], isTelemetryMode);
			
			logger.info(jsonResult);
		}catch(Exception e){
			logger.error("Error: " + e);
		} finally {
			stopWatch.stop();
			
			logger.info("--");
			logger.info(stopWatch.getDiffAsSecondsString());
			
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
