<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.models.UserDao" %>
<style>
    <%@ include file="css/style.css"%>
</style>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<jsp:include page="navbar.jsp" />
<%
    UserDao user = (UserDao) request.getAttribute("user" + "");
    if (user != null) {
%>
<h2>Edit User: <%= user.getUsername() %></h2>
<form action="<%=request.getContextPath()%>/EditUserServlet" method="post">
    <input type="hidden" name="username" value="<%= user.getUsername() %>">
    <label for="newUsername">New Username:</label>
    <input type="text" id="newUsername" name="newUsername" value="<%= user.getUsername() %>"><br>
    <label for="newPassword">New Password:</label>
    <input type="password" id="newPassword" name="newPassword" value="<%= user.getPassword() %>"><br>
    <label for="newEmail">New Email:</label>
    <input type="email" id="newEmail" name="newEmail" value="<%= user.getEmail() %>"><br>
    <label for="newRole">New Role:</label>
    <input type="text" id="newRole" name="newRole" value="<%= user.getRole() %>"><br>
    <input type="submit" value="Update">
</form>
<%
} else {
%>
<p>User not found.</p>
<%
    }
%>
</body>
</html>