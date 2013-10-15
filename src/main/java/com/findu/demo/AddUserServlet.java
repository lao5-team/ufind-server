package com.findu.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserServlet extends HttpServlet {
	public AddUserServlet() {
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String user1 = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		String name = new String(request.getParameter("name").getBytes(
				"ISO8859_1"), "utf-8");
		String picture = new String(request.getParameter("picture").getBytes(
				"ISO8859_1"), "utf-8");
		User user = new User();
		user.setUser(user1);
		user.setPwd(pwd);
		user.setName(name);
		user.setPicture(picture);
		
		Dao dao = new Dao();
		boolean added = dao.addUser(user);
		/*
		 * request.setAttribute("info",new
		 * String("<br><br><center><h1><font color=red>tianjiachengong,gongxi!!" +
		 * "</font></h1></center><br>")); request.setAttribute("id", new
		 * String("a")); request.setAttribute("denglu",new String(
		 * "<br><br><center><a href = /jspDev/index.jsp target =_parent>��½</href></center><br>"
		 * ));
		 */

		request.getRequestDispatcher("info.jsp").forward(request, response);
	}

}
