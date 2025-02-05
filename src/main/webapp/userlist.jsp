<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.models.UserDao" %>
<style>
    <%@ include file="css/style.css"%>
</style>

<jsp:include page="navbar.jsp" />

<main>
    <h2 style="text-align: center">User List</h2>
    <% String message = (String) request.getAttribute("message"); %>
    <% if (message != null) { %>
    <p style="text-align: center"><%= message %></p>
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
                    <div style="display: inline-flex">
                        <form action="<%=request.getContextPath()%>/DeleteUserServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this user?');">
                            <input type="hidden" name="username" value="<%= user.getUsername() %>">
                            <input type="submit" value="Delete">
                        </form>
                        <form action="<%=request.getContextPath()%>/EditUserServlet" method="get">
                            <input type="hidden" name="username" value="<%= user.getUsername() %>">
                            <input type="submit" value="Edit">
                        </form>
                    </div>

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
