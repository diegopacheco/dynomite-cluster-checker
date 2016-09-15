package com.github.diegopacheco.dynomite.cluster.checker.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.github.diegopacheco.dynomite.cluster.checker.DynomiteClusterCheckerMain;

@SuppressWarnings("serial")
public class RestServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(RestServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		try{
			String seeds = req.getParameter("seeds").toString();
			logger.info("Checking seeds: " + seeds);
			
			DynomiteClusterCheckerMain dcc = new DynomiteClusterCheckerMain();
			String json = dcc.run(seeds);
			out.write(json);
		}catch(Exception e){
			logger.error(e);
			out.write("{ \"Error\": " + e.toString() + " }");
		}finally {
			out.close();
		}
	}
	
}
