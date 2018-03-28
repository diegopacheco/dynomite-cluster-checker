package com.github.diegopacheco.dynomite.cluster.checker.context;

import java.util.ArrayList;
import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;

public class ExecutionReport {
	
	private String timeToRun = "";
	
	private String failoverStatus = "";
	private Integer replicationCount = 0;
	private Integer redisReplicationCount = 0;
	
	private List<NodeCheckerResponse> nodesReport = new ArrayList<>();
	private List<DynomiteNodeInfo>   offlineNodes = new ArrayList<>();
	private String jsonResult = "";
	
	public ExecutionReport() {}

	public String getFailoverStatus() {
		return failoverStatus;
	}
	public int getFailoverStatusTelemetry() {
		return "ok".toUpperCase().equals(failoverStatus) ? 0 : 1;
	}
	
	public void setFailoverStatus(String failoverStatus) {
		this.failoverStatus = failoverStatus;
	}

	public List<NodeCheckerResponse> getNodesReport() {
		return nodesReport;
	}
	
	public int getBadNodesTelemetry() {
		return (offlineNodes==null) ? 0 : offlineNodes.size();
	}
	
	public void setNodesReport(List<NodeCheckerResponse> nodesReport) {
		this.nodesReport = nodesReport;
	}
	
	public String getTimeToRun() {
		return timeToRun;
	}
	public void setTimeToRun(String timeToRun) {
		this.timeToRun = timeToRun;
	}
	
	public List<DynomiteNodeInfo> getOfflineNodes() {
		return offlineNodes;
	}

	public void setOfflineNodes(List<DynomiteNodeInfo> offlineNodes) {
		this.offlineNodes = offlineNodes;
	}

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}
	
	public Integer getReplicationCount() {
		return replicationCount;
	}
	public void setReplicationCount(Integer replicationCount) {
		this.replicationCount = replicationCount;
	}

	public Integer getRedisReplicationCount() {
		return redisReplicationCount;
	}
	public void setRedisReplicationCount(Integer redisReplicationCount) {
		this.redisReplicationCount = redisReplicationCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offlineNodes == null) ? 0 : offlineNodes.hashCode());
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
		ExecutionReport other = (ExecutionReport) obj;
		if (offlineNodes == null) {
			if (other.offlineNodes != null)
				return false;
		} else if (!offlineNodes.equals(other.offlineNodes))
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
		return "ExecutionReport [timeToRun=" + timeToRun + ", failoverStatus=" + failoverStatus + ", replicationCount="
		    + replicationCount + ", redisReplicationCount=" + redisReplicationCount + ", nodesReport=" + nodesReport
		    + ", offlineNodes=" + offlineNodes + "]";
	}
	
}
