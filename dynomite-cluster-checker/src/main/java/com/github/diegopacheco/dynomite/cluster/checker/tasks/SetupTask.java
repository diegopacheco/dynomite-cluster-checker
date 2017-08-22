package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteSeedsParser;

/**
 * SetupTask make the setup for next tasks.
 * 
 * @author diegopacheco
 *
 */
public class SetupTask implements Task{
	
	@Override
	public void execute(ExecutionContext ec) {
		ec.setRawSeeds( processSeeds(ec.getRawSeeds()) );
		
		List<DynomiteNodeInfo> originalNodes = DynomiteSeedsParser.parse(ec.getRawSeeds());
		ec.setOriginalNodes(originalNodes);
	}
	
	private String processSeeds(String rawSeeds) {
		if (null==rawSeeds) return null;
		String seeds = rawSeeds;
		seeds = seeds.replaceAll("\r", "");
		seeds = seeds.replaceAll("\n", "");
		seeds = seeds.replaceAll(" ", "");
		return seeds;
	}
	
}
