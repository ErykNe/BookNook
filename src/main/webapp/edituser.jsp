<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.models.User" %>
<style>
    <%@ include file="css/style.css"%>
</style>

<jsp:include page="navbar.jsp" />
<%
    User user = (User) request.getAttribute("user" + "");
    if (user != null) {
%>
<div class="add-item-panel">
    <h2>Edit User: <%= user.getUsername() %></h2>
    <form action="<%=request.getContextPath()%>/EditUserServlet" method="post">
        <input type="hidden" name="username" value="<%= user.getUsername() %>">
        <table>
            <tr>
                <td><label for="newUsername">New Username:</label></td>
                <td><input type="text" id="newUsername" name="newUsername" value="<%= user.getUsername() %>"></td>
            </tr>
            <tr>
                <td><label for="newPassword">New Password:</label></td>
                <td><input type="password" id="newPassword" name="newPassword" value="<%= user.getPassword() %>"></td>
            </tr>
            <tr>
                <td><label for="newEmail">New Email:</label></td>
                <td><input type="email" id="newEmail" name="newEmail" value="<%= user.getEmail() %>"></td>
            </tr>
            <tr>
                <td><label for="newRole">New Role:</label></td>
                <td><input type="text" id="newRole" name="newRole" value="<%= user.getRole() %>"></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center;">
                    <input type="submit" value="Update">
                </td>
            </tr>
        </table>
    </form>
</div>
<%
} else {
%>
<p style="margin-top: 100px; text-align: center">User not found.</p>
<%
    }
%>