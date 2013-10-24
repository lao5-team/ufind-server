package com.findu.demo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserServlet extends HttpServlet {
	public AddUserServlet() {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));

		String msg = "";
		String username = "";
		String password = "";
		while ((msg = reader.readLine()) != null) {//无法读取到
			System.out.println("receive adduser message:" + msg);
			if(msg.startsWith("#username#=")){
				username = msg.substring(11);
			}
			if(msg.startsWith("#pwd#=")){
				password = msg.substring(6);
			}
			
		}
		
		User user = new User();
		user.setUser(username);
		user.setPwd(password);
		//user.setName(name);
		//user.setPicture(picture);
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		
		Dao dao = new Dao();
		boolean added = dao.addUser(user);
		
		if(added){
			msg = "adduser-ok";
		}else{
			msg = "adduser-fail";
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>" + msg + "</h1>");
		response.getWriter().println(
				"session=" + request.getSession(true).getId());
		/*
		 * request.setAttribute("info",new
		 * String("<br><br><center><h1><font color=red>tianjiachengong,gongxi!!" +
		 * "</font></h1></center><br>")); request.setAttribute("id", new
		 * String("a")); request.setAttribute("denglu",new String(
		 * "<br><br><center><a href = /jspDev/index.jsp target =_parent>��½</href></center><br>"
		 * ));
		 */

		//request.getRequestDispatcher("info.jsp").forward(request, response);
	}

}
