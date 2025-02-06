<style>
    <%@ include file="css/style.css"%>
</style>

<div class="register-form">
    <h2>Register</h2>
    <form action="<%=request.getContextPath()%>/RegisterServlet" method="post">
        <table>
            <tr>
                <td><label for="username">Username</label></td>
                <td><input type="text" id="username" name="username" required></td>
            </tr>
            <tr>
                <td><label for="email">Email:</label></td>
                <td><input type="email" id="email" name="email" required></td>
            </tr>
            <tr>
                <td><label for="password">Password:</label></td>
                <td><input type="password" id="password" name="password" required></td>
            </tr>
        </table>
        <button type="submit">Register</button>
    </form>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
    <p><%= message %></p>
    <p><a href="<%=request.getContextPath()%>/index.jsp?page=login">Login</a></p>
    <p><a href="<%=request.getContextPath()%>/index.jsp?page=booknook">Go back to BookNook</a></p>
    <%
        }
    %>
</div>