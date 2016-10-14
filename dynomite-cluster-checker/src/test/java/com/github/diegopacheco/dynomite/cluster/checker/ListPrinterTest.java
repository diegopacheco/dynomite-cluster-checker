package com.github.diegopacheco.dynomite.cluster.checker;

import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.github.diegopacheco.dynomite.cluster.checker.util.ListJsonPrinter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListPrinterTest {


    @Test
    public void shouldContainsBadNodeNamesWhenTelemetryModeIsAsked() {
        String report = ListJsonPrinter.printTelemetry(buildResultReport());
        Assert.assertTrue(report.contains("\"badNodeNames\": \"localhost1,localhost2\","));
    }

    private ResultReport buildResultReport() {
        ResultReport resultReport = new ResultReport();
        List<DynomiteNodeInfo> badNodes = new ArrayList<DynomiteNodeInfo>();
        badNodes.add(new DynomiteNodeInfo("localhost1", "6379", "rack1", "dc1", "123"));
        badNodes.add(new DynomiteNodeInfo("localhost2", "6379", "rack1", "dc1", "123"));
        resultReport.setBadNodes(badNodes);
        resultReport.setFailoverStatus("Issue");
        List<CheckerResponse> responses = new ArrayList<CheckerResponse>();
        responses.add(new CheckerResponse("localhost1:6379:rack1:dc1:123|localhost2:6379:rack1:dc1:123|","0" ,"0", false, "localhost1"));
        responses.add(new CheckerResponse("localhost1:6379:rack1:dc1:123|localhost2:6379:rack1:dc1:123|","0" ,"0", false, "localhost2"));
        resultReport.setNodesReport(responses);
        return  resultReport;
    }

}
