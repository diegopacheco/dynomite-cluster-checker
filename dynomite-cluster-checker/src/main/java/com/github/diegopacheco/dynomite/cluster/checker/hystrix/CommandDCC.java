package com.github.diegopacheco.dynomite.cluster.checker.hystrix;

import com.github.diegopacheco.dynomite.cluster.checker.DynomiteClusterCheckerMain;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

public class CommandDCC extends HystrixCommand<String> {

    private final String seeds;
    private final Boolean isTelemetryMode;

    public CommandDCC(String seeds,Boolean isTelemetryMode) {
        
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DCCGroup"))
                .andCommandPropertiesDefaults(
                	HystrixCommandProperties.Setter()
                	   .withExecutionTimeoutEnabled(true)
                	   .withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD)
                       .withExecutionTimeoutInMilliseconds(50000)
                    )
        );
        
        this.seeds = seeds;
        this.isTelemetryMode = isTelemetryMode;
        
        ConfigurationManager.getConfigInstance().setProperty(
        		  "hystrix.command.DCCGroup.execution.isolation.thread.timeoutInMilliseconds", 
        		  50000);
    }
    
    @Override
    protected String getFallback() {
    	return "FALLBACK";
    }

    @Override
    protected String run() {
    	DynomiteClusterCheckerMain dcc = new DynomiteClusterCheckerMain();
		String json = dcc.run(seeds, isTelemetryMode);
		return json;
    }
}