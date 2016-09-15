package com.github.diegopacheco.dynomite.cluster.checker;

import com.github.diegopacheco.dynomite.cluster.checker.util.JsonPrinter;

/**
 * Represents a check for a node in Dynomite Cluster
 * 
 * @author diegopacheco
 * @since 01/07/2016
 * @version 1.0
 *
 */
public class CheckerResponse implements JsonPrinter {
	
	private String server; 
	private String seeds;
	private String insertTime;
	private String getTime;
	private boolean consistency = false;
	private String failoverStatus;
	
	private String insertError = null;
	private String getError = null;
	
	public CheckerResponse() {}

	public CheckerResponse(String seeds, String insertTime, String getTime, boolean consistency,String server,String failoverStatus) {
		super();
		this.seeds = seeds;
		this.insertTime = insertTime;
		this.getTime = getTime;
		this.consistency = consistency;
		this.server = server;
		this.failoverStatus = failoverStatus;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public boolean isConsistency() {
		return consistency;
	}

	public void setConsistency(boolean consistency) {
		this.consistency = consistency;
	}
	
	public String getSeeds() {
		return seeds;
	}

	public void setSeeds(String seeds) {
		this.seeds = seeds;
	}
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getInsertError() {
		return insertError;
	}

	public void setInsertError(String insertError) {
		this.insertError = insertError;
	}

	public String getGetError() {
		return getError;
	}

	public void setGetError(String getError) {
		this.getError = getError;
	}
	
	public String getFailoverStatus() {
		return failoverStatus;
	}
	public void setFailoverStatus(String failoverStatus) {
		this.failoverStatus = failoverStatus;
	}

	public void cleanUp(){
		setInsertTime(null);
		setGetTime(null);
		setConsistency(false);
		setFailoverStatus("");
	}
	
	public String toJson(){
		return "{"  + 
					"\"server\":\""      + server + "\"," +
					"\"seeds\":\""        + seeds + "\"," +
					"\"insertTime\":\""   + insertTime + "\"," +
					"\"getTime\":\""      + getTime + "\"," +
					"\"insertError\":\""  + insertError + "\"," +
					"\"getError\":\""     + getError + "\"," +
					"\"consistency\":\""  + consistency + "\"" +
					"\"failoverStatus\":\""  + failoverStatus + "\"" +
				"}";
	}
	
	public String toPrettyJson(){
		return "  {\r\n"  + 
				pritIfNotNull("    \"server\":\""  + server + "\",\r\n",server) +
				pritIfNotNull("    \"seeds\":\"" + seeds + "\",\r\n",seeds) +
				pritIfNotNull("    \"insertTime\":\"" + insertTime + "\",\r\n",insertTime) +
				pritIfNotNull("    \"getTime\":\""    + getTime + "\",\r\n",getTime) +
				pritIfNotNull("    \"insertError\":\""  + insertError + "\",",insertError) +
				pritIfNotNull("    \"getError\":\""     + getError + "\",",getError)       +
				"    \"consistency\":\""  + consistency + "\"\r\n" +
				"    \"failoverStatus\":\""  + failoverStatus + "\"\r\n" +
				"  }";
	}
	
	private String pritIfNotNull(String msg,String field){
		return ("".equals(field) || null == field) ? "" : msg;
	}
	
	@Override
	public String toString() {
		return toPrettyJson();
	}
	
}
