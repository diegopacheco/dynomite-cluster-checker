package com.github.diegopacheco.dynomite.cluster.checker;

import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.google.common.collect.ImmutableList;

public class ResultReport {
	
	private String failoverStatus;
	private List<CheckerResponse> nodesReport;
	private List<DynomiteNodeInfo> badNodes;
	
	public ResultReport() {}

	public String getFailoverStatus() {
		return failoverStatus;
	}
	public void setFailoverStatus(String failoverStatus) {
		this.failoverStatus = failoverStatus;
	}

	public List<CheckerResponse> getNodesReport() {
		return nodesReport;
	}
	public void setNodesReport(List<CheckerResponse> nodesReport) {
		this.nodesReport = nodesReport;
	}
	
	public List<DynomiteNodeInfo> getBadNodes() {
		return badNodes;
	}
	public void setBadNodes(List<DynomiteNodeInfo> badNodes) {
		this.badNodes =  ImmutableList.copyOf(badNodes);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((badNodes == null) ? 0 : badNodes.hashCode());
		result = prime * result + ((failoverStatus == null) ? 0 : failoverStatus.hashCode());
		result = prime * result + ((nodesReport == null) ? 0 : nodesReport.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultReport other = (ResultReport) obj;
		if (badNodes == null) {
			if (other.badNodes != null)
				return false;
		} else if (!badNodes.equals(other.badNodes))
			return false;
		if (failoverStatus == null) {
			if (other.failoverStatus != null)
				return false;
		} else if (!failoverStatus.equals(other.failoverStatus))
			return false;
		if (nodesReport == null) {
			if (other.nodesReport != null)
				return false;
		} else if (!nodesReport.equals(other.nodesReport))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResultReport [failoverStatus=" + failoverStatus + ", nodesReport=" + nodesReport + ", badNodes="
				+ badNodes + "]";
	}
}
