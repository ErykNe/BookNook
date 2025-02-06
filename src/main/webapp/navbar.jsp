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
    <a>Welcome, <%= username %>!</a>
    <a> Balance: $<%= String.format("%.2f", balance) %> </a>
    <%
      if ("admin".equals(role)) {
    %>
    <a class="admin-panel" href="<%=request.getContextPath()%>/UserListServlet">Admin Panel</a>
    <%
      }

    %>
    <a href="<%=request.getContextPath()%>/index.jsp?page=cart"><img class="cart-enabled" src="images/3737369.png" alt="Cart" /></a>
    <a class="orders" href="<%=request.getContextPath()%>/index.jsp?page=orders">Orders</a>
    <%
    } else {
    %>
    <a><img class="cart-disabled" src="images/3737369.png" alt="Cart"/></a>

    <% } %>
  </div>
</div>