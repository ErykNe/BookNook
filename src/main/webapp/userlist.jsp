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
<jsp:include page="navbar.jsp" />
<main>
    <h2 style="text-align: center">User List</h2>
    <% String message = (String) request.getAttribute("message"); %>
    <% if (message != null) { %>
    <p><%= message %></p>
    <% } %>
    <div class="tables" style="text-align: center; justify-content: center">
        <table style="text-align: center; justify-content: center">
            <tr>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
                <th>Action</th>
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
                <td>
                    <form action="<%=request.getContextPath()%>/DeleteUserServlet" method="post">
                        <input type="hidden" name="username" value="<%= user.getUsername() %>">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <a class="add" href="<%=request.getContextPath()%>/additem.jsp">Add Item</a>
    </div>
</main>

</body>
</html>