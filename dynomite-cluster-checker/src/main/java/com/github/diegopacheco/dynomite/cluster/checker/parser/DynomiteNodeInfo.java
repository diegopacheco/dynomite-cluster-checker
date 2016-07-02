package com.github.diegopacheco.dynomite.cluster.checker.parser;

/**
 * Represents a Dynomite Seed Node Configuration
 * 
 * @author diegopacheco
 * @since 01/07/2016
 * @version 1.0
 *
 */
public class DynomiteNodeInfo {
	
	private String server;
	private String port;
	private String rack;
	private String dc;
	private String tokens;
	
	public DynomiteNodeInfo() {}

	public DynomiteNodeInfo(String server, String port, String rack, String dc, String tokens) {
		super();
		this.server = server;
		this.port = port;
		this.rack = rack;
		this.dc = dc;
		this.tokens = tokens;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getRack() {
		return rack;
	}

	public void setRack(String rack) {
		this.rack = rack;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getTokens() {
		return tokens;
	}

	public void setTokens(String tokens) {
		this.tokens = tokens;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((server == null) ? 0 : server.hashCode());
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
		DynomiteNodeInfo other = (DynomiteNodeInfo) obj;
		if (server == null) {
			if (other.server != null)
				return false;
		} else if (!server.equals(other.server))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return server  + ":" + port + ":" + rack + ":" + dc + ":" + tokens;
	}
	
}
