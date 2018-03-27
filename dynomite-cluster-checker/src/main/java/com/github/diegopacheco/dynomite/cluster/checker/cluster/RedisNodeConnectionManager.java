package com.github.diegopacheco.dynomite.cluster.checker.cluster;

import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;

import redis.clients.jedis.Jedis;

/**
 * RedisNodeConnectionManager connects to a single redis node.
 * 
 * @author diegopacheco
 * @since 27/03/2018
 *
 */
public class RedisNodeConnectionManager {
	
		public static Jedis createNodeConnection(DynomiteNodeInfo node) {
			 Jedis jedisClient = new Jedis(node.getServer(), new Integer(node.getRedisPort()));
			 jedisClient.connect();
			 return jedisClient;
		}
	
}
