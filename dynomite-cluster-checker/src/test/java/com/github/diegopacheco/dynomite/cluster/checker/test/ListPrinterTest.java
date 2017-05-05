package com.github.diegopacheco.dynomite.cluster.checker.test;

import com.github.diegopacheco.dynomite.cluster.checker.context.ExecutionReport;
import com.github.diegopacheco.dynomite.cluster.checker.context.NodeCheckerResponse;
import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.github.diegopacheco.dynomite.cluster.checker.util.ListJsonPrinter;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListPrinterTest {

    @Test
    public void shouldContainsBadNodeNamesWhenTelemetryModeIsAsked() {
        String report = ListJsonPrinter.printTelemetry(reportWithBadNodes());
        Assert.assertTrue(report.contains("\"badNodeNames\": \"localhost1,localhost2\","));
    }

    @Test
    public void shouldNotContainsBadNodeNamesWhenThereIsNoIssue() {
        String report = ListJsonPrinter.printTelemetry(reportWithoutBadNodes());
        Assert.assertFalse(report.contains("\"badNodeNames\":"));
    }

    private ExecutionReport reportWithoutBadNodes() {
        return  buildResultReport(new ArrayList<DynomiteNodeInfo>());
    }

    private ExecutionReport reportWithBadNodes() {
        return buildResultReport(Lists.newArrayList(
                new DynomiteNodeInfo("localhost1", "6379", "rack1", "dc1", "123"),
                new DynomiteNodeInfo("localhost2", "6379", "rack1", "dc1", "123")));
    }

    private ExecutionReport buildResultReport(List<DynomiteNodeInfo> nodes) {
        ExecutionReport resultReport = new ExecutionReport();
        resultReport.setOfflineNodes(nodes);
        resultReport.setFailoverStatus("Issue");
        List<NodeCheckerResponse> responses = new ArrayList<NodeCheckerResponse>();
        responses.add(new NodeCheckerResponse("localhost1:6379:rack1:dc1:123|localhost2:6379:rack1:dc1:123|","0" ,"0", false, "localhost1"));
        responses.add(new NodeCheckerResponse("localhost1:6379:rack1:dc1:123|localhost2:6379:rack1:dc1:123|","0" ,"0", false, "localhost2"));
        resultReport.setNodesReport(responses);
        return  resultReport;
    }

}
