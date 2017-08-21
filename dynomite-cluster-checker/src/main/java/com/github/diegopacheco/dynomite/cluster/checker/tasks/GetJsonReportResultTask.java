package com.github.diegopacheco.dynomite.cluster.checker.tasks;

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
		String jsonResult = (ec.getIsTelemetryMode()) ? ListJsonPrinter.printTelemetry(ec.getExecutionReport()) : ListJsonPrinter.print(ec.getExecutionReport());
		ec.getExecutionReport().setJsonResult(jsonResult);
	}
	
}
