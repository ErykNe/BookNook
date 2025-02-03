<style>
    <%@ include file="css/style.css"%>
</style>

<div class="register-form">
    <h2>Login</h2>
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
    %>
</div>