package com.shashi.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class BookTrainFwd extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		Cookie ck[] = req.getCookies();
		if (ck != null) {
			String uName = ck[0].getValue();
			// String pWord = ck[1].getValue();
			if (!uName.equals("") || uName != null) {
				RequestDispatcher rd = req.getRequestDispatcher("BookTrains.html");
				rd.forward(req, res);
			}
		} else {
			RequestDispatcher rd = req.getRequestDispatcher("UserLogin.html");
			PrintWriter pw = res.getWriter();
			pw.println("<div class='tab'><p1 class='menu'>Please Login First !</p1></div>");
			rd.include(req, res);
		}
	}

}
