package com.github.diegopacheco.dynomite.cluster.checker.util;

import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.ResultReport;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;

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
		
		StringBuffer sb = new StringBuffer("{\n\r");
		sb.append(" \"failoverStatus\": \"" + rr.getFailoverStatus() +  "\",\n\r");
		
		if(rr.getBadNodes()!=null && rr.getBadNodes().size() >= 1 ){
			sb.append(" \"badNodes\": [");
			int i = 0;
			for(DynomiteNodeInfo node: rr.getBadNodes()){
				sb.append("\"" + node.toString() + "\"");
				if ((i+1) < rr.getBadNodes().size())
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
			sb.append( ((JsonPrinter)array[i]).toPrettyJson() + ( (i+1<array.length) ? ",\n\r" : "\n\r") );
		}
		sb.append("]\n\r");
		sb.append("}\n\r");
		return sb.toString();
	}
	
}
