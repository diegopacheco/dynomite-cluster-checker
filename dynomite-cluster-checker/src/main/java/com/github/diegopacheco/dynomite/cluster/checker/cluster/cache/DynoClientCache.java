package com.github.diegopacheco.dynomite.cluster.checker.cluster.cache;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.netflix.dyno.jedis.DynoJedisClient;

@Singleton
public class DynoClientCache {

	private static Cache<String, DynoJedisClient> cache;
	private static final Logger logger = Logger.getLogger(DynoClientCache.class);

	static {
		cache = CacheBuilder.newBuilder().
				maximumSize(500).
				expireAfterAccess(2, TimeUnit.HOURS).
				removalListener(new RemovalListener<String, DynoJedisClient>() {
					@Override
					public void onRemoval(RemovalNotification<String, DynoJedisClient> notification) {
						logger.debug("Removing... " + notification.getKey());
						try {
							notification.getValue().stopClient();
						} catch (Exception e) {
							logger.error("Error on close evicted client. EX: " + e);
						}
					}
				}).build();
	}

	public static DynoJedisClient get(String seeds) {
		try {
			return cache.getIfPresent(seeds);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void put(String seeds, DynoJedisClient client) {
		cache.put(seeds, client);
	}

}
