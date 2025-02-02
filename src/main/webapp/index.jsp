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
            <a href="?page=register">Register</a>
            <a href="?page=login">Login</a>
        </div>
        <div class="right-nav">
            <p>Balance: </p>
            <a href="?page=cart"><img src="images/3737369.png" alt="Cart" /></a>
        </div>        
    </div>

    <main>
        <%
            String pageParam = request.getParameter("page"); 
            if (pageParam == null) {
                pageParam = "booknook.jsp"; 
            } else {
                pageParam = pageParam + ".jsp"; 
            }
        %>
        <jsp:include page="<%= pageParam %>" />

    </main>
</body>
</html>
