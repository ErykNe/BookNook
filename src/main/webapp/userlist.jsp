<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.models.UserDao" %>

<style>
    <%@ include file="css/style.css"%>
</style>

<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h2>User List</h2>
<table border="1">
    <tr>
        <th>Username</th>
        <th>Email</th>
        <th>Role</th>
    </tr>
    <%
        List<UserDao> users = (List<UserDao>) request.getAttribute("users");
        if (users != null) {
            for (UserDao user : users) {
    %>
    <tr>
        <td><%= user.getUsername() %></td>
        <td><%= user.getEmail() %></td>
        <td><%= user.getRole() %></td>
    </tr>
    <%
            }
        }
    %>
</table>
<a href="<%=request.getContextPath()%>/additem.jsp">Add Item</a>
</body>
</html>