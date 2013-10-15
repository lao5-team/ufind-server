package com.findu.demo;

import java.sql.*;

public class Dao {
	private Connection mConn;
	private PreparedStatement mPstat;
	String mSql = "";
	public String mSql_DBName = "findu";
	public String mSql_Url = "jdbc:mysql://localhost:3306/";
	public String mUsers = "root";
	public String mPwd = "mysql";

	public Dao() {
		String driver = "com.mysql.jdbc.Driver";
		//String url = "jdbc:mysql://localhost/findu";
		try {
			Class.forName(driver);
			try {
				System.out.println("class load over");
				mConn = DriverManager.getConnection(mSql_Url, mUsers, mPwd);
				//mConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/findu?user=root&password=");
			} catch (SQLException e) {
				System.out.println("connect e");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * yonghu zhuce
	 */
	public boolean login(User user) throws SQLException {
		mConn = DriverManager.getConnection(mSql_Url 
				+ "?user=" + mUsers + "&password=" + mPwd
				+ "&useUnicode=true&characterEncoding=gb2312");
		boolean i = false;
		mSql = "select * from user where user=? and pwd=?";

		mPstat = mConn.prepareStatement(mSql);

		mPstat.setString(1, user.getUser());
		mPstat.setString(2, user.getPwd());

		ResultSet rs1 = (ResultSet) mPstat.executeQuery();
		if (rs1.next()) {
			i = true;
			rs1.close();
			mPstat.close();
		} else {
			i = false;
			rs1.close();
			mPstat.close();
		}
		mConn.close();
		return i;
	}

	/**
	 * 
	 */
	public boolean addUser(User user) {
		try {
			mConn = DriverManager.getConnection(mSql_Url + "/" + mSql_DBName
					+ "?user=" + mUsers + "&password=" + mPwd
					+ "&useUnicode=true&characterEncoding=gb2312");
			mSql = "insert into user values(?,?,?,?,?)";
			mPstat = mConn.prepareStatement(mSql);
			mPstat.setString(1, user.getUser());
			mPstat.setString(2, user.getPwd());
			mPstat.setString(3, user.getName());
			mPstat.setString(4, user.getPicture());
			mPstat.executeUpdate();
			mPstat.close();
			mConn.close();
			return true;

		} catch (SQLException e) {
			System.out.println("add e");
			e.printStackTrace();
		}
		
		return false;

	}
}
