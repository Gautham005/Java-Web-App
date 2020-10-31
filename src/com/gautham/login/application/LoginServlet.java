package com.gautham.login.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "test123");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost()");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		try {

			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select * from userData where email='" + userName + "' and password='" + password + "'");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("user.html");
			if (resultSet.next()) {
				request.setAttribute("message", "<h2>Login successful!!<h2><br> Welcome to Home Page");
				requestDispatcher.forward(request, response);
			} else {
				String msg = "Invalid username or password!";
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.println("<script>alert(\"" + msg + "\")</script>");
				requestDispatcher = request.getRequestDispatcher("login.html");
				requestDispatcher.include(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
