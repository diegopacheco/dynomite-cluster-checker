package com.github.diegopacheco.dynomite.cluster.checker.cluster.cache;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import com.github.diegopacheco.dynomite.cluster.checker.cluster.DCCConnectionManager;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.netflix.dyno.jedis.DynoJedisClient;

@Singleton
public class ClientCache {

	private static Cache<String, DynoJedisClient> cache;

	static {
		cache =  CacheBuilder.newBuilder().
				  maximumSize(500).
				  expireAfterWrite(2, TimeUnit.HOURS).
				  removalListener(new RemovalListener<String, DynoJedisClient>(){
						@Override
						public void onRemoval(RemovalNotification<String, DynoJedisClient> notification) {
							System.out.println("Removing... " + notification.getKey());
							try{
								notification.getValue().stopClient();
							}catch(Exception e){}
						}
				  }).
				  build();
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
	
	public static void main(String[] args) {
		String seeds = "127.0.0.1:8102:rack1:dc:100";
		DynoJedisClient client = DCCConnectionManager.createSingleNodeCluster("test1", new DynomiteNodeInfo("127.0.0.1","8102","rack1","dc","100"));
		
		ClientCache.put(seeds, client);
		System.out.println(ClientCache.get(seeds));
		System.out.println(ClientCache.get(seeds));
		System.out.println(ClientCache.get(seeds));
		
		String seeds2 = "127.0.0.1:8102:rack1:dc:0";
		ClientCache.put(seeds2, client);
		System.out.println(ClientCache.get(seeds2));
		System.out.println(ClientCache.get(seeds));
	}

}
