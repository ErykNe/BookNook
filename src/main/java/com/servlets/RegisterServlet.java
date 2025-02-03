package com.servlets;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Forward the request to the register.jsp page
		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			registerUser(username, email, password, getServletContext());
			request.setAttribute("message", "User registered successfully!");
		} catch (Exception e) {
			request.setAttribute("message", "Failed to register user in database");
			e.printStackTrace();
		}

		request.getRequestDispatcher("index.jsp?page=register").forward(request, response);
	}

	private void registerUser(String username, String email, String password, ServletContext context) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		String path = context.getRealPath("/WEB-INF/database.db");
		String sql = "INSERT INTO Users (Username, Email, Password, Balance) VALUES (?, ?, ?, ?);";

		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, username);
			pstmt.setString(2, email);
			pstmt.setString(3, password);
			pstmt.setDouble(4, 1000.50);

			pstmt.executeUpdate();
		}
	}
}