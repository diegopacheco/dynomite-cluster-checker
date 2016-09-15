package com.github.diegopacheco.dynomite.cluster.checker.util;

import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.ResultReport;

/**
 * Pritns a list of JsonPrinter in Json.
 * @author diegopacheco
 * @since 01/07/2016
 * @version 1.0
 *
 */
public class ListJsonPrinter {
	
	public static String print(ResultReport rr){
		List<? extends JsonPrinter> list = rr.getNodesReport();
		if (list.size()==1) return list.get(0).toPrettyJson();
		
		StringBuffer sb = new StringBuffer("{\n\r");
		sb.append(" \"failoverStatus\": \"" + rr.getFailoverStatus() +  "\",\n\r");
		sb.append(" \"nodesReport\":\n\r");
		Object[] array = list.toArray();
		sb.append("[\n\r");
		for(int i=0;i<array.length;i++){
			sb.append( ((JsonPrinter)array[i]).toPrettyJson() + ( (i+1<array.length) ? ",\n\r" : "\n\r") );
		}
		sb.append("]\n\r");
		sb.append("}\n\r");
		return sb.toString();
	}
	
}
