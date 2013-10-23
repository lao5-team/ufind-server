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

	private boolean isAddUserAction(String msg) {
		return msg.equals(DataStruct.ACTION_ADDUSER);
	}

	private void doAddUserAction(HttpServletRequest request,
			HttpServletResponse response, BufferedReader reader, String msg)
			throws ServletException, IOException {

		String qqid = "";
		String nickname = "";
		String picture = "";
		String userid = null;
		String pwd = null;
		int loginType = -1;

		while ((msg = reader.readLine()) != null) {// 无法读取到
			System.out.println("receive adduser message:" + msg);

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
			doResponse(request, response, DataStruct.ACTION_ADDUSER + ":"
					+ DataStruct.RESULT_OK);
			return;
		}

		String ownid = createOwnId(qqid);
		User user = new User(qqid, ownid, userid, pwd, picture);

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		Dao dao = new Dao();
		boolean added = dao.addUser(user);

		if (added) {
			msg = DataStruct.ACTION_ADDUSER + ":" + DataStruct.RESULT_OK;
		} else {
			msg = DataStruct.ACTION_ADDUSER + ":" + DataStruct.RESULT_FAIL;
		}
		doResponse(request, response, msg);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				request.getInputStream()));

		String msg = "";
		msg = reader.readLine();
		if (!isAddUserAction(msg)) {
			return;
		}

		doAddUserAction(request, response, reader, msg);
		/*
		 * String qqid = ""; String nickname = ""; String picture = ""; String
		 * userid = null; String pwd = null; int loginType = -1;
		 * 
		 * while ((msg = reader.readLine()) != null) {//无法读取到
		 * System.out.println("receive adduser message:" + msg);
		 * 
		 * if(msg.startsWith(LOGIN_TYPE)){ loginType =
		 * Integer.parseInt(msg.substring(LOGIN_TYPE.length())); } if(loginType
		 * == QQ_LOGIN){ if(msg.startsWith(QQ_ID)){ qqid =
		 * msg.substring(QQ_ID.length()); } if(msg.startsWith(NICKNAME)){
		 * nickname = msg.substring(NICKNAME.length()); }
		 * if(msg.startsWith(PICTURE)){ picture =
		 * msg.substring(PICTURE.length()); }
		 * 
		 * 
		 * }else if(loginType == FINDU_LOGIN){
		 * 
		 * }
		 * 
		 * }
		 * 
		 * if(loginType == -1 || (loginType == QQ_LOGIN && qqid.equal("")){
		 * doResponse(response, ACTION_ADDUSER + ":"+RESULT_OK); return; }
		 * 
		 * String ownid = createOwnId(qqid); User user = new User(qqid, ownid,
		 * userid, pwd, picture);
		 * 
		 * response.setContentType("text/html");
		 * response.setCharacterEncoding("utf-8");
		 * 
		 * Dao dao = new Dao(); boolean added = dao.addUser(user);
		 * 
		 * if(added){ msg = ACTION_ADDUSER + ":"+RESULT_OK; }else{ msg =
		 * ACTION_ADDUSER + ":"+RESULT_FAIL; } doResponse(response, msg);
		 */
		/*
		 * request.setAttribute("info",new
		 * String("<br><br><center><h1><font color=red>tianjiachengong,gongxi!!"
		 * + "</font></h1></center><br>")); request.setAttribute("id", new
		 * String("a")); request.setAttribute("denglu",new String(
		 * "<br><br><center><a href = /jspDev/index.jsp target =_parent>��½</href></center><br>"
		 * ));
		 */

		// request.getRequestDispatcher("info.jsp").forward(request, response);
	}
}
