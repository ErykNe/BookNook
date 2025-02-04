<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>BookNook</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp" />
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