<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.models.UserDao" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp" />

<h2>Edit User</h2>
<% UserDao user = (UserDao) request.getAttribute("user"); %>
<form action="<%=request.getContextPath()%>/EditUserServlet" method="post">
    <input type="hidden" name="oldUsername" value="<%= user.getUsername() %>">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required><br>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" value="<%= user.getPassword() %>" required><br>
    <label for="role">Role:</label>
    <input type="text" id="role" name="role" value="<%= user.getRole() %>" required><br>
    <input type="submit" value="Update">
</form>
</body>
</html>