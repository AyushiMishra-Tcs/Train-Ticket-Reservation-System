package com.shashi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shashi.beans.TrainException;
import com.shashi.utility.DBUtil;

@SuppressWarnings("serial")
public class ChangeUserPwd extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		Cookie ck[] = req.getCookies();
		if (ck != null) {
			String uName = ck[0].getValue();
			String pWord = ck[1].getValue();
			if (!uName.equals("") || uName != null) {
//				ServletContext sct = req.getServletContext();
//				UserBean ub = (UserBean) sct.getAttribute("ubean");
				try {
					String u_Name = req.getParameter("username");
					String oldPWord = (String) req.getParameter("oldpassword");
					String newPWord = req.getParameter("newpassword");
					if (uName.equals(u_Name)) {
						if (pWord.equals(oldPWord)) {
							Connection con = DBUtil.getCon();
							PreparedStatement ps = con
									.prepareStatement("update customer set pword=? where mailid=? and pword=?");
							ps.setString(1, newPWord);
							ps.setString(2, uName);
							ps.setString(3, pWord);
							int k = ps.executeUpdate();
							if (k == 1) {
								RequestDispatcher rd = req.getRequestDispatcher("UserLogin.html");
								rd.include(req, res);

								Cookie ck1 = new Cookie("ckname", "");
								ck1.setMaxAge(0);
								res.addCookie(ck1);
								Cookie ck2 = new Cookie("ckpwd", "");
								ck2.setMaxAge(0);
								res.addCookie(ck2);
								pw.println(
										"<div class='tab'>Your Username and Password has Been Updated Successfully<br/>Please Login Again !</div>");
							} else {
								RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
								rd.include(req, res);
								pw.println("<div class='tab'>" + "		<p1 class='menu'>" + "	Hello " + uName
										+ " ! Welcome to our new NITRTC Website" + "		</p1>" + "	</div>");
								pw.println(
										"<div class='main'><p1 class='menu'><a href='viewuserprofile'>view Profile</a></p1>"
												+ "<p1 class='menu'><a href='edituserprofile'>Edit Profile</a></p1>"
												+ "<p1 class='menu'><a href='changeuserpassword'>Change Password</a></p1>"
												+ "</div>");
								pw.println("<div class='tab'>Invalid Username and Old Password !</div>");
							}
						} else {
							RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
							rd.include(req, res);
							pw.println(
									"<div class='main'><p1 class='menu'><a href='viewuserprofile'>view Profile</a></p1>"
											+ "<p1 class='menu'><a href='edituserprofile'>Edit Profile</a></p1>"
											+ "<p1 class='menu'><a href='changeuserpassword'>Change Password</a></p1>"
											+ "</div>");
							pw.println("<div class='tab'>Wrong Old PassWord!</div>");
						}
					} else {
						RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
						rd.include(req, res);
						pw.println("<div class='main'><p1 class='menu'><a href='viewuserprofile'>view Profile</a></p1>"
								+ "<p1 class='menu'><a href='edituserprofile'>Edit Profile</a></p1>"
								+ "<p1 class='menu'><a href='changeuserpassword'>Change Password</a></p1>" + "</div>");
						pw.println("<div class='tab'>Invalid UserName</div>");
					}

				} catch (Exception e) {
					throw new TrainException(422, this.getClass().getName() + "_FAILED", e.getMessage());
				}
			}
		} else {
			RequestDispatcher rd = req.getRequestDispatcher("UserLogin.html");
			rd.include(req, res);
			pw.println("<div class='tab'><p1 class='menu'>Please Login first !</p1></div>");
		}
	}

}
