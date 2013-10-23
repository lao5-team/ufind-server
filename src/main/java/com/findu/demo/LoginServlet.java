package com.findu.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.*;

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

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				request.getInputStream()));

		String msg = "";
		msg = reader.readLine();
		if (!isUserLoginAction(msg)) {
			return;
		}

		doUserLoginAction(request, response, reader, msg);

	}

	private void doUserLoginAction(HttpServletRequest request,
			HttpServletResponse response, BufferedReader reader, String msg)
			throws ServletException, IOException {

		String qqid = "";
		String nickname = "";
		String picture = "";
		String userid = null;
		String pwd = null;
		int loginType = -1;

		while ((msg = reader.readLine()) != null) {// 无法读取到
			System.out.println("receive login message:" + msg);

			//login#logintype#
			if (msg.startsWith(DataStruct.LOGIN_TYPE)) {
				loginType = Integer.parseInt(msg
						.substring(DataStruct.LOGIN_TYPE.length()));
			}
			if (loginType == DataStruct.QQ_LOGIN) {
				if (msg.startsWith(DataStruct.QQ_ID)) {
					qqid = msg.substring(DataStruct.QQ_ID.length());
				}
				if (msg.startsWith(DataStruct.NICKNAME)) {
					nickname = msg.substring(DataStruct.NICKNAME.length());
				}
				if (msg.startsWith(DataStruct.PICTURE)) {
					picture = msg.substring(DataStruct.PICTURE.length());
				}

			} else if (loginType == DataStruct.FINDU_LOGIN) {

			}

		}

		if (loginType == -1
				|| (loginType == DataStruct.QQ_LOGIN && qqid.equals(""))) {
			doResponse(request, response, DataStruct.ACTION_LOGINUSER + ":"
					+ DataStruct.RESULT_OK);
			return;
		}

		String ownid = createOwnId(qqid);
		User user = new User(qqid, ownid, userid, pwd, picture);

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		Dao dao = new Dao();
		boolean added = false;
		try{
			dao.login(user);
			added = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}

		if (added) {
			msg = DataStruct.ACTION_LOGINUSER + ":" + DataStruct.RESULT_OK;
		} else {
			msg = DataStruct.ACTION_LOGINUSER + ":" + DataStruct.RESULT_FAIL;
		}
		doResponse(request, response, msg);

	}

	private void doResponse(HttpServletRequest request,
			HttpServletResponse response, String msg) throws ServletException,
			IOException {

		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>" + msg + "</h1>");
		response.getWriter().println(
				"session=" + request.getSession(true).getId());
	}

	private String createOwnId(String qqid) {
		return "findu" + qqid;
	}

	private boolean isUserLoginAction(String msg) {
		return msg.equals(DataStruct.ACTION_LOGINUSER);
	}
}
