package com.github.diegopacheco.dynomite.cluster.checker.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.github.diegopacheco.dynomite.cluster.checker.tasks.engine.DCCTaskExecutionEngine;
import com.netflix.hystrix.Hystrix;

@SuppressWarnings("serial")
public class RestServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(RestServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		try{
			String seeds = req.getParameter("seeds");
			if (seeds==null){
				out.write("{ \"Error\": \"Abort! seeds cannot be nuul!\" }");
				return;
			}
			
			boolean shouldRunTelemetryMode = resolveTelemetryMode(req.getParameterValues("telemetry"));
			logArgs(req);
			
			String json = new DCCTaskExecutionEngine().run(seeds, shouldRunTelemetryMode);
			out.write(json);
			
		}catch(Exception e){
			logger.error(e); 
			out.write("{ \"Error\": \"" + e.toString() + " - " + org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e) + "\" }");
		}finally {
			Hystrix.reset();
			out.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void logArgs(HttpServletRequest req){
		logger.info("Checking seeds: " + req.getParameter("seeds"));
		logger.info("TELEMETRY mode: " + req.getParameterValues("telemetry"));
		
		Enumeration<String> parameterNames = req.getParameterNames();
		while(parameterNames.hasMoreElements()){
			 String paramName = parameterNames.nextElement();
			 logger.info("Other parameter: " + paramName);
		}
	}
	
	private boolean resolveTelemetryMode(String telemetry[]){
		if (telemetry==null) return false;
		return ("true".equals(telemetry[0])) ? true : false;
	}
	
}
