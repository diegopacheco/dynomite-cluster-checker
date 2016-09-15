package com.github.diegopacheco.dynomite.cluster.checker.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.diegopacheco.dynomite.cluster.checker.DynomiteClusterCheckerMain;

@WebServlet(name="RestServlet", urlPatterns={"/check"} ) 
public class RestServlet extends javax.servlet.http.HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try{
			String seeds = req.getParameter("seeds").toString();
			String json = DynomiteClusterCheckerMain.run(seeds);
			resp.getWriter().write(json);
		}catch(Throwable e){
			resp.getWriter().write(e.toString());
		}
		
	}
	
}
