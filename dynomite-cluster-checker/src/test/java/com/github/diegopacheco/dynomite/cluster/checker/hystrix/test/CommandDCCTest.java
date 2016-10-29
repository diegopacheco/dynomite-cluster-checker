package com.github.diegopacheco.dynomite.cluster.checker.hystrix.test;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.github.diegopacheco.dynomite.cluster.checker.hystrix.CommandDCC;

public class CommandDCCTest {
	
	@Test
	public void testHystrixCommandSync(){
		CommandDCC cmd = new CommandDCC("200.55.5.1:8101:rack1:local-dc:437425602",true); 
		Long init = System.currentTimeMillis();
		try{
			String json = cmd.execute();
			System.out.println(json);
			Assert.assertNotNull(json);
		}finally {
			Long end = System.currentTimeMillis();
			System.out.println("--");
			System.out.println( "TIME TO RUN: " + TimeUnit.MILLISECONDS.toSeconds((end-init)) + " seconds");
			cmd = null;
		}
	}

	
}
