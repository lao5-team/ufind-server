package com.findu.demo;

import java.net.InetSocketAddress;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.findu.demo.LoginServlet;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		System.out.println("Server Start");
		Server server = new Server(3306);

		ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		// http://localhost:8080/login
		context.addServlet(new ServletHolder(new LoginServlet()), "/login");
		
		// http://localhost:8080/login
		context.addServlet(new ServletHolder(new AddUserServlet()), "/adduser");

		// http://localhost:8080/hello
		context.addServlet(new ServletHolder(new HelloServlet()), "/hello");
		// http://localhost:8080/hello/kongxx
		context.addServlet(
				new ServletHolder(new HelloServlet("Hello Kongxx!")),
				"/hello/kongxx");

		// http://localhost:8080/goodbye
		context.addServlet(new ServletHolder(new GoodbyeServlet()), "/goodbye");
		// http://localhost:8080/goodbye/kongxx
		context.addServlet(new ServletHolder(new GoodbyeServlet(
				"Goodbye kongxx!")), "/goodbye/kongxx");

		server.start();
		server.join();
	}
}