package com.github.diegopacheco.dynomite.cluster.checker.util;

import java.util.List;
import java.util.stream.Collectors;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionReport;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;

/**
 * Pritns a list of JsonPrinter in Json.
 * @author diegopacheco
 * @since 01/07/2016
 * @version 1.0
 *
 */
public class ListJsonPrinter {
	
	public static String print(ExecutionReport rr){
		List<? extends JsonPrinter> list = rr.getNodesReport();
		
		StringBuffer sb = new StringBuffer("{\n\r");
		sb.append(" \"timeToRun\": \"" + rr.getTimeToRun() + " seconds" + "\",\n\r");
		sb.append(" \"failoverStatus\": \"" + rr.getFailoverStatus() +  "\",\n\r");
		sb.append(" \"replicationCount\": \"" + rr.getReplicationCount() +  "\",\n\r");
		sb.append(" \"redisReplicationCount\": \"" + rr.getRedisReplicationCount() +  "\",\n\r");
		
		if(rr.getOfflineNodes()!=null && rr.getOfflineNodes().size() >= 1 ){
			sb.append(" \"badNodes\": [");
			int i = 0;
			for(DynomiteNodeInfo node: rr.getOfflineNodes()){
				sb.append("\"" + node.toString() + "\"");
				if ((i+1) < rr.getOfflineNodes().size())
					sb.append(", ");
				i++;
			}
			sb.append(" ], \n\r");
		}else{
			sb.append(" \"badNodes\": [], \n\r");
		}
		
		sb.append(" \"nodesReport\":\n\r");
		Object[] array = list.toArray();
		sb.append("[\n\r");
		for(int i=0;i<array.length;i++){
			sb.append( ((JsonPrinter)array[i]).toPrettyJson() + resolveComma(array,i) );
		}
		sb.append("]\n\r");
		sb.append("}\n\r");
		return sb.toString();
	}
	
	public static String printTelemetry(ExecutionReport rr){
		List<? extends JsonPrinter> list = rr.getNodesReport();
		
		StringBuffer sb = new StringBuffer("{\n\r");
		sb.append(" \"timeToRun\": \"" + rr.getTimeToRun() +  "\",\n\r");
		sb.append(" \"failoverStatus\": \"" + rr.getFailoverStatusTelemetry() +  "\",\n\r");
		sb.append(" \"replicationCount\": \"" + rr.getReplicationCount() +  "\",\n\r");
		sb.append(" \"redisReplicationCount\": \"" + rr.getRedisReplicationCount() +  "\",\n\r");

		if (areBadNodes(rr.getBadNodesTelemetry()))
			sb.append(" \"badNodeNames\": \""+ String.join(",", rr.getOfflineNodes().stream().map(badNode -> badNode.getServer()).collect(Collectors.toList()))  + "\", \n\r");

		sb.append(" \"badNodes\": "+ rr.getBadNodesTelemetry()  + ", \n\r");
		
		sb.append(" \"nodesReport\":\n\r");
		Object[] array = list.toArray();
		sb.append("[\n\r");
		for(int i=0;i<array.length;i++){
			sb.append( ((JsonPrinter)array[i]).toPrettyTelemetryJson()  + resolveComma(array,i) );
		}
		sb.append("]\n\r");
		sb.append("}\n\r");
		return sb.toString();
	}

	private static boolean areBadNodes(int badNodes) {
		return badNodes > 0;
	}
	
	private static String resolveComma(Object[] array,int i){
		return ( (i+1<array.length) ? ",\n\r" : "\n\r");
	}
	
}
