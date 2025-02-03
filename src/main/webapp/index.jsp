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
            <a href="?page=cart"><img src="images/3737369.png" alt="Cart" /></a>
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
