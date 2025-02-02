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
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        registerUser(username, email, password, getServletContext());      
        
		request.setAttribute("message", "User registered successfully!");

        // Forward back to index.jsp with parameter
        request.getRequestDispatcher("index.jsp?page=register").forward(request, response);
	}

    private void registerUser(String username, String email, String password, ServletContext context) {

    		try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String path = getServletContext().getRealPath("/WEB-INF/database.db");

        	String sql = "INSERT INTO Users (Username, Email, Password, Balance) VALUES (?, ?, ?, ?);";
        	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
        	        PreparedStatement pstmt = conn.prepareStatement(sql)) {

        	        // Set values for the prepared statement
        	        pstmt.setString(1, username);
        	        pstmt.setString(2, email);
        	        pstmt.setString(3, password);
        	        pstmt.setDouble(4, 1000.50);

        	        // Execute the query
        	        pstmt.executeUpdate();
        	        System.out.println("User successfully registered in the database!");

        	    } catch (SQLException e) {
        	        e.printStackTrace();
        	}

        
    }

}
