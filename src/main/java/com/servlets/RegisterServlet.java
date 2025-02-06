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
		//default redirect to itself preventing unwanted get actions performed by user which can lead to malfunctions
		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

        try {
			//check if user with the same username or email already exists
            if (userAlreadyExists(username, email, getServletContext())){
                request.setAttribute("message", "Failed to register user in database: User with the same username or email already exists.");
            } else {
				// if doesn't, register user in database
				try {
					registerUser(username, email, password, getServletContext());
					request.setAttribute("message", "User registered successfully!");
				} catch (Exception e) {
					request.setAttribute("message", "Failed to register user in database, please try again.");
					e.printStackTrace();
				}
			}
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		//send the message to the page
		request.getRequestDispatcher("index.jsp?page=register").forward(request, response);
	}

	private boolean userAlreadyExists(String username, String email, ServletContext context) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		String path = context.getRealPath("/WEB-INF/database.db");
		String sql = "SELECT * FROM users WHERE username = ? OR email = ?;";
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, username);
			pstmt.setString(2, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
			pstmt.setDouble(4, 0);

			pstmt.executeUpdate();
		}
	}
}