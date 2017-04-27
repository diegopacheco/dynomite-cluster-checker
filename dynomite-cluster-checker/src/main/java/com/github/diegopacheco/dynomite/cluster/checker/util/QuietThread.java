package com.github.diegopacheco.dynomite.cluster.checker.util;

/**
 * QuietThread does Thread.sleep without a try/catch.
 * 
 * @author diegopachedo
 *
 */
public class QuietThread {
	
	public static void sleep(Long time){
		try {
			Thread.sleep(time);
		} catch (Throwable t) {
			// by design does nothing.
		}
	}
	
}
