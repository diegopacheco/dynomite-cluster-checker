package com.github.diegopacheco.dynomite.cluster.checker.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.netflix.dyno.connectionpool.Host;
import com.netflix.dyno.connectionpool.TokenMapSupplier;
import com.netflix.dyno.connectionpool.impl.lb.AbstractTokenMapSupplier;
import com.netflix.dyno.connectionpool.impl.lb.HostToken;

/**
 * TokenMapSupplierFactory creates TokenMapSupplier baased on DynomiteNodeInfo.
 * 
 * @author diegopacheco
 *
 */
public class TokenMapSupplierFactory {
	
	/**
	 * 
	 * SAMPLE
	 * 
	 *  *  {
     *      "dc": "eu-west-1",
     *      "hostname": "ec2-52-208-92-24.eu-west-1.compute.amazonaws.com",
     *      "ip": "52.208.92.24",
     *      "rack": "dyno_sandbox--euwest1c",
     *      "token": "1383429731",
     *      "zone": "eu-west-1c"
     *  },
	 * 
	 * 
	 * @param nodes
	 * @return
	 */
	public static TokenMapSupplier build(List<DynomiteNodeInfo> nodes){
		StringBuilder jsonSB = new StringBuilder("[");
		int count = 0;
		final Map<String,DynomiteNodeInfo> mapNodes = new HashMap<>();
		
		for(DynomiteNodeInfo node: nodes){
			mapNodes.put(node.getServer(), node);
			jsonSB.append(node.toJsonTopology());
			count++;
			if (count < nodes.size())
				jsonSB.append(" , ");
		}
		jsonSB.append(" ]\"");
		
	   final String json = jsonSB.toString();
	   TokenMapSupplier testTokenMapSupplier = new AbstractTokenMapSupplier() {
			    @Override
			    public String getTopologyJsonPayload(String hostname) {
			    	return json;
			    }
				@Override
				public String getTopologyJsonPayload(Set<Host> activeHosts) {
					return json;
				}
				@Override
				public List<HostToken> getTokens(Set<Host> activeHosts) {
					List<HostToken> tokens = new ArrayList<>();
					for(DynomiteNodeInfo node: nodes){
						tokens.add(new HostToken(new Long(node.getTokens()), node.toHOST()));
					}
					return tokens;
				}
				@Override
				public HostToken getTokenForHost(Host host, Set<Host> activeHosts) {
					DynomiteNodeInfo dni = mapNodes.get(host.getHostName());
					return new HostToken(new Long(dni.getTokens()), dni.toHOST());
				}
		};
		return testTokenMapSupplier;
	}
	
}
