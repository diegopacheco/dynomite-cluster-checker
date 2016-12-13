package com.github.diegopacheco.dynomite.cluster.checker.hystrix.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

/**
 * Simple Hystrix Command Test to make sure try/catch/finally is proper applied and we can close resources properly.
 * 
 * @author diegopacheco
 *
 */
public class HystrixCommandBehaviorTest {
	
	protected class CmdTest extends HystrixCommand<String> {
		private long ttl = 2000L;
		
		public CmdTest(Long ttl){
		    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DCCGroup"))
	                .andCommandPropertiesDefaults(
	                	HystrixCommandProperties.Setter()
	                	   .withExecutionTimeoutEnabled(true)
	                	   .withExecutionIsolationThreadInterruptOnTimeout(true)
	                	   .withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD)
	                       .withExecutionTimeoutInMilliseconds(3000)
	                    )
	        );
		    this.ttl = ttl;
		}
		
		@Override
	    protected String getFallback() {
	    	return "TIMEOUT";
	    }
		
		private String internalImpl(){
			try{
				Thread.sleep(ttl);
				throw new RuntimeException("Fake ERROR");
			}catch(Exception e){
				System.out.println("Catch");
			}finally{
				System.out.println("Finally");
			}
			return "OK";
		}
		
		@Override
		protected String run() {
			return internalImpl();
		}
	}

	
	private void testComand(Long ttl){
		Long init = System.currentTimeMillis();
		CmdTest cmd = new CmdTest(ttl);

		System.out.println("Waiting for: " + ttl + " ms");
		String result = cmd.execute();
		System.out.println("Result: " + result);
		
		Long end = System.currentTimeMillis();
		System.out.println("--");
		System.out.println( "TIME TO RUN: " + TimeUnit.MILLISECONDS.toSeconds((end-init)) + " seconds");
		System.out.println("--");
	}
	
	@Test
	public void testCommandTimeoutBehavior_1_OK(){
		testComand(1000L);	
	}
	
	@Test
	public void testCommandTimeoutBehavior_2_KO(){
		testComand(5000L);	
	}
	
}