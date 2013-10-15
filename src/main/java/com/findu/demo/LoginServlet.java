package com.findu.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String msg = "Hello World!";

	public LoginServlet() {
	}

	public LoginServlet(String msg) {
		this.msg = msg;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));

		while ((msg = reader.readLine()) != null) {//无法读取到
			System.out.println("receive Request message:" + msg);
			
		}
		
		String reusername = request.getParameter("username");
		String repassword = request.getParameter("password");
		System.out.println("receive Request user:" + reusername);
		System.out.println("receive Request password:" + repassword);
		boolean isLogin = checklogin(reusername, repassword);
		if (isLogin) {
			//response.sendRedirect("MyJsp.jsp");
			msg = "login-ok";
		} else {
			//response.sendRedirect("index.jsp");
			msg = "login-fail";
		}
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>" + msg + "</h1>");
		response.getWriter().println(
				"session=" + request.getSession(true).getId());
	}

	public boolean checklogin(String username, String password) {
		try {
			Dao dao = new Dao();
			return dao.login(new User(username, password, null, null));
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

}
