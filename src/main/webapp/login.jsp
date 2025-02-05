<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<style>
    <%@ include file="css/style.css"%>
</style>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="register-form">
    <h2>Login</h2>
    <%
        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
    %>
    <p>You are logged in as <%= username %>.</p>
    <form action="<%=request.getContextPath()%>/LogoutServlet" method="post">
        <button type="submit">Logout</button>
    </form>
    <%
    } else {
    %>
    <form action="<%=request.getContextPath()%>/LoginServlet" method="post">
        <table>
            <tr>
                <td><label for="username">Username</label></td>
                <td><input type="text" id="username" name="username" required></td>
            </tr>
            <tr>
                <td><label for="password">Password:</label></td>
                <td><input type="password" id="password" name="password" required></td>
            </tr>
        </table>
        <button type="submit">Login</button>
    </form>
    <%
        String message = (String) request.getAttribute("message");
        Boolean isError = (Boolean) request.getAttribute("isError");
        if (message != null) {
    %>
    <p style="color: <%= isError != null && isError ? "red" : "green" %>;"><%= message %></p>
    <%
            }
        }
    %>
</div>
</body>
</html>