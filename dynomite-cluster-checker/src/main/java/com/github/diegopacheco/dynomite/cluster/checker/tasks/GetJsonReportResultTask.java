package com.github.diegopacheco.dynomite.cluster.checker.tasks;

import java.util.ArrayList;

import com.github.diegopacheco.dynomite.cluster.checker.CheckerResponse;
import com.github.diegopacheco.dynomite.cluster.checker.ResultReport;
import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionContext;
import com.github.diegopacheco.dynomite.cluster.checker.util.ListJsonPrinter;

/**
 * GetJsonReportResultTask this get the DCC result as json. 
 * 
 * @author diegopacheco
 *
 */
public class GetJsonReportResultTask implements Task {
	
	@Override
	public void execute(ExecutionContext ec) {
		
		ResultReport resultReport = new ResultReport();
		resultReport.setNodesReport(new ArrayList<>());
		
		CheckerResponse checkerResponse = new CheckerResponse();
		checkerResponse.setSeeds(ec.getRawSeeds());
		
		if (ec.getOfflineNodes() != null && ec.getOfflineNodes().size() >= 1) {
			resultReport.setBadNodes(ec.getOfflineNodes());
		}
		
		resultReport.setTimeToRun(ec.getTimeToRunDCC());
		
		resultReport.setFailoverStatus(" NOT TESTED YET");
		
		String jsonResult = (ec.getIsTelemetryMode()) ? ListJsonPrinter.printTelemetry(resultReport) : ListJsonPrinter.print(resultReport);
		ec.setJsonResult(jsonResult);
	
	}
	
}
