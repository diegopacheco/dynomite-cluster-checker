package com.github.diegopacheco.dynomite.cluster.checker.context;

import java.util.ArrayList;
import java.util.List;

import com.github.diegopacheco.dynomite.cluster.checker.parser.DynomiteNodeInfo;
import com.netflix.dyno.jedis.DynoJedisClient;

/**
 * DCC Execution context.
 * 
 * @author diegopacheco
 *
 */
public class ExecutionContext {
	
	private String rawSeeds = ""; 
	private Boolean isTelemetryMode = false;
	
	private List<DynomiteNodeInfo> originalNodes = new ArrayList<>();
	private List<DynomiteNodeInfo> onlineNodes   = new ArrayList<>();
	private List<DynomiteNodeInfo> offlineNodes  = new ArrayList<>();
	
	private DynoJedisClient wholeClusterClient   =  null;
	private ExecutionReport executionReport = new ExecutionReport();
	
	private String replicationKey;
	private String replicationValue;
	
	private String failOverKey;
	private String failOverValue;
	
	public ExecutionContext() {}

	public String getRawSeeds() {
		return rawSeeds;
	}
	public void setRawSeeds(String rawSeeds) {
		this.rawSeeds = rawSeeds;
	}

	public Boolean getIsTelemetryMode() {
		return isTelemetryMode;
	}
	public void setIsTelemetryMode(Boolean isTelemetryMode) {
		this.isTelemetryMode = isTelemetryMode;
	}

	public List<DynomiteNodeInfo> getOriginalNodes() {
		return originalNodes;
	}

	public void setOriginalNodes(List<DynomiteNodeInfo> originalNodes) {
		this.originalNodes = originalNodes;
	}

	public List<DynomiteNodeInfo> getOnlineNodes() {
		return onlineNodes;
	}
	public void setOnlineNodes(List<DynomiteNodeInfo> onlineNodes) {
		this.onlineNodes = onlineNodes;
	}

	public List<DynomiteNodeInfo> getOfflineNodes() {
		return offlineNodes;
	}
	public void setOfflineNodes(List<DynomiteNodeInfo> offlineNodes) {
		this.offlineNodes = offlineNodes;
	}

	public DynoJedisClient getWholeClusterClient() {
		return wholeClusterClient;
	}
	public void setWholeClusterClient(DynoJedisClient wholeClusterClient) {
		this.wholeClusterClient = wholeClusterClient;
	}

	public ExecutionReport getExecutionReport() {
		return executionReport;
	}
	public void setExecutionReport(ExecutionReport executionReport) {
		this.executionReport = executionReport;
	}

	public String getReplicationKey() {
		return replicationKey;
	}
	public void setReplicationKey(String replicationKey) {
		this.replicationKey = replicationKey;
	}

	public String getReplicationValue() {
		return (replicationValue==null) ? "" : replicationValue;
	}
	public void setReplicationValue(String replicationValue) {
		this.replicationValue = replicationValue;
	}

	public String getFailOverKey() {
		return failOverKey;
	}

	public void setFailOverKey(String failOverKey) {
		this.failOverKey = failOverKey;
	}

	public String getFailOverValue() {
		return  (failOverValue==null) ? "" : failOverValue;
	}
	public void setFailOverValue(String failOverValue) {
		this.failOverValue = failOverValue;
	}
	
}
