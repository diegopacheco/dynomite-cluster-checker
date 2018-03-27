package com.github.diegopacheco.dynomite.cluster.checker.cluster.cache;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import redis.clients.jedis.Jedis;

@Singleton
public class RedisClientCache {

	private static Cache<String, Jedis> cache;
	private static final Logger logger = Logger.getLogger(RedisClientCache.class);

	static {
		cache = CacheBuilder.newBuilder().
				maximumSize(500).
				expireAfterAccess(2, TimeUnit.HOURS).
				removalListener(new RemovalListener<String, Jedis>() {
					@Override
					public void onRemoval(RemovalNotification<String, Jedis> notification) {
						logger.debug("Removing... " + notification.getKey());
						try {
							notification.getValue().disconnect();
						} catch (Exception e) {
							logger.error("Error on close evicted client. EX: " + e);
						}
					}
				}).build();
	}

	public static Jedis get(String node) {
		try {
			return cache.getIfPresent(node);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void put(String node, Jedis client) {
		cache.put(node, client);
	}

}