<form action="<%=request.getContextPath()%>/RegisterServlet" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>
    <button type="submit">Register</button>
</form>

<% 
    String message = (String) request.getAttribute("message"); 
    if (message != null) { 
%>
    <p style="color: green;"><%= message %></p>
<% 
    } 
%>