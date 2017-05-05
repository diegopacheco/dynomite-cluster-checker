package com.github.diegopacheco.dynomite.cluster.checker.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.netflix.dyno.connectionpool.Host;
import com.netflix.dyno.connectionpool.HostSupplier;

/**
 * HostSupplierFactory creates HOSTS based on DynomiteNodeInfo.
 * 
 * 
 * @author diegopacheco
 *
 */
public class HostSupplierFactory {
	
	public static HostSupplier build(List<DynomiteNodeInfo> nodes){
		final List<Host> hosts = new ArrayList<Host>();
		
		for(DynomiteNodeInfo node: nodes){
			hosts.add(node.toHOST());
		}
		
		final HostSupplier customHostSupplier = new HostSupplier() {
		   @Override
		   public Collection<Host> getHosts() {
			   return hosts;
		   }
		};
		return customHostSupplier;
	}
	
}