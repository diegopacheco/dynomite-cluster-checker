package com.github.diegopacheco.dynomite.cluster.checker.hystrix;

import com.github.diegopacheco.dynomite.cluster.checker.cluster.DCCConnectionManager;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.netflix.dyno.jedis.DynoJedisClient;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

public class DynomiteSingleNodeCommand extends HystrixCommand<Boolean> {
	
    private final String clusterName;
    private final DynomiteNodeInfo node;
    private DynoJedisClient cluster = null;
    
    public DynomiteSingleNodeCommand(String clusterName,DynomiteNodeInfo node) {
        
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DSNGroup"))
                .andCommandPropertiesDefaults(
                	HystrixCommandProperties.Setter()
                	   .withExecutionTimeoutEnabled(true)
                	   .withExecutionIsolationThreadInterruptOnTimeout(true)
                	   .withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD)
                       .withExecutionTimeoutInMilliseconds(15000)
                    )
        );
        
        this.clusterName = clusterName;
        this.node = node;
    }
    
    @Override
    protected Boolean run() {
    	try{
    		cluster = DCCConnectionManager.createSingleNodeCluster(clusterName,node);
    		cluster.get("awesomeSbrubles");
    	}catch(Throwable t){
    	}finally{
    		closeClusterConnection();
    	}
		return true;
    }
    
    private void closeClusterConnection(){
		if (cluster!=null)
			cluster.stopClient();
    }
    
    @Override
    protected Boolean getFallback() {
    	closeClusterConnection();
    	return false;
    }

	
}
