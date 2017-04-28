package com.github.diegopacheco.dynomite.cluster.checker.util;

import java.util.concurrent.TimeUnit;

/**
 * Chronometer utility class to benchmark time.
 * 
 * @author diegopacheco
 *
 */
public class Chronometer {
	
	private Long init = 0L; 
	private Long end = 0L;
	
	public Long start(){
		this.init = System.currentTimeMillis();
		return this.init;
	}
	
	public Long stop(){
		this.end = System.currentTimeMillis();
		return this.end;
	}
	
	public Long getDiffAsLong(){
		return TimeUnit.MILLISECONDS.toSeconds((end - init));
	}
	
	public String getDiffAsString(){
		return getDiffAsLong() + "";
	}
	
	public String getDiffAsSecondsString(){
		return "TIME TO RUN: " + getDiffAsLong() + " seconds";
	}
	
	@Override
	public String toString() {
		return "Start: " + init + ", End: " + end + " - DIFF: " + getDiffAsString();
	}
	
}
