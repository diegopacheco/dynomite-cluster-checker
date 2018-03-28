package com.github.diegopacheco.dynomite.cluster.checker.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parse the Dynomite Seeds File. Expecting format: server:port:rack:dc:tokens|server:port:rack:dc:tokens|server:port:rack:dc:tokens
 * @author diegopacheco
 * @since 01/07/2016
 * @version 1.0
 *
 */
public class DynomiteSeedsParser {
	
	public static List<DynomiteNodeInfo> parse(String seeds){
		
		List<DynomiteNodeInfo> result = new ArrayList<>();
		
		if ("local".equals(seeds)) 
			return  Arrays.asList( new DynomiteNodeInfo("127.0.0.1", "8102", "rack1", "localdc", "1383429731") );
		
		if (seeds == null || "".equals(seeds)) 
			throw new IllegalArgumentException("Seeds is blank or null. Invalid Seeds! ");
		
		String[] seedsArray =  (seeds.contains("|")) ? seeds.split("\\|") : new String[]{seeds} ;
		if (seedsArray==null || seedsArray.length ==0 )throw new IllegalArgumentException("Invalid Seeds! Seeds: " + seeds);
		
		
		for( String s: seedsArray){
			String[] itens = s.split(":");
			if (itens==null || itens.length ==0 )throw new IllegalArgumentException("Invalid Seeds! Seeds: " + seeds);
			
			DynomiteNodeInfo node = new DynomiteNodeInfo();
			node.setServer(itens[0]);
			node.setPort(itens[1]);
			node.setRack(itens[2]);
			node.setDc(itens[3]);
			node.setTokens(itens[4]);
			try {
				node.setRedisPort(itens[5]);
			}catch(Exception e) {
			}
			result.add(node);
		}
		
		return result;
	}
	
}
