package com.github.diegopacheco.dynomite.cluster.checker.hystrix;

import com.github.diegopacheco.dynomite.cluster.checker.cluster.DynomiteClusterConnectionManager;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.netflix.dyno.jedis.DynoJedisClient;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

public class DynomiteSingleNodeCommand extends HystrixCommand<Boolean> {
	
    private final String clusterName;
    private final DynomiteNodeInfo node;

    public DynomiteSingleNodeCommand(String clusterName,DynomiteNodeInfo node) {
        
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DSNGroup"))
                .andCommandPropertiesDefaults(
                	HystrixCommandProperties.Setter()
                	   .withExecutionTimeoutEnabled(true)
                	   .withExecutionIsolationThreadInterruptOnTimeout(true)
                	   .withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD)
                       .withExecutionTimeoutInMilliseconds(5000)
                    )
        );
        
        this.clusterName = clusterName;
        this.node = node;
        
    }
    
    @Override
    protected Boolean getFallback() {
    	return false;
    }

    @Override
    protected Boolean run() {
    	DynoJedisClient cluster = DynomiteClusterConnectionManager.createSingleNodeCluster(clusterName,node);
		cluster.get("awesomeSbrubles");
		cluster.stopClient();
		return true;
    }
	
}
