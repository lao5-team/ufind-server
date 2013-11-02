package com.findu.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

/*
 * update protocol:
 * #update#/r/n#openid#=12345\r\n#userid#=54321\r\n#userpwd#=123\r\n#picture#=http://123123.com
 */
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String msg = "Hello World!";

	public UpdateServlet() {
	}

	public UpdateServlet(String msg) {
		this.msg = msg;
	}
	
	private void doResponse(HttpServletRequest request,
			HttpServletResponse response, String msg) throws ServletException,
			IOException {

		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>" + msg + "</h1>");
		response.getWriter().println(
				"session=" + request.getSession(true).getId());
	}
	

	private void doUserUpdateAction(HttpServletRequest request,
			HttpServletResponse response, BufferedReader reader, String msg)
			throws ServletException, IOException {

		String qqid = null;
		String nickname = null;
		String picture = null;
		String userid = null;
		String pwd = null;
		int loginType = -1;
		String sqlString = null;

		while ((msg = reader.readLine()) != null) {
			System.out.println("receive login message:" + msg);

			// login#logintype#
			if (msg.startsWith(DataStruct.LOGIN_TYPE)) {
				loginType = Integer.parseInt(msg
						.substring(DataStruct.LOGIN_TYPE.length()));
			}
			if (loginType == DataStruct.QQ_LOGIN) {
				if (msg.startsWith(DataStruct.QQ_ID)) {
					qqid = msg.substring(DataStruct.QQ_ID.length());
				}
				if(qqid != null){
					sqlString = "update table findu where qqid="+qqid;				
				}

			} else if (loginType == DataStruct.FINDU_LOGIN) {

				if (msg.startsWith(DataStruct.USERID)) {
					userid = msg.substring(DataStruct.USERID.length());
				}
				
				if (msg.startsWith(DataStruct.USERPWD)) {
					pwd = msg.substring(DataStruct.USERPWD.length());
				}
				if(userid != null){
					sqlString = "update table findu where userid="+userid;				
				}
			}

			if (msg.startsWith(DataStruct.NICKNAME)) {
				nickname = msg.substring(DataStruct.NICKNAME.length());
			}
			if (msg.startsWith(DataStruct.PICTURE)) {
				picture = msg.substring(DataStruct.PICTURE.length());
			}
			
		}
		
		if(sqlString == null){
			doResponse(request, response, DataStruct.ACTION_UPDATE + ":"
					+ DataStruct.RESULT_FAIL);
			return;
		}
		
		if(pwd != null){
			sqlString += " set pwd="+pwd;
		}
		if(nickname != null){
			sqlString += " set nickname="+nickname;
		}
		if(picture != null){
			sqlString += " set picture="+picture;
		}

		if (loginType == -1
				|| (loginType == DataStruct.QQ_LOGIN && qqid.equals(""))) {
			doResponse(request, response, DataStruct.ACTION_LOGINUSER + ":"
					+ DataStruct.RESULT_FAIL);
			return;
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		Dao dao = new Dao();
		boolean update = dao.update(sqlString);

		if (update) {
			msg = DataStruct.ACTION_UPDATE + ":" + DataStruct.RESULT_OK;
		} else {
			msg = DataStruct.ACTION_UPDATE + ":" + DataStruct.RESULT_FAIL;
		}
		doResponse(request, response, msg);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>" + msg + "</h1>");
		response.getWriter().println(
				"session=" + request.getSession(true).getId());
	}
}
