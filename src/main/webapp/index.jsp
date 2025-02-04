<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>BookNook</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
    <div class="left-nav">
        <a href="<%=request.getContextPath()%>/index.jsp?page=booknook">BookNook</a>
        <a href="<%=request.getContextPath()%>/index.jsp?page=register">Register</a>
        <a href="<%=request.getContextPath()%>/index.jsp?page=login">Login</a>
    </div>
    <div class="right-nav">
        <%
            if (session != null && session.getAttribute("username") != null) {
                String username = (String) session.getAttribute("username");
                Double balance = (Double) session.getAttribute("balance");
                String role = (String) session.getAttribute("role");
        %>
        <span>Welcome, <%= username %>! Balance: $<%= balance %></span>
        <%
            if ("admin".equals(role)) {
        %>
        <a class="admin-panel" href="<%=request.getContextPath()%>/UserListServlet">Admin Panel</a>
        <%
                }
            }
        %>
        <a href="<%=request.getContextPath()%>/index.jsp?page=cart"><img src="images/3737369.png" alt="Cart" /></a>
        <a class="orders" href="<%=request.getContextPath()%>/index.jsp?page=orders">Orders</a>
    </div>
</div>

<main>
    <%
        String pageName = request.getParameter("page");
        if (pageName == null) {
            pageName = "booknook.jsp";
        } else {
            pageName = pageName + ".jsp";
        }
    %>
    <jsp:include page="<%= pageName %>"/>
</main>
</body>
</html>