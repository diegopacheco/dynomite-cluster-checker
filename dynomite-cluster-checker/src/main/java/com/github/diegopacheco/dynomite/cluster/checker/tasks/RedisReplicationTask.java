package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.github.diegopacheco.dynomite.cluster.checker.cluster.cache.RedisClientCache;
import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.context.NodeCheckerResponse;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;

import redis.clients.jedis.Jedis;

public class RedisReplicationTask implements Task{
	
	private static final Logger logger = Logger.getLogger(RedisReplicationTask.class);
	
	@Override
	public void execute(ExecutionContext ec) {
		
		int replicationCount = 0;
		List<DynomiteNodeInfo> nodes = ec.getOnlineNodes();
		for(DynomiteNodeInfo node : nodes) {
			
			Jedis redisCliet =  RedisClientCache.get(node.toSeedRedis());
			if(redisCliet!=null) {
				
				String result = "";
				try {
					result = redisCliet.get(ec.getReplicationKey());
				}catch(Exception e) {
					logger.error("Error to read " + ec.getReplicationKey() + " from Redis - EX: " + e);
				}
				
				if( result!=null && 
					  (!"".equals(result)) && 
					  ec.getReplicationValue().equals(result)) {
					
					replicationCount++;
					
					List<NodeCheckerResponse> onlineDynNodes = ec.getExecutionReport().getNodesReport();
					List<NodeCheckerResponse> resultFilter = onlineDynNodes
										.stream()
										.filter(  n -> node.toSeedRedis().equals(n.getSeeds()) )
										.collect(Collectors.toList());
					
					if(resultFilter!=null && resultFilter.size()==1) {
						resultFilter.get(0).setConsistencyRedis(true);
					}
				}
			}
		}
		ec.getExecutionReport().setRedisReplicationCount(replicationCount);
		
	}
	
}
